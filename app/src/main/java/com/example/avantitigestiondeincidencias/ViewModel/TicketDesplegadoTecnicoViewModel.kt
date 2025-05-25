package com.example.avantitigestiondeincidencias.ViewModel

import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.DescripcionAccion
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TicketDesplegadoTecnicoViewModel: ViewModel() {

    private val _listaDescripcionAcciones = MutableStateFlow<List<DescripcionAccion>>(emptyList())
    val listaDescripcionAcciones: StateFlow<List<DescripcionAccion>> get() = _listaDescripcionAcciones.asStateFlow()

    private val _descripcionAccion = MutableStateFlow(DescripcionAccion())
    val descripcionAccion: StateFlow<DescripcionAccion> get() = _descripcionAccion.asStateFlow()

    private val _observaciones = MutableStateFlow("SIN OBSERVACIONES")
    val observaciones: StateFlow<String> get() = _observaciones.asStateFlow()

    suspend fun setListaDescripcionAcciones() {_listaDescripcionAcciones.value = AccionRequest().buscarDescripcionAccion()}

    fun setDescripcionAccion(entrada: String) { _descripcionAccion.value.descripcion = entrada }

    fun setObservaciones(entrada: String){ _observaciones.value = entrada }

    fun validarDatosTicketAccion(): Boolean
    {

        return descripcionAccion.value.descripcion.isNotBlank() && observaciones.value.isNotBlank()

    }

    suspend fun IncidenciaResuelta(ticket: Ticket)
    {
        //Se crea la fila de la accion, y se actualiza el estado del ticket
        TicketRequests().resolverTicket(ticket, observaciones.value)

        AccionRequest()
            .insertarAccion(ticket, descripcionAccion.value.descripcion)
    }

}