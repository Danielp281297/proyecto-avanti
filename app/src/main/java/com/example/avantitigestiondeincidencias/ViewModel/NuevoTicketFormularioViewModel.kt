package com.example.avantitigestiondeincidencias.ViewModel


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalTime

class NuevoTicketFormularioViewModel: ViewModel() {

    private val _descripcion = MutableStateFlow<String>("")
    val descripcion: StateFlow<String> get() = _descripcion.asStateFlow()

    private val _idTipoTicket = MutableStateFlow<Int>(1)
    val idTipoTicket: StateFlow<Int> get() = _idTipoTicket.asStateFlow()

    fun getDescripcion(entrada: String){ _descripcion.value = entrada }

    fun getIdTipoTicket(id: Int){ _idTipoTicket.value = id }

    // Validaciones
    fun validarContenidoTicket(context: Context): Boolean
    {

        var bandera = false

        if(idTipoTicket.value < 1 || idTipoTicket.value > 4)
        {
            Toast.makeText(context, "Tipo de ticket inválido. Intente de nuevo.", Toast.LENGTH_SHORT).show()
        }
        else if(descripcion.value.isBlank()) // Se comprueba que la descripcion no este vacia, ni conformada por espacios
        {

            Toast.makeText(context, "Descripción inválida. Intente de nuevo.", Toast.LENGTH_SHORT).show()

        }
        else bandera = true

        return bandera

    }

    // Supabase
    suspend fun abrirTicket(idClienteInterno: Int){

        val ticket = Ticket(
            hora = LocalTime.now().toString(),
            fecha = LocalDate.now().toString(),
            descripcion = descripcion.value,
            observaciones = "Sin observaciones",
            idPrioridadTicket = 1,
            idTipoTicket = idTipoTicket.value,
            idEstadoTicket = 1, // abierto
            idClienteInterno = idClienteInterno,
            idTecnico = 1

        )

        TicketRequests().insertarTicketByClienteInternoId(ticket)

    }

}