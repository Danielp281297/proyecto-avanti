package com.example.avantitigestiondeincidencias.ViewModel

import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InicioAdministradorViewModel: ViewModel(){

    private val _ticktesAbiertos = MutableStateFlow<List<Ticket>>(emptyList())
    val ticketAbiertos: StateFlow<List<Ticket>> get() = _ticktesAbiertos.asStateFlow()

    suspend fun obtenerTicketsAbiertos(){

        TicketRequests().seleccionarTicketsAbiertos{ tickets ->

            _ticktesAbiertos.value = tickets

        }

    }

    suspend fun realtimeTicketAbiertos(scope: CoroutineScope){


        TicketRequests().realtimeTicketsAbiertosRequest (scope) { tickets ->

            _ticktesAbiertos.value = emptyList()
            _ticktesAbiertos.value = tickets

        }


    }

}