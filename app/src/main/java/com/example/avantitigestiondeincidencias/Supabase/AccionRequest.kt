package com.example.avantitigestiondeincidencias.Supabase

import android.util.Log
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.AVANTI.DescripcionAccion
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.FormatoHoraFecha.FormatoHoraFecha
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.delay
import kotlinx.coroutines.selects.select
import java.time.LocalTime

class AccionRequest: SupabaseClient() {

    val columnasAccion = Columns.raw("""
        id_acción,
        descripción_acción(
            id_descripción_acción,
            descripción_acción_ejecutada
        )   
        """.trimIndent())

    val accionRequest = Columns.raw("""
        id_acción,
        fecha_acción,
        hora_acción,
        descripción_acción( 
            descripción_acción_ejecutada
        ),
        ticket!inner (
            id_ticket,
            fecha_ticket,
            hora_ticket,
            descripción_ticket,
            observaciones_ticket,
            id_tipo_ticket,
            id_estado_ticket,
            id_cliente_interno,
            id_técnico,
            tipo_ticket(
                id_tipo_ticket,
                nombre_tipo_ticket
            ),
            prioridad_ticket(
                id_prioridad_ticket,
                nivel_prioridad_ticket
            ),
            estado_ticket(
                id_estado_ticket,
                tipo_estado_ticket
            ),
            cliente_interno(
                id_cliente_interno,
                empleado(
                    id_empleado,
                    cédula_empleado,
                    primer_nombre_empleado,
                    segundo_nombre_empleado,
                    primer_apellido_empleado,
                    segundo_apellido_empleado,
                    correo_electrónico_empleado,
                    teléfono_empleado(
                        extensión_teléfono,
                        código_operadora_teléfono(
                            operadora_teléfono
                        )
                    ),
                    departamento(
                        nombre_departamento,
                        piso_departamento,
                        sede(
                            nombre_sede
                        )
                    ),
                    cargo_empleado(
                        tipo_cargo_empleado
                    )
                )
            ),
            técnico(
                id_técnico,
                grupo_atención(
                    id_grupo_atención,
                    nombre_grupo_atención
                ),
                empleado(
                    id_empleado,
                    cédula_empleado,
                    primer_nombre_empleado,
                    segundo_nombre_empleado,
                    primer_apellido_empleado,
                    segundo_apellido_empleado,
                    correo_electrónico_empleado,
                    id_usuario,
                    teléfono_empleado(
                        extensión_teléfono,
                        código_operadora_teléfono(
                            operadora_teléfono
                        )
                    ),
                    departamento(
                        nombre_departamento,
                        piso_departamento,
                        sede(
                            nombre_sede
                        )
                    ),
                    cargo_empleado(
                        tipo_cargo_empleado
                    ),
                    usuario(
                        id_usuario
                    )
                )
            )
        )
    """.trimIndent())

    suspend fun buscarAccionesByIdConRangoFechas(idTecnico: Int, fechaInicio: String, fechaFin: String, acciones: (List<Accion>) -> Unit) {

         val resultados = getSupabaseClient().from("acción").select(columns = accionRequest){

             filter {
                 gte("fecha_acción", fechaInicio)
                 lte("fecha_acción", fechaFin)
                 eq("ticket.id_técnico", idTecnico)
             }

         }
             .decodeList<Accion>()

        resultados.forEach { accion ->

            accion.fecha = FormatoHoraFecha.formatoFecha(accion.fecha).toString()
            accion.hora = FormatoHoraFecha.formatoHora(accion.hora)

        }
        acciones(resultados)

    }


    suspend fun buscarDescripcionAccion(): List<DescripcionAccion> {

        return getSupabaseClient().from("descripción_acción").select().decodeList<DescripcionAccion>()

    }

    suspend fun cerrarTicket(ticket: Ticket, accion: String)
    {

        val accion = Accion(
            hora = LocalTime.now().toString(),
            fecha = java.time.LocalDate.now().toString(),
            idDescripcionAccion = seleccionarAccion(accion),
            idTicket = ticket.id
        )

        // Se crea la accion
        getSupabaseClient().from("acción").insert(accion)


    }

    suspend fun seleccionarAccion(accionEjecutada: String): Int
    {

        var indice: Int = 0

        // Se obtienen los datos de la tabla descripcion_accion
        val descripciones = buscarDescripcionAccion()

        descripciones.forEach { item ->
            Log.d("DESCRIPCION", item.toString())
        }

        // Se comparan las cadenas de caracteres
        for (item in descripciones)
        {
            // Si la accionEjecutada es igual a alguna descripcion en la fila
            // Se devuelve el id del mismo
            if (accionEjecutada == item.descripcion) {
                indice = item.id
                break
            }
        }

        // En caso contrario, se crea la fila, y se devuelve el id
        if (indice == 0) {
            val nuevaDescripcion =
                getSupabaseClient().from("descripción_acción")
                    .insert(DescripcionAccion(descripcion = accionEjecutada)){ select() }
                    .decodeSingle<DescripcionAccion>()

            indice = nuevaDescripcion.id
        }

        return indice

    }

    suspend fun seleccionarAccionbyTicketId(id: Int, accion: (Accion) -> Unit) {

        val resultado = getSupabaseClient().from("acción").select(columns = accionRequest){
            filter {
                eq("id_ticket", id)
            }
        }.decodeSingle<Accion>()

        // Se formatea la fecha y la hora de la accion
            resultado.fecha = FormatoHoraFecha.formatoFecha(resultado.fecha).toString()
            resultado.hora = FormatoHoraFecha.formatoHora(resultado.hora)

        accion(resultado)
    }

}