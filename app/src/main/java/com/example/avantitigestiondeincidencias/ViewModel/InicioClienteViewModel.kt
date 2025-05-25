package com.example.avantitigestiondeincidencias.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InicioClienteViewModel: ViewModel() {

    private val _clienteTickets = MutableStateFlow<List<Ticket>>(emptyList())
    val clienteTickets: StateFlow<List<Ticket>> get() = _clienteTickets.asStateFlow()

    suspend fun obtenerClienteTickets(idEmpleado: Int){

        TicketRequests().buscarTicketByClienteInternoId(idEmpleado) { tickets ->

            _clienteTickets.value = tickets

        }

    }

    private fun ticketsEstadosNotificaciones(context: Context, ticket: Ticket)
    {

        // Se busca si el estado del ticket esta En Proceso

        if(ticket.idEstadoTicket == 2)
        {

            Notification().mostrarNotificacion(context, "${ticket.tipo.tipoTicket} - ${ticket.descripcion}"
                , "Su ${ticket.tipo.tipoTicket} está siendo atendida por ${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.primerApellido}")

        }

        if(ticket.idEstadoTicket == 4)
        {

            Notification().mostrarNotificacion(context, "${ticket.tipo.tipoTicket} - ${ticket.descripcion}"
                , "Su ${ticket.tipo.tipoTicket} fue resuelta por ${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.primerApellido}. Cierre el ticket para terminar la gestión.")

        }
    }

    suspend fun realtimeClienteTickets(context: Context, scope: CoroutineScope, idClienteInterno: Int){

        TicketRequests().realtimeTicketRequestClienteInternoId(scope, idClienteInterno) { tickets ->

            _clienteTickets.value = listOf()

            tickets.forEach { item ->
                if (item.clienteInterno.id == idClienteInterno)
                    //Se almacenan los datos coincidentes con la lista
                    _clienteTickets.update { it + item }
            }


            // Se buscan los estados de los tickets para enviar las notificaciones
            _clienteTickets.value.reversed().forEach { item ->

                ticketsEstadosNotificaciones(context, item)

            }

        }

    }

}