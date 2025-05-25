package com.example.avantitigestiondeincidencias.ViewModel

import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InicioTecnicoViewModel: ViewModel() {

    private val _ticketsAsignados = MutableStateFlow<List<Ticket>>(emptyList())
    val ticketsAsignados: StateFlow<List<Ticket>> get() = _ticketsAsignados.asStateFlow()

    suspend fun obtenerTicketsAsignados(idTecnico: Int){

        TicketRequests().buscarTicketByTecnicoId(idTecnico) { tickets ->

            _ticketsAsignados.value = tickets

        }

    }

    suspend fun realtimeTicketsAsignados(scope: CoroutineScope, idTecnico: Int){

        TicketRequests().realtimeTicketRequestTecnicoId(scope, idTecnico) { tickets ->

            _ticketsAsignados.value = emptyList()
            _ticketsAsignados.value = tickets

        }

    }

}