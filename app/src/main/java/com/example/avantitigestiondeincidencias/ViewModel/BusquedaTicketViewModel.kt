package com.example.avantitigestiondeincidencias.ViewModel

import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BusquedaTicketViewModel: ViewModel() {

    private val _listaTickets = MutableStateFlow<List<Ticket>>(emptyList())
    val listaTickets: StateFlow<List<Ticket>> get() = _listaTickets.asStateFlow()

    private val _descripcion = MutableStateFlow<String>("")
    val descripcion: StateFlow<String> get() = _descripcion.asStateFlow()

    private val _fechaInicial = MutableStateFlow<String>("")
    val fechaInicial: StateFlow<String> get() = _fechaInicial.asStateFlow()

    private val _fechaFinal = MutableStateFlow<String>("")
    val fechaFinal: StateFlow<String> get() = _fechaFinal.asStateFlow()

    fun setDescripcion(entrada:String){ _descripcion.value = entrada }
    fun setFechaInicial(fecha: String){ _fechaInicial.value = fecha }
    fun setFechaFinal(fecha: String){ _fechaFinal.value = fecha }

    suspend fun obtenerTickets(idTecnico: Int){

        TicketRequests().buscarTicketsByTecnicoIdYFechas(
            id = idTecnico,
            fechaInicio = fechaInicial.value,
            fechaFin = fechaFinal.value,
            descripcion = descripcion.value
        ){ tickets ->

            _listaTickets.value = tickets

        }

    }



}