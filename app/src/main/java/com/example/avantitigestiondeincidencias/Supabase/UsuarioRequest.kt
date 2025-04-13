package com.example.avantitigestiondeincidencias.Supabase

import android.util.Log
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.Login
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.ktor.sse.COLON

class UsuarioRequest(): SupabaseClient() {

    private val columnaTecnico = Columns.raw("""
        
        id_técnico,
        grupo_atención(
            id_grupo_atención,
            nombre_grupo_atención
        ),
        empleado!inner(
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
            ),
            usuario!inner(
                id_usuario,
                nombre_usuario,
                id_tipo_usuario,
                tipo_usuario(
                    tipo_usuario
                )
            )
        )
        
    """.trimIndent())

    private val columnaClienteInterno = Columns.raw("""
        id_cliente_interno,
        empleado!inner(
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
            ),
            usuario!inner(
                id_usuario,
                nombre_usuario,
                id_tipo_usuario,
                tipo_usuario(
                    tipo_usuario
                )
            )
        )
    """.trimIndent())

    // Metodo para devolver los datos de los tecnicos de forma descendente
    suspend fun seleccionarUsuariosTecnico(function: (List<Tecnico>) -> Unit) {

        val dataset = getSupabaseClient().from("técnico").select(columns = columnaTecnico){

            order("id_técnico", Order.DESCENDING)
            filter {
                gt("id_técnico", 1)
            }

        }.decodeList<Tecnico>()

        function(dataset)

    }

    // Metodo para devolver los datos de los clientes tecnicos de forma descendente
    suspend fun seleccionarUsuariosClienteInterno(function: (List<ClienteInterno>) -> Unit) {

        val dataset = getSupabaseClient().from("cliente_interno").
        select(columns = columnaClienteInterno){
            order("id_cliente_interno", Order.DESCENDING)
            filter {
                gt("id_cliente_interno", 1)
            }
        }.decodeList<ClienteInterno>()

        function(dataset)

    }

}