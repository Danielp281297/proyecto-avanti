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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.FormatoHoraFecha.Conversor
import com.example.avantitigestiondeincidencias.FormatoHoraFecha.Conversor.Companion.duracionFechasMilisegundos
import com.example.avantitigestiondeincidencias.FormatoHoraFecha.FormatoHoraFecha
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.POI.POI
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
import com.example.avantitigestiondeincidencias.Supabase.TecnicoRequest
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.ViewModel.IndicadoresAdministradorViewModel
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



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndicadoresAdministrador(
    navController: NavController,
    context: Context,
    administrador: Tecnico,
    containerColor: Color = Color.Transparent,
    viewModel: IndicadoresAdministradorViewModel = viewModel()
) {

    val scrollState = rememberScrollState()

    val fechaFinal = viewModel.fechaFinal.collectAsState()
    val fechaInicial = viewModel.fechaInicial.collectAsState()
    val listaTickets = viewModel.listaTickets.collectAsState()

    var showDatePickerState = remember {
        mutableStateOf(false)
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

    val ticketsAbiertosContador = viewModel.ticketsAbiertosContador.collectAsState()

    val ticketsPendientesContador = viewModel.ticketsPendientesContador.collectAsState()
    val ticketsCerradosContador = viewModel.ticketsCerradosContador.collectAsState()
    val ticketsCanceladosContador = viewModel.ticketsCanceladosContador.collectAsState()
    val ticketsIncidenciasContador = viewModel.ticketsIncidenciasContador.collectAsState()
    val ticketsSolicitudesContador = viewModel.ticketsSolicitudesContador.collectAsState()
    val ticketsMantenimientoContador = viewModel.ticketsMantenimientoContador.collectAsState()
    val ticketsControlCambioContador = viewModel.ticketsControlCambioContador.collectAsState()
    val promedioCalificacionTicket = viewModel.promedioCalificacionTicket.collectAsState()

    var promedioTiempoResolucion = viewModel.promedioTiempoResolucion.collectAsState()

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
                            text = fechaInicial.value,
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
                            text = fechaFinal.value,
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

            if(listaTickets.value.isNotEmpty()) {

                Spacer(modifier = Modifier.padding(5.dp))
                Text("Total de tickets: \n ${listaTickets.value.count()}", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

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
                        promedioTiempoResolucion.value)
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

    if(viewModel.validarFechas())
    {

        habilitarCargarDatosState.value = true
    }

    if (generarIndicadoresState.value) {

        ObtenerDatosTicketsYAcciones(viewModel)
        
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
                    viewModel.setFechaInicial(datePickerInput)
                } else if (banderaState.value == 2) {
                    viewModel.setFechaFinal(datePickerInput)
                }

                showDatePickerState.value = false
            })

    }

    if(generarInformeIndicadoresState.value)
    {
        viewModel.generarInforme(context)
        generarInformeIndicadoresState.value = false
    }

}

@Composable
fun ObtenerDatosTicketsYAcciones(
    viewModel: IndicadoresAdministradorViewModel
){


    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            viewModel.obtenerTicketsResueltos()
            delay(1000)

        }
    }

}
/*
fun promedioCalificacionTickets(dataset: List<Ticket>): Float {

    var sumatoria = 0
    var promedio = 0F

    dataset.forEach {
        sumatoria += it.calificacionGestionTicket
    }

    promedio = sumatoria / dataset.count().toFloat()

    return promedio

}
*/

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

/*
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
*/

@Preview(showBackground = true)
@Composable
fun IndicadoresAdministradorPreview() {

    val context = LocalContext.current

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {
        IndicadoresAdministrador(navController, context, Tecnico())
    }
}
