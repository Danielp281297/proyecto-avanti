package com.example.avantitigestiondeincidencias.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TicketDesplegadoAdministrador: ViewModel() {

    private val _listaTecnicos = MutableStateFlow<List<String>>(emptyList())
    val listaTecnicos: StateFlow<List<String>> get() = _listaTecnicos.asStateFlow()

    private val _idTecnico = MutableStateFlow<Int>(0)
    val idTecnico: StateFlow<Int> get() = _idTecnico.asStateFlow()

    private val _prioridadTicket = MutableStateFlow<Int>(0)
    val prioridadTicket: StateFlow<Int> get() = _prioridadTicket.asStateFlow()

    fun setListaTecnicos(dataset: List<String>){ _listaTecnicos.update { it + "Seleccione --" + dataset } }
    fun setPrioridadTicket(prioridad: Int) { _prioridadTicket.value = prioridad }
    fun setIdTecnico(cadena: String){

        // Se obtiene el id del tecnico de la cadena de caracteres
        _idTecnico.value = obtenerEnteroDeCadena(cadena) ?: -1
    }

    fun validarDatosTicket() : Boolean
    {

        if (_idTecnico.value > 0 && (_prioridadTicket.value > 1 && _prioridadTicket.value < 6))
            return true
        else
            return false

    }

    suspend fun asignarTicket(idTicket: Int){

        TicketRequests().updateTicketEnProcesoTecnicoEstadoById(idTicket, prioridadTicket.value, idTecnico.value)

    }

    suspend fun cancelarTicket(ticket: Ticket, resultado: (Ticket?) -> Unit)
    {
        TicketRequests().cancelarTicket(ticket){

            resultado(it)

        }
    }

    fun obtenerEnteroDeCadena(cadena: String): Int? {

        //Definir la expresión regular para encontrar dígitos
        val regex = "\\d+".toRegex() // \\d+ significa uno o más dígitos (0-9)

        val matchResult = regex.find(cadena)

        val valorEntero = matchResult?.value?.toInt()

        return valorEntero

    }

}