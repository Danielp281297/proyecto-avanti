package com.example.avantitigestiondeincidencias.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.POI.POI
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InformeTecnicoViewModel: ViewModel() {

    private val _fechaInicial = MutableStateFlow<String>("")
    val fechaInicial: StateFlow<String> get() = _fechaInicial.asStateFlow()

    private val _fechaFinal = MutableStateFlow<String>("")
    val fechaFinal: StateFlow<String> get() = _fechaFinal.asStateFlow()

    private val _listaAcciones = MutableStateFlow<List<Accion>>(emptyList())
    val listaAcciones: StateFlow<List<Accion>> get() = _listaAcciones.asStateFlow()

    fun setFechaInicial(fecha: String){ _fechaInicial.value = fecha }
    fun setFechaFinal(fecha: String){ _fechaFinal.value = fecha }

    fun validarFechas() : Boolean = fechaInicial.value.isNotBlank() && fechaFinal.value.isNotBlank()

    suspend fun obtenerTicketsResueltos(idTecnico: Int)
    {

        // Se convierten las fechas en formato YYYY-MM-DDDD
        val formatoEntrada = DateTimeFormatter.ofPattern("dd-M-yyyy") // Corregido para aceptar mes de un dígito

        val fechaInicio = LocalDate.parse(fechaInicial.value, formatoEntrada)
        val fechaFin = LocalDate.parse(fechaFinal.value, formatoEntrada)

        AccionRequest().buscarAccionesByIdConRangoFechas(
            idTecnico = idTecnico,
            inicioFecha = fechaInicio.toString(),
            finFecha = fechaFin.toString(),
            acciones = {
                _listaAcciones.value = it
            }
        )

    }

    fun generarInforme(context: Context)
    {

        if (listaAcciones.value.isNotEmpty()) {
            POI.generalInformeExcel(listaAcciones.value, fechaInicial.value, fechaFinal.value, ){ _, _->}

            // Se avisa la creacion del informe\
            Notification().mostrarNotificacion(
                context,
                "AVANTI - Gestión de Incidencias: Informe",
                "Se guardó el informe en la carpeta de Documentos."
            )
        }
        else
            Log.d("AVISO", "LISTA VACIA, NO PROCEDE")

    }

}