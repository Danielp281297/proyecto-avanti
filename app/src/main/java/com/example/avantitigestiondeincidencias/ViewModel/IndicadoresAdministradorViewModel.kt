package com.example.avantitigestiondeincidencias.ViewModel

import android.content.Context
import android.util.Log
import android.util.StateSet
import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.FormatoHoraFecha.Conversor
import com.example.avantitigestiondeincidencias.FormatoHoraFecha.Conversor.Companion.duracionFechasMilisegundos
import com.example.avantitigestiondeincidencias.FormatoHoraFecha.FormatoHoraFecha
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.POI.POI
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.Duration

class IndicadoresAdministradorViewModel: ViewModel() {

    private val _fechaInicial = MutableStateFlow<String>("")
    val fechaInicial: StateFlow<String> get() = _fechaInicial.asStateFlow()

    private val _fechaFinal = MutableStateFlow<String>("")
    val fechaFinal: StateFlow<String> get() = _fechaFinal.asStateFlow()

    private val _listaTickets = MutableStateFlow<List<Ticket>>(emptyList())
    val listaTickets: StateFlow<List<Ticket>> get() = _listaTickets.asStateFlow()

    private val _listaAcciones = MutableStateFlow<List<Accion>>(emptyList())
    val listaAcciones: StateFlow<List<Accion>> get() = _listaAcciones.asStateFlow()

    // Datos para los Pie Chart
    private val _ticketsAbiertosContador = MutableStateFlow<Int>(0)
    val ticketsAbiertosContador: StateFlow<Int> get() = _ticketsAbiertosContador.asStateFlow()

    private val _ticketsPendientesContador = MutableStateFlow<Int>(0)
    val ticketsPendientesContador: StateFlow<Int> get() = _ticketsPendientesContador.asStateFlow()

    private val _ticketsCerradosContador = MutableStateFlow<Int>(0)
    val ticketsCerradosContador: StateFlow<Int> get() = _ticketsCerradosContador.asStateFlow()

    private val _ticketsCanceladosContador = MutableStateFlow<Int>(0)
    val ticketsCanceladosContador: StateFlow<Int> get() = _ticketsCanceladosContador.asStateFlow()


    private val _ticketsIncidenciasContador = MutableStateFlow<Int>(0)
    val ticketsIncidenciasContador: StateFlow<Int> get() = _ticketsIncidenciasContador.asStateFlow()

    private val _ticketsSolicitudesContador = MutableStateFlow<Int>(0)
    val ticketsSolicitudesContador: StateFlow<Int> get() = _ticketsSolicitudesContador.asStateFlow()

    private val _ticketsMantenimientoContador = MutableStateFlow<Int>(0)
    val ticketsMantenimientoContador: StateFlow<Int> get() = _ticketsMantenimientoContador.asStateFlow()

    private val _ticketsControlCambioContador = MutableStateFlow<Int>(0)
    val ticketsControlCambioContador: StateFlow<Int> get() = _ticketsControlCambioContador.asStateFlow()

    // Promedios
    private val _promedioCalificacionTicket = MutableStateFlow<Float>(0F)
    val promedioCalificacionTicket: StateFlow<Float> get() = _promedioCalificacionTicket.asStateFlow()

    private val _promedioTiempoResolucion = MutableStateFlow<Duration>(Duration.ZERO)
    val promedioTiempoResolucion: StateFlow<Duration> get() = _promedioTiempoResolucion.asStateFlow()

    fun setFechaInicial(fecha: String){ _fechaInicial.value = fecha }
    fun setFechaFinal(fecha: String){ _fechaFinal.value = fecha }

    fun validarFechas() : Boolean = fechaInicial.value.isNotBlank() || fechaFinal.value.isNotBlank()

    private fun promedioTiempoResolucion() {

        var fechaHora = Conversor.Companion.RangoFecha(
            fechaInicio = Conversor.Companion.Fecha(
                fecha = "",
                hora = ""
            ),
            fechaFin = Conversor.Companion.Fecha(
                fecha = "",
                hora = ""
            )
        )

        var sumatoria = Duration.ZERO

        listaAcciones.value.forEach { accion ->

            fechaHora = Conversor.Companion.RangoFecha(
                fechaInicio = Conversor.Companion.Fecha(
                    fecha = FormatoHoraFecha.formatoFecha(accion.ticket.fechaAsignacion).toString(),
                    hora = FormatoHoraFecha.formatoHora(accion.ticket.horaAsignacion)
                ),
                fechaFin = Conversor.Companion.Fecha(
                    fecha = accion.fecha,
                    hora = FormatoHoraFecha.formatoHora(accion.hora)
                )
            )

            sumatoria = sumatoria.plus(duracionFechasMilisegundos(fechaHora))

        }

        _promedioTiempoResolucion.value = sumatoria.dividedBy(listaAcciones.value.count().toLong())

    }

    private fun promedioCalificacionTickets()
    {

        var sumatoria = 0

        listaTickets.value.forEach {
            sumatoria += it.calificacionGestionTicket
        }

        _promedioCalificacionTicket.value = sumatoria / listaTickets.value.count().toFloat()

    }

    private fun ObtenerDatosTickets(){

        listaTickets.value.forEach { ticket ->

            when(ticket.idEstadoTicket)
            {
                1 -> _ticketsAbiertosContador.value++
                3 -> _ticketsPendientesContador.value++
                5 -> _ticketsCerradosContador.value++
                6 -> _ticketsCanceladosContador.value++
            }

            when(ticket.idTipoTicket)
            {
                1 -> _ticketsIncidenciasContador.value++
                2 -> _ticketsSolicitudesContador.value++
                3 -> _ticketsMantenimientoContador.value++
                4 -> _ticketsControlCambioContador.value++
            }

        }

    }

    suspend fun obtenerTicketsResueltos()
    {

        TicketRequests().buscarTicketsByTecnicoIdYFechas(1, fechaInicial.value, fechaFinal.value, "") {

            _listaTickets.value = it
            ObtenerDatosTickets()

        }

        AccionRequest().seleccionarAccionesbyRangoFechas(fechaInicial.value, fechaFinal.value) {

            _listaAcciones.value = it
            promedioTiempoResolucion()
            promedioCalificacionTickets()

        }

        // Se obtienen los datos de los tickets

    }

    fun generarInforme(context: Context)
    {

        if (listaAcciones.value.isNotEmpty())
        {

            // Se obtienen los datos de los tecnicos en la lista de las acciones
            val tecnicos = listaAcciones.value.map { it.ticket.tecnico }.toSet().toList()

            var numeroTicketsPorTecnico = mutableListOf<Int>()

            tecnicos.forEach { tecnico ->
                numeroTicketsPorTecnico.add(listaAcciones.value.count{ it.ticket.tecnico == tecnico })
            }

            var nombresTecnicosYTickets = mutableListOf<String>()
            var i = 0
            tecnicos.forEach {tecnico ->

                nombresTecnicosYTickets.add("${tecnico.empleado.primerNombre} ${tecnico.empleado.primerApellido}: \n ${numeroTicketsPorTecnico[i]} Tickets")
                i++
            }

            POI.generalInformeExcel(
                acciones = listaAcciones.value,
                fechaInicio = fechaInicial.value,
                fechaFin = fechaFinal.value,
                adicional = { sheet, cantidad ->

                    // Se crea la grafica de tortas, en base a los tickets y los tecnicos
                    POI.generarPieChart(
                        sheet = sheet,
                        filaComienzo = cantidad + 2,
                        columnaComienzo = 2,
                        filaFin = cantidad + 15,
                        columnaFin = 5,
                        tituloPieChart = "Tickets resueltos \n Desde ${fechaInicial.value} " + if (fechaFinal.value.isNotEmpty()) " hasta ${fechaFinal.value}" else "",
                        categorias = nombresTecnicosYTickets.toTypedArray(),
                        valores = numeroTicketsPorTecnico.toTypedArray()
                    )

                    val tipoTicktes = listOf(
                        listaAcciones.value.count { it.ticket.tipo.id == 1 },
                        listaAcciones.value.count { it.ticket.tipo.id == 2 },
                        listaAcciones.value.count { it.ticket.tipo.id == 3 },
                        listaAcciones.value.count { it.ticket.tipo.id == 4 },
                    )

                    POI.generarPieChart(
                        sheet = sheet,
                        filaComienzo = cantidad + 2,
                        columnaComienzo = 6,
                        filaFin = cantidad + 15,
                        columnaFin = 9,
                        tituloPieChart = "Tickets por tipo \n Desde ${fechaInicial.value} " + if (fechaFinal.value.isNotEmpty()) " hasta ${fechaFinal.value}" else "",
                        categorias = arrayOf(
                            "Incidencia:\n ${tipoTicktes[0]} tickets",
                            "Solicitud:\n ${tipoTicktes[1]} tickets",
                            "Mantenimiento:\n ${tipoTicktes[2]} tickets",
                            "Control de cambio:\n ${tipoTicktes[3]} tickets"
                        ),
                        valores = tipoTicktes.toTypedArray()
                    )

                }
            )
            Notification().mostrarNotificacion(
                context,
                "AVANTI - Gestión de Incidencias: Informe",
                "Se guardó el informe en la carpeta de Documentos."
            )

        }

    }

}