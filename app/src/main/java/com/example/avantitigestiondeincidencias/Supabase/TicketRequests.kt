package com.example.avantitigestiondeincidencias.Supabase

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.selects.select

class TicketRequests(): SupabaseClient() {

    val request = """
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
            cliente_interno!inner(
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
                    )
                )
            ),
            técnico!inner(
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
                    )
                )
            )
            
        """.trimIndent()
    val columnas = Columns.raw(request)

    // Metodo para mostrar los tickets. En el caso del inicio del administrador, se mostraran todos los tickets que no esten cerrados
    @OptIn(SupabaseExperimental::class)
    suspend fun mostrarTablaTicket(lambda: (tickets: List<Ticket>) -> Unit)
    {


        val resultados =  getSupabaseClient().from("ticket").select(columns = this.columnas){

            // Se ordenan los datos de forma descendente
            order(column = "id_ticket", order = Order.DESCENDING)
            limit(count = 50)

        }.decodeList<Ticket>()

        lambda(resultados)

    }

    @OptIn(SupabaseExperimental::class)
    suspend fun realtimeTicketRequest(scope: CoroutineScope, lambda: (dataset: MutableList<Ticket>) -> Unit)
    {

        val channel = getSupabaseClient().channel(channelId = "ChannelTicket")

        val channelFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public"){
            table = "ticket"
        }

        var dataset = mutableListOf<Ticket>()

        channelFlow.onEach {

            var results = getSupabaseClient().from("ticket").selectAsFlow(Ticket::id)

            results.collect {

                dataset.clear()
                mostrarTablaTicket {

                    dataset.addAll(it)

                }

                lambda(dataset)

            }

        }.launchIn(scope)

        //channel.realtime.connect()

        channel.subscribe()

    }


    //Se busca los ticket en base al id de cliente interno
    suspend fun insertarTicketByClienteInternoId(ticket: Ticket) {

        getSupabaseClient().from("ticket").insert(ticket){
            select(columns = columnas)
        }.decodeSingle<Ticket>()

        //Se obtienen los datos para actualizar la base de datos
        /*
        buscarTicketByClienteInternoId(ticket.clienteInterno.id) { tickets ->

            tickets.forEach { item ->
                Log.e("TICEKT", item.toString())
            }
            lambda(tickets)

        }
        */


    }

    suspend fun buscarTicketByClienteInternoId(idEmpleado: Int, lambda: (List<Ticket>) -> Unit) {

        val resultados =  getSupabaseClient().from("ticket").select(columns = this.columnas){

            // Se ordenan los datos de forma descendente
            order(column = "id_ticket", order = Order.DESCENDING)
            filter { eq("cliente_interno.empleado.id_empleado", idEmpleado) }
            limit(count = 50)

        }.decodeList<Ticket>()

        lambda(resultados)

    }

    @OptIn(SupabaseExperimental::class)
    suspend fun realtimeTicketRequestClienteInternoId(scope: CoroutineScope, id: Int, lambda: (dataset: MutableList<Ticket>) -> Unit)
    {

        val channel = getSupabaseClient().channel(channelId = "ChannelTicket")

        val channelFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public"){
            table = "ticket"
        }

        var dataset = mutableListOf<Ticket>()

        channelFlow.onEach {

            // Se buscan todos las filas que pertenezcan al ID del cliente interno

            mostrarTablaTicket {

                dataset.clear()

                it.forEach { ticket ->

                        dataset.add(ticket)
                }

            }

            lambda(dataset)

        }.launchIn(scope)

        channel.subscribe()

    }

    suspend fun updateTicketEnProcesoTecnicoEstadoById(idTicket: Int, idTecnico: Int)
    {

        getSupabaseClient().from("ticket").update({
            set("id_estado_ticket", 2)
            set("id_técnico", idTecnico)
        }){
            filter{
                eq("id_ticket", idTicket)
            }
        }

        delay(1000)
        getSupabaseClient().from("ticket").update({
            set("id_estado_ticket", 3)
            set("técnico.id_empleado", idTecnico)
        }){
            filter{
                eq("id_ticket", idTicket)
            }
        }

    }

    suspend fun buscarTicketByTecnicoId(id: Int, lambda: (List<Ticket>) -> Unit) {


        val resultados =  getSupabaseClient().from("ticket").select(columns = this.columnas){


            // Se ordenan los datos de forma descendente
            order(column = "id_ticket", order = Order.DESCENDING)
            limit(count = 50)


        }.decodeList<Ticket>()

        val lista = resultados.filter { it.tecnico.empleado.id == id }

        lambda(lista)

    }

    @OptIn(SupabaseExperimental::class)
    suspend fun realtimeTicketRequestTecnicoId(scope: CoroutineScope, id: Int, lambda: (dataset: MutableList<Ticket>) -> Unit)
    {

        val channel = getSupabaseClient().channel(channelId = "ChannelTicket")

        val channelFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public"){
            table = "ticket"
        }

        var dataset = mutableListOf<Ticket>()

        channelFlow.onEach {

            // Se buscan todos las filas que pertenezcan al ID del cliente interno
            mostrarTablaTicket {

                dataset.clear()

                it.forEach { ticket ->
                    if (ticket.tecnico.id == id)
                    {
                        dataset.add(ticket)
                    }
                }

            }

            lambda(dataset)

        }.launchIn(scope)

        channel.subscribe()

    }

    suspend fun cerrarTicket(ticket: Ticket, observaciones: String)
    {

        // Se comprueba si la accion ya ha sido creada
        // Sino se crea, y se obtiene los datos de la fila

        //Se actualiza el ticket a cerrado
        getSupabaseClient().from("ticket").update({
            set("id_estado_ticket", 4)
            set("observaciones_ticket", observaciones)
        }){
            filter {
                eq("id_ticket", ticket.id)
            }
        }
        delay(1000)
        getSupabaseClient().from("ticket").update({
            set("id_estado_ticket", 5)
        }){
            filter {
                eq("id_ticket", ticket.id)
            }
        }

    }

    suspend fun borrarTicket(ticket: Ticket)
    {

        Log.d("TICKET ID", ticket.id.toString())

        // Primero, se verifica si el ticket tiene una accion asociada
        getSupabaseClient().from("acción").delete(){
            filter {
                eq("id_ticket", ticket.id)
            }
        }

        // Se borra el ticket
        getSupabaseClient().from("ticket").delete{
            filter {
                eq("id_ticket", ticket.id)
            }
        }

    }

}