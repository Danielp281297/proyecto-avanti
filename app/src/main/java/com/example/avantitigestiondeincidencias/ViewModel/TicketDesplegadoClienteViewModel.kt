package com.example.avantitigestiondeincidencias.ViewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TicketDesplegadoClienteViewModel: ViewModel() {

    private val _calificacion = MutableStateFlow<Int>(1)
    val calificacion: StateFlow<Int> get() = _calificacion.asStateFlow()

    fun setCalificacion(valor: Int){ _calificacion.value = valor }

    suspend fun cerrarTicket(ticket: Ticket, resultados: () -> Unit)
    {
        TicketRequests().cerrarTicket(ticket, calificacion.value){

            resultados()
        }

    }

    suspend fun cancelarTicket(ticket: Ticket)
    {
        TicketRequests().cancelarTicket(ticket){}
    }

}