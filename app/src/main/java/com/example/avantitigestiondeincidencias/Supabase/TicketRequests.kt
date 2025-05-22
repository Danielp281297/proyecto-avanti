package com.example.avantitigestiondeincidencias.Supabase

import android.util.Log
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.FormatoHoraFecha.FormatoHoraFecha
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.LocalDate
import java.time.LocalTime

class TicketRequests(): SupabaseClient() {

    val request = """
            id_ticket,
            fecha_ticket,
            hora_ticket,
            fecha_asignación_ticket,
            hora_asignación_ticket,
            descripción_ticket,
            observaciones_ticket,
            calificación_gestión_ticket,
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
                    nacionalidad_empleado,
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

        }.decodeList<Ticket>()

        // Se formatea la fecha y la hora de los tickets
        resultados.forEach {ticket ->

            ticket.fecha = FormatoHoraFecha.formatoFecha(ticket.fecha).toString()
            ticket.hora = FormatoHoraFecha.formatoHora(ticket.hora)

        }

        lambda(resultados)

    }

    suspend fun seleccionarTicketsAbiertos(ticketsAbiertos: (tickets: List<Ticket>) -> Unit)
    {

        val resultados =  getSupabaseClient().from("ticket").select(columns = this.columnas){

            // Se ordenan los datos de forma descendente
            order(column = "id_ticket", order = Order.DESCENDING)
            filter {
                eq("id_estado_ticket", 1)  // Abierto
            }

        }.decodeList<Ticket>()

        // Se formatea la fecha y la hora de los tickets
        resultados.forEach {ticket ->

            ticket.fecha = FormatoHoraFecha.formatoFecha(ticket.fecha).toString()
            ticket.hora = FormatoHoraFecha.formatoHora(ticket.hora)

        }

        ticketsAbiertos(resultados)

    }



    @OptIn(SupabaseExperimental::class)
    suspend fun realtimeTicketCanceladoRequest(scope: CoroutineScope, ticket: Ticket, lambda: (bandera: Boolean) -> Unit){

        val channel = getSupabaseClient().channel(channelId = "ChannelTicket")

        val channelFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public"){
            table = "ticket"
        }

        var dataset = mutableListOf<Ticket>()

        channelFlow.onEach {

            var results = getSupabaseClient().from("ticket").selectAsFlow(Ticket::id)

            results.collect {

                it.forEach {
                    if (it.id == ticket.id && it.idEstadoTicket == 6)
                        lambda(true)
                }



            }

        }.launchIn(scope)

        channel.subscribe()
    }

    @OptIn(SupabaseExperimental::class)
    suspend fun realtimeTicketsAbiertosRequest(scope: CoroutineScope, lambda: (dataset: MutableList<Ticket>) -> Unit)
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
                seleccionarTicketsAbiertos {

                    dataset.addAll(it)

                }

                lambda(dataset)

            }

        }.launchIn(scope)

        //channel.realtime.connect()

        channel.subscribe()

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

    }

    suspend fun buscarTicketByClienteInternoId(idEmpleado: Int, lambda: (List<Ticket>) -> Unit) {

        val resultados =  getSupabaseClient().from("ticket").select(columns = this.columnas){

            // Se ordenan los datos de forma descendente
            order(column = "id_ticket", order = Order.DESCENDING)
            filter { eq("cliente_interno.empleado.id_empleado", idEmpleado) }
            limit(count = 50)

        }.decodeList<Ticket>()

        // Se formatea la fecha y la hora de los tickets
        resultados.forEach {ticket ->

            ticket.fecha = FormatoHoraFecha.formatoFecha(ticket.fecha).toString()
            ticket.hora = FormatoHoraFecha.formatoHora(ticket.hora)
            ticket.fechaAsignacion = FormatoHoraFecha.formatoFecha(ticket.fechaAsignacion).toString()
            ticket.horaAsignacion = FormatoHoraFecha.formatoHora(ticket.horaAsignacion)

        }

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

    suspend fun updateTicketEnProcesoTecnicoEstadoById(idTicket: Int, idPrioridadTicket: Int, idTecnico: Int)
    {

        getSupabaseClient().from("ticket").update({
            set("fecha_asignación_ticket", java.time.LocalDate.now().toString())
            set("hora_asignación_ticket", LocalTime.now().toString())
            set("id_estado_ticket", 2)
            set("id_prioridad_ticket", idPrioridadTicket)
            set("id_técnico", idTecnico)
        }){
            filter{
                eq("id_ticket", idTicket)
            }
        }

        delay(1000)
        getSupabaseClient().from("ticket").update({
            set("id_estado_ticket", 3)
        }){
            filter{
                eq("id_ticket", idTicket)
            }
        }

    }

    suspend fun buscarTicketByTecnicoId(id: Int, lambda: (List<Ticket>) -> Unit) {


        var resultados =  getSupabaseClient().from("ticket").select(columns = this.columnas){

            // Se ordenan los datos de forma descendente
            order(column = "id_ticket", order = Order.DESCENDING)
            limit(count = 50)
            // Se buscan los tickets que no esten cerrados
            filter {
                eq("id_técnico", id)
                lt("id_estado_ticket", 4)
            }

        }.decodeList<Ticket>()

        /*
        // Se formatea la fecha y la hora de los tickets
        resultados.forEach {ticket ->

            ticket.fecha = FormatoHoraFecha.formatoFecha(ticket.fecha).toString()
            ticket.hora = FormatoHoraFecha.formatoHora(ticket.hora)

        }

        val lista = resultados.filter { it.tecnico.empleado.id == id }
        */

        resultados.forEach {

            it.hora = FormatoHoraFecha.formatoHora(it.hora)
            it.fecha = FormatoHoraFecha.formatoFecha(it.fecha).toString()

            it.fechaAsignacion = FormatoHoraFecha.formatoFecha(it.fechaAsignacion)!!
            it.horaAsignacion  = FormatoHoraFecha.formatoHora(it.horaAsignacion)

        }

        lambda(resultados)

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
            buscarTicketByTecnicoId(id){

                dataset.clear()

                //it.forEach { ticket ->
                    //if (ticket.tecnico.empleado.id == id)
                   // {
                        dataset.addAll(it)
                    //}
               // }

            }

            lambda(dataset)

        }.launchIn(scope)

        channel.subscribe()

    }

    suspend fun resolverTicket(ticket: Ticket, observaciones: String)
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

    }

    suspend fun cerrarTicket(ticket: Ticket, calificacion: Int, resultado: () -> Unit)
    {

        //Se actualiza el ticket a cerrado
        getSupabaseClient().from("ticket").update({
            set("id_estado_ticket", 5)
            set("calificación_gestión_ticket", calificacion)
        }){
            filter {
                eq("id_ticket", ticket.id)
            }
        }

        resultado()

    }

    suspend fun cancelarTicket(ticket: Ticket, resultado: (Ticket?) -> Unit)
    {

        // Se borra el ticket
        val resultado = getSupabaseClient().from("ticket").update({
            set("id_estado_ticket", 6) //CANCELADO
        }){
            filter {
                eq("id_ticket", ticket.id)
            }
        }.decodeAsOrNull<Ticket>()

        resultado(resultado)

    }

    suspend fun seleccionarTicketByDescripcion(descripcion: String, tickets: (List<Ticket>) -> Unit) {


        val resultados =  getSupabaseClient().postgrest.from("ticket").select(columns = this.columnas){

            // Se ordenan los datos de forma descendente
            order(column = "id_ticket", order = Order.DESCENDING)
            filter {
                like("descripción_ticket", "%${descripcion}%")
            }

        }.decodeList<Ticket>()

        // Se formatea la fecha y la hora de los tickets
        resultados.forEach {ticket ->

            ticket.fecha = FormatoHoraFecha.formatoFecha(ticket.fecha).toString()
            ticket.hora = FormatoHoraFecha.formatoHora(ticket.hora)

        }

        tickets(resultados)
    }

    suspend fun seleccionarTicketsByIdTecnicoYRangoFechas(idTecnico: Int, inicioFecha: String, finFecha: String, tickets: (List<Ticket>) -> Unit)
    {

        val resultados = getSupabaseClient().from("ticket").select(columnas){

            filter {
                gte("fecha_ticket", inicioFecha)
                lte("fecha_ticket", finFecha)
                eq("id_técnico", idTecnico)
            }

            order(column = "id_ticket", order = Order.DESCENDING)

        }.decodeList<Ticket>()

        tickets(resultados)

    }

    suspend fun buscarTicketsByTecnicoIdYFechas(id: Int, fechaInicio: String, fechaFin: String, descripcion: String, tickets:(List<Ticket>) -> Unit){


        var resultados = getSupabaseClient().from("ticket").select(columnas){
            order("id_ticket", Order.DESCENDING)
            filter {

                if (fechaInicio.isNotEmpty())
                    gte("fecha_ticket", FormatoHoraFecha.formatoFechaSupabase(fechaInicio)!!)

                if (fechaFin.isNotEmpty())
                    lte("fecha_ticket", FormatoHoraFecha.formatoFechaSupabase(fechaFin)!!)

                if (descripcion.isNotEmpty())
                    like("descripción_ticket", "%${descripcion}%")

                if (id > 1)
                eq("id_técnico", id)
            }
        }.decodeList<Ticket>()

        resultados.forEach {

            it.hora = FormatoHoraFecha.formatoHora(it.hora)
            it.fecha = FormatoHoraFecha.formatoFecha(it.fecha).toString()

            it.fechaAsignacion = FormatoHoraFecha.formatoFecha(it.fechaAsignacion)!!
            it.horaAsignacion  = FormatoHoraFecha.formatoHora(it.horaAsignacion)

        }

       tickets(resultados)

    }

}