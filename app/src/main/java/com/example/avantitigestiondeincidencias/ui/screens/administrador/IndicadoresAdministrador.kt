package com.example.avantitigestiondeincidencias.ui.screens.administrador

import java.time.Duration
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.FormatoHoraFecha.FormatoHoraFecha
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
import com.example.avantitigestiondeincidencias.Supabase.TecnicoRequest
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonCargaPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.DatePicker
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ValoresPieChartsAdministrador
import com.example.avantitigestiondeincidencias.ui.screens.componentes.colorControlCambio
import com.example.avantitigestiondeincidencias.ui.screens.componentes.colorIncidencias
import com.example.avantitigestiondeincidencias.ui.screens.componentes.colorMantenimiento
import com.example.avantitigestiondeincidencias.ui.screens.componentes.colorSolicitudes
import com.example.avantitigestiondeincidencias.ui.screens.componentes.colorTicketAbierto
import com.example.avantitigestiondeincidencias.ui.screens.componentes.colorTicketCancelados
import com.example.avantitigestiondeincidencias.ui.screens.componentes.colorTicketCerrados
import com.example.avantitigestiondeincidencias.ui.screens.componentes.colorTicketPendiente
import com.example.avantitigestiondeincidencias.ui.screens.componentes.pieChart
import com.example.avantitigestiondeincidencias.ui.screens.componentes.pieChartCajas
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.apache.poi.xddf.usermodel.chart.ChartTypes
import org.apache.poi.xddf.usermodel.chart.LegendPosition
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory
import org.apache.poi.xssf.usermodel.XSSFDrawing
import org.apache.poi.xssf.usermodel.XSSFSheet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Fecha(val fecha: String, val hora: String)
data class RangoFecha(val fechaInicio: Fecha, val fechaFin: Fecha)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndicadoresAdministrador(
    navController: NavController,
    context: Context,
    administrador: Tecnico,
    containerColor: Color = Color.Transparent) {

    val scrollState = rememberScrollState()

    var tecnicosList = remember {
        mutableListOf<String>("TODOS")
    }

    var mostrarSpinnerState = remember {
        mutableStateOf(false)
    }

    var showDatePickerState = remember {
        mutableStateOf(false)
    }

    var fechaInicioState = remember {
        mutableStateOf<String>("")
    }

    var fechaFinalState = remember {
        mutableStateOf<String>("")
    }

    var banderaState = remember {
        mutableStateOf(0)
    }

    var habilitarCargarDatosState = remember {
        mutableStateOf(false)
    }

    var generarIndicadoresState = remember {
        mutableStateOf(false)
    }

    var generarInformeIndicadoresState = remember {
        mutableStateOf(false)
    }

    var ticketsAbiertosContador = remember {
        mutableStateOf(0)
    }

    var ticketsPendientesContador = remember {
        mutableStateOf(0)
    }

    var ticketsCerradosContador = remember {
        mutableStateOf(0)
    }

    var ticketsCanceladosContador = remember {
        mutableStateOf(0)
    }

    var ticketsIncidenciasContador = remember {
        mutableStateOf(0)
    }
    var ticketsSolicitudesContador = remember {
        mutableStateOf(0)
    }
    var ticketsMantenimientoContador = remember {
        mutableStateOf(0)
    }
    var ticketsControlCambioContador = remember {
        mutableStateOf(0)
    }

    var promedioCalificacionTicket = remember{
        mutableStateOf(0F)
    }

    var primedioTiempoResolucion = remember{
        mutableStateOf<Duration?>(Duration.ZERO)
    }

    var datasetTicket = remember {
        mutableStateListOf<Ticket>()
    }

    var datasetAccion = remember {
        mutableStateListOf<Accion>()
    }

    LaunchedEffect(Unit)
    {
        CoroutineScope(Dispatchers.IO).launch {
            TecnicoRequest().seleccionarTecnicos { tecnicos ->

                tecnicos.forEach { item ->

                    tecnicosList.add("${item.empleado.primerNombre} ${item.empleado.primerApellido} - ${item.grupoAtencion.grupoAtencion}")
                    mostrarSpinnerState.value = true

                }

            }
        }
    }

    ScaffoldSimplePersonalizado(
        tituloPantalla = "Usuarios",
        containerColor = containerColor
    )
    {

        Column(modifier = Modifier
            .padding(25.dp)
            .verticalScroll(scrollState, true))
        {
            Spacer(modifier = Modifier.padding(45.dp))

            Text(
                "Ingrese los datos correspondientes",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Row(modifier = Modifier.fillMaxWidth())
            {

                Column(modifier = Modifier.weight(1F))
                {

                    Text("Fecha Inicial:")
                    Column(modifier = Modifier.clickable {
                        showDatePickerState.value = true
                        banderaState.value = 1
                    })
                    {

                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = fechaInicioState.value,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )

                        HorizontalDivider(thickness = 1.dp)
                    }

                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column(modifier = Modifier.weight(1F))
                {
                    Text("Fecha Final:")
                    Column(modifier = Modifier.clickable {
                        showDatePickerState.value = true
                        banderaState.value = 2
                    })
                    {
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = fechaFinalState.value,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                        HorizontalDivider(thickness = 1.dp)

                    }

                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
            BotonCargaPersonalizado(
                onClick = {

                    //Se comprueba que las entradas fueran validas
                    generarIndicadoresState.value = true

                },
                isLoading = generarIndicadoresState.value,
                enabled = habilitarCargarDatosState.value,
                CuerpoBoton = {
                    Text(text = "CARGAR DATOS", fontFamily = montserratFamily)
                }
            )

            Spacer(modifier = Modifier.padding(5.dp))

            if(datasetTicket.isNotEmpty()) {

                Spacer(modifier = Modifier.padding(5.dp))
                Text("Total de tickets: \n ${datasetTicket.count()}", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.padding(5.dp))
                Text("Estados de tickets", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier.fillMaxWidth())
                {
                    pieChart(
                        ticketsAbiertosContador.value,
                        ticketsPendientesContador.value,
                        ticketsCerradosContador.value,
                        ticketsCanceladosContador.value,
                        color1 = colorTicketAbierto,
                        color2 = colorTicketPendiente,
                        color3 = colorTicketCerrados,
                        color4 = colorTicketCancelados
                    ){ abiertos, pendientes, cerrados, cancelados ->

                        pieChartCajas(
                            abiertos, pendientes, cerrados, cancelados,
                            leyenda1 = "Tickets\nAbiertos",
                            leyenda2 = "Tickets\nPendientes",
                            leyenda3 = "Tickets\nCerrados",
                            leyenda4 = "Tickets\nCancelados",
                            color1 = colorTicketAbierto,
                            color2 = colorTicketPendiente,
                            color3 = colorTicketCerrados,
                            color4 = colorTicketCancelados
                        )

                    }

                }
                Spacer(modifier = Modifier.padding(5.dp))
                Text("Tipos de tickets", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier.fillMaxWidth())
                {
                    pieChart(
                        ticketsIncidenciasContador.value,
                        ticketsSolicitudesContador.value,
                        ticketsMantenimientoContador.value,
                        ticketsControlCambioContador.value,
                        color1 = colorIncidencias,
                        color2 = colorSolicitudes,
                        color3 = colorMantenimiento,
                        color4 = colorControlCambio
                    ){ incidencias, solicitudes, mantenimiento, controlCambio ->

                        pieChartCajas(
                            incidencias, solicitudes, mantenimiento, controlCambio,
                            leyenda1 = "Incidencia",
                            leyenda2 = "Solicitud",
                            leyenda3 = "Manteni-\nmiento",
                            leyenda4 = "Control de\ncambio",
                            color1 = colorIncidencias,
                            color2 = colorSolicitudes,
                            color3 = colorMantenimiento,
                            color4 = colorControlCambio
                        )

                    }

                }
                Row(modifier = Modifier.fillMaxWidth())
                {
                    promedioTiempoPantalla(Modifier
                        .height(100.dp)
                        .weight(1F),
                        primedioTiempoResolucion.value)
                    Spacer(modifier = Modifier.padding(5.dp))
                    promedioCalificacionPantalla(Modifier
                        .height(100.dp)
                        .weight(1F),
                        promedioCalificacionTicket.value)

                }
                Spacer(modifier = Modifier.padding(5.dp))
                BotonCargaPersonalizado(onClick = {
                    generarInformeIndicadoresState.value = true
                },
                    isLoading = generarIndicadoresState.value,
                    enabled = habilitarCargarDatosState.value)
                {
                    Text(text = "GENERAR INFORME", fontFamily = montserratFamily)
                }

            }

            Spacer(modifier = Modifier.padding(55.dp))
        }

    }

    if(fechaInicioState.value.isNotEmpty() || fechaFinalState.value.isNotEmpty())
    {
        habilitarCargarDatosState.value = true
    }

    if (generarIndicadoresState.value) {

        datasetTicket.clear()
        //Se obtienen los datos y se actualiza los tickets para los indicadores
        //buscarTicketByIdTecnicoyRangoFechas(tecnicosListState.value, fechaInicioState.value, fechaFinalState.value){ tickets ->
        //    dataset.addAll(tickets)
        //}
        datosTicketsYAcciones(fechaInicioState.value, fechaFinalState.value, { tickets ->
            datasetTicket.addAll(tickets)

            // Se obtienen los datos de la tabla
            ValoresPieChartsAdministrador(datasetTicket){ abiertos, pendientes, cerrados, cancelados, incidencias, solicitudes, mantenimiento, controlCambio ->

                ticketsAbiertosContador.value   = abiertos
                ticketsPendientesContador.value = pendientes
                ticketsCerradosContador.value   = cerrados
                ticketsCanceladosContador.value = cancelados

                ticketsIncidenciasContador.value = incidencias
                ticketsSolicitudesContador.value = solicitudes
                ticketsMantenimientoContador.value = mantenimiento
                ticketsControlCambioContador.value = controlCambio

            }

            promedioCalificacionTicket.value = promedioCalificacionTickets(datasetTicket)

        },
            { acciones ->
                //Se obtiene el promedio de calificación de la gestion de incidencias
                datasetAccion.clear()
                datasetAccion.addAll(acciones)
                primedioTiempoResolucion.value = promedioTiempoResolucion(datasetAccion)

            })
        
        generarIndicadoresState.value = false
    }

    if (showDatePickerState.value)
    {
        var datePickerInput = ""


        DatePicker(
            showDialog = showDatePickerState.value,
            containerColor = containerColor,
            ondismiss = { showDatePickerState.value = false },
            fecha = {
                datePickerInput = it

                if (banderaState.value == 1) {
                    fechaInicioState.value = datePickerInput
                } else if (banderaState.value == 2) {
                    fechaFinalState.value = datePickerInput
                }

                showDatePickerState.value = false
            })

    }

    if(generarInformeIndicadoresState.value)
    {

        generarInformeIndicadores(
            context,
            fechaInicioState.value,
            fechaFinalState.value,
            //ticketsIncidenciasContador.value,
            //ticketsSolicitudesContador.value,
            //ticketsMantenimientoContador.value,
            //ticketsControlCambioContador.value,
            datasetAccion)
        generarInformeIndicadoresState.value = false
    }

}

fun generarInformeIndicadores(
    context: Context,
    fechaInicio: String,
    fechaFin: String,
    acciones: List<Accion>)
{

    if (acciones.isNotEmpty())
    {

        // Se obtienen los datos de los tecnicos en la lista de las acciones
        val tecnicos = acciones.map { it.ticket.tecnico }.toSet().toList()

        var numeroTicketsPorTecnico = mutableListOf<Int>()

        tecnicos.forEach { tecnico ->
            numeroTicketsPorTecnico.add(acciones.count{ it.ticket.tecnico == tecnico })
        }

        var nombresTecnicosYTickets = mutableListOf<String>()
        var i = 0
        tecnicos.forEach {tecnico ->

            nombresTecnicosYTickets.add("${tecnico.empleado.primerNombre} ${tecnico.empleado.primerApellido}: \n ${numeroTicketsPorTecnico[i]} Tickets")
            i++
        }

        _root_ide_package_.com.example.avantitigestiondeincidencias.ui.screens.tecnico.generalInformeExcel(
            acciones = acciones,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            adicional = { sheet, cantidad ->

                // Se crea la grafica de tortas, en base a los tickets y los tecnicos
                generarPieChart(
                    sheet = sheet,
                    filaComienzo = cantidad + 2,
                    columnaComienzo = 2,
                    filaFin = cantidad + 15,
                    columnaFin = 5,
                    tituloPieChart = "Tickets resueltos \n Desde $fechaInicio " + if (fechaFin.isNotEmpty()) " hasta $fechaFin" else "",
                    categorias = nombresTecnicosYTickets.toTypedArray(),
                    valores = numeroTicketsPorTecnico.toTypedArray()
                )

                val tipoTicktes = listOf(
                    acciones.count { it.ticket.tipo.id == 1 },
                    acciones.count { it.ticket.tipo.id == 2 },
                    acciones.count { it.ticket.tipo.id == 3 },
                    acciones.count { it.ticket.tipo.id == 4 },
                )

                generarPieChart(
                    sheet = sheet,
                    filaComienzo = cantidad + 2,
                    columnaComienzo = 6,
                    filaFin = cantidad + 15,
                    columnaFin = 9,
                    tituloPieChart = "Tickets por tipo \n Desde $fechaInicio " + if (fechaFin.isNotEmpty()) " hasta $fechaFin" else "",
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

fun generarPieChart(
    sheet: XSSFSheet?,
    filaComienzo: Int,
    columnaComienzo: Int,
    filaFin: Int,
    columnaFin: Int,
    tituloPieChart: String,
    categorias: Array<String>,
    valores: Array<Int>,

){
    val drawing: XSSFDrawing = sheet!!.createDrawingPatriarch()
    val anchor = drawing.
    createAnchor(0,
        0,
        0,
        0,
        columnaComienzo,  // Columna donde comienza el grafico
        filaComienzo, // Fila donde comienza el grafico
        columnaFin,   // Cantidad de columnas que conforma el grafico
        filaFin  // Cantidad de filas que conforma el grafico
    )

    val chart = drawing.createChart(anchor)
    chart.setTitleText(tituloPieChart)
    chart.titleOverlay = false

    val legend = chart.orAddLegend
    legend.position = LegendPosition.TOP_RIGHT


    val categorias = XDDFDataSourcesFactory.
    fromArray(categorias)

    val valores = XDDFDataSourcesFactory.
    fromArray(valores)

    val data = chart.createData(ChartTypes.PIE, null, null)
    data.setVaryColors(true)

    val serie = data.addSeries(categorias, valores)
    serie.setTitle(null, null)

    chart.plot(data)
}

fun promedioCalificacionTickets(dataset: List<Ticket>): Float {

    var sumatoria = 0
    var promedio = 0F

    dataset.forEach {
        sumatoria += it.calificacionGestionTicket
    }

    promedio = sumatoria / dataset.count().toFloat()

    return promedio

}

@Composable
fun promedioCalificacionPantalla(modifier: Modifier, promedio: Float)
{

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {

        Text(
            "Promedio de Calificación",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(promedio.toString(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun promedioTiempoPantalla(modifier: Modifier, promedio: Duration?)
{

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {

        Text(
            "Tiempo Promedio de Resolución",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            "%02d:%02d:%02d".format(promedio!!.toHours(),
                promedio.toMinutes() % 60,
                promedio.toSeconds() % 60),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

fun promedioTiempoResolucion(dataset: List<Accion>): Duration? {

    var fechaHora = RangoFecha(
        fechaInicio = Fecha(
            fecha = "",
            hora = ""
        ),
        fechaFin = Fecha(
            fecha = "",
            hora = ""
        )
    )

    var sumatoria = Duration.ZERO

    dataset.forEach { accion ->

        fechaHora = RangoFecha(
            fechaInicio = Fecha(
                fecha = FormatoHoraFecha.formatoFecha(accion.ticket.fechaAsignacion).toString(),
                hora = FormatoHoraFecha.formatoHora(accion.ticket.horaAsignacion)
            ),
            fechaFin = Fecha(
                fecha = accion.fecha,
                hora = FormatoHoraFecha.formatoHora(accion.hora)
            )
        )

        Log.d("Fecha", fechaHora.toString())
        sumatoria = sumatoria.plus(duracionFechasMilisegundos(fechaHora))

    }

    return sumatoria.dividedBy(dataset.count().toLong())

}

fun duracionFechasMilisegundos(rangoFecha: RangoFecha): Duration? {

    val formato = "dd-MM-yyyy HH:mm:ss"
    val formateador = DateTimeFormatter.ofPattern(formato)

    val dateTime1 = LocalDateTime.parse("${rangoFecha.fechaInicio.fecha} ${rangoFecha.fechaInicio.hora}", formateador)
    val dateTime2 = LocalDateTime.parse("${rangoFecha.fechaFin.fecha} ${rangoFecha.fechaFin.hora}", formateador)

    val duracion = if (dateTime2.isAfter(dateTime1)) {
        Duration.between(dateTime1, dateTime2)
    } else {
        Duration.between(dateTime2, dateTime1)
    }

    return duracion
}

@Composable
fun datosTicketsYAcciones(fechaInicio: String, fechaFin: String, tickets: (List<Ticket>) -> Unit, acciones: (List<Accion>) -> Unit){

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            TicketRequests().buscarTicketsByTecnicoIdYFechas(1, fechaInicio, fechaFin, "") {

                tickets(it)

            }
            delay(1000)

            AccionRequest().seleccionarAccionesbyRangoFechas(fechaInicio, fechaFin) {

                acciones(it)

            }

        }
    }

}

@Composable
fun buscarTicketByIdTecnicoyRangoFechas(
    id: Int,
    fechaInicio: String,
    fechaFin: String,
    resultados: (List<Ticket>) -> Unit
) {

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            // Si no hay tecnicos que buscar, se extraen todos los ticket dentro del rango de fechas
            if(id == 0)
            {

                TicketRequests().mostrarTablaTicket {

                    resultados(it)

                }

            }
            else if (id > 0) {
                TicketRequests().seleccionarTicketsByIdTecnicoYRangoFechas(
                    idTecnico = id,
                    inicioFecha = fechaInicio,
                    finFecha = fechaFin
                ) {

                    resultados(it)

                }
            }


        }
    }

}

@Preview(showBackground = true)
@Composable
fun IndicadoresAdministradorPreview() {

    val context = LocalContext.current

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {
        IndicadoresAdministrador(navController, context, Tecnico())
    }
}
