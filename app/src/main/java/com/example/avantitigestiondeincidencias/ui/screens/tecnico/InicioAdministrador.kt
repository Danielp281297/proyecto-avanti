package com.example.avantitigestiondeincidencias.ui.screens.tecnico


import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

val amarillo = Color(0xFFFDD835)
val verde = Color(0xFF43A047)
val rojo = Color(0xFFD50000)

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun InicioAdministrador(navController: NavController)
{

    val scope = rememberCoroutineScope()

    var dataset = remember {
        mutableStateListOf<Ticket>()
    }

    var urgentesTicketsContador = remember {
        mutableStateOf<Int>(0)
    }

    var cerradosTicketsContador = remember {
        mutableStateOf<Int>(0)
    }

    var pendientesTicketsContador = remember {
        mutableStateOf<Int>(0)
    }

    var ticketstate = remember{
        mutableStateOf(false)
    }

    var pantallaCargaState = remember{
        mutableStateOf(false)
    }

    // Corrutina para obtener los tickets
    LaunchedEffect(Unit)
    {

        withContext(Dispatchers.IO) {
            pantallaCargaState.value = true
            TicketRequests().mostrarTablaTicket { tickets ->

                dataset.addAll(tickets)

            }
            pantallaCargaState.value = false

        }

    }

    if(pantallaCargaState.value)
    {
        PantallaCarga()
    }


    // Se almacenan los valores de para los cuadros de los tickets
    almacenarValoresPieChartAdministrador(dataset){ pendientes, cerrados, urgentes ->

        pendientesTicketsContador.value = pendientes
        cerradosTicketsContador.value = cerrados
        urgentesTicketsContador.value = urgentes

    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.Transparent
                ),
                title = {
                    Text("Inicio", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            )
        },
        //Color de fondo
        containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
    )
    {

        Column(modifier = Modifier.padding(25.dp))
        {
            Spacer(modifier = Modifier.padding(45.dp))

            pieChartTecnico(pendientesTicketsContador.value, cerradosTicketsContador.value, urgentesTicketsContador.value)

            Spacer(modifier = espacioSpacer)
            Column(modifier = Modifier.fillMaxWidth().height(225.dp))
            {
                Text(text = " Últimos tickets: \n",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())

                LazyColumn(modifier = Modifier.fillMaxWidth(),
                    content = {

                        items(dataset.count()) { index ->

                            //Si el ticket no es abierto, se muestra en los ultimos tickets

                                ultimosTicketsLazyColumnContent(dataset[index], ticketstate, navController)
                                Divider()

                        }

                    })
            }
        }

    }

    //Corrutinas para mostrar los cambios en tiempo real
    LaunchedEffect(Unit)
    {

        delay(1000)

        withContext(Dispatchers.IO) {

            TicketRequests().realtimeTicketRequest(scope) { tickets ->

                dataset.clear()
                dataset.addAll(tickets)

            }

            // Se almacenan los valores de para los cuadros de los tickets
            almacenarValoresPieChartAdministrador(dataset){ abiertos, cerrados, urgentes ->

                pendientesTicketsContador.value = abiertos
                cerradosTicketsContador.value = cerrados
                urgentesTicketsContador.value = urgentes

            }

        }

    }


}

@Composable
fun PantallaCarga()
{

    Box(modifier = Modifier.fillMaxSize().background(Color.White))
    {

        Column(modifier = Modifier.align(Alignment.Center))
        {

            Text("CARGANDO", fontWeight = FontWeight.Bold)

        }

    }

}


fun almacenarValoresPieChartAdministrador(dataset: List<Ticket>, lambda: (ticketsPendientes: Int, ticketsCerrados: Int, ticketUrgentes: Int) -> Unit)
{

    var ticketsPendientes: Int = 0
    var ticketsCerrados: Int = 0
    var ticketUrgentes: Int = 0

    dataset.forEach { item ->

        if (item.prioridad.nivel == "URGENTE")
        {
            ticketUrgentes++
        }

        if (item.estado.tipoEstado == "Cerrado")
        {
            ticketsCerrados++
        }
        else ticketsPendientes++

    }

    lambda(ticketsPendientes, ticketsCerrados, ticketUrgentes)

}


@Composable
fun cuadroNumeroTicketsTecnico(numero: Int, titulo: String, color: Color)
{

    Column(modifier = Modifier.padding().fillMaxHeight().width(100.dp).border(width = 2.dp, color = color, shape = RoundedCornerShape(10.dp)),
        verticalArrangement = Arrangement.Center)
    {
        Text(text = numero.toString(), color = color, fontWeight = FontWeight.Bold, fontSize = 35.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Text(text = titulo, color = color, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(0.dp), fontSize = 14.sp)
    }

}

@Composable
fun pieChartCajas(numero1: Int, numero2: Int, numero3: Int)
{
    Row(modifier = Modifier.fillMaxWidth().height(120.dp),
        horizontalArrangement = Arrangement.SpaceBetween)
    {

        cuadroNumeroTicketsTecnico(numero1, "Tickets\nPendientes", amarillo)
        cuadroNumeroTicketsTecnico(numero2, "Tickets\nCerrados", verde)
        cuadroNumeroTicketsTecnico(numero3, "Tickets\nUrgentes", rojo)

    }
}

@Composable
fun pieChartTecnico(abiertosTicketContador: Int, cerradosTicketContador: Int, urgentesTicketContador: Int)
{
    val suma = abiertosTicketContador+cerradosTicketContador+urgentesTicketContador
    val grados1: Float = abiertosTicketContador.toFloat()/suma.toFloat()*360
    val grados2: Float = cerradosTicketContador.toFloat()/suma.toFloat()*360
    val grados3: Float = urgentesTicketContador.toFloat()/suma.toFloat()*360
    val grosorLinea = 40F

    MaterialTheme{

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            androidx.compose.foundation.Canvas(modifier = Modifier.size(140.dp))
            {
                drawArc(
                    color = amarillo,
                    startAngle = 0F,
                    sweepAngle = grados1,
                    useCenter = false,
                    style = Stroke(
                        width = grosorLinea,
                        cap = StrokeCap.Square,      // Le oculta el fondo de la franja
                        join = StrokeJoin.Round    // Oculta los laterales de la franja
                    )
                )

                drawArc(
                    color = verde,
                    startAngle = grados1,
                    sweepAngle = grados2,
                    useCenter = false,
                    style = Stroke(
                        width = grosorLinea,
                        cap = StrokeCap.Square,
                        join = StrokeJoin.Round
                    )
                )

                drawArc(
                    color = rojo,
                    startAngle = grados2+grados1,
                    sweepAngle = grados3,
                    useCenter = false,
                    style = Stroke(
                        width = grosorLinea,
                        cap = StrokeCap.Square,
                        join = StrokeJoin.Round
                    )
                )

            }
        }
    }

    Spacer(modifier = Modifier.padding(25.dp))

    pieChartCajas(abiertosTicketContador, cerradosTicketContador, urgentesTicketContador)

}

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun ultimosTicketsLazyColumnContent(ticket: Ticket, ticketstate: MutableState<Boolean>, navController: NavController) {


    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(1.dp, 5.dp, 1.dp, 5.dp)
            .clickable {

                //Se envia el ticket en formato JSON, y cuando se cambie de pantalla, se convierte en objeto Ticket otra vez
                val jsonState = Json.encodeToString(ticket)
                navController.navigate("TicketDesplegadoAdministrador" + "/${jsonState}")


            })
    {

        Row(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
        {
            Column()
            {

                Text(text = ticket.tipo.tipoTicket, fontWeight = FontWeight.Bold, modifier = Modifier.width(210.dp))

                Text(
                    text = "${ticket.clienteInterno.empleado.departamento.sede.nombre}: ${ticket.clienteInterno.empleado.departamento.piso} - ${ticket.clienteInterno.empleado.departamento.nombre}",
                    modifier = Modifier.padding(0.dp),
                    fontSize = 12.sp
                )

            }

            // Prioridad del ticket
            Box(modifier = Modifier.width(200.dp).border(width = 1.dp , shape = RectangleShape, color = Color.Black))
            {
                Text(
                    text = ticket.prioridad.nivel,
                    fontSize = 13.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }



        Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
        {
            Text(text = ticket.descripcion, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "${ticket.fecha} - ${ticket.hora}", fontSize = 12.sp)

            if(ticket.idTecnico > 1)
            {
                Row {
                    Text(text = "Encargado: ", fontWeight = FontWeight.Bold)
                    Text(text = "${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.primerApellido}")
                }

            }

        }

    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        )
@Composable
fun darkModeScreen()
{
    AVANTITIGestionDeIncidenciasTheme {
        //InicioAdministrador()
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    val context = LocalContext.current

    AVANTITIGestionDeIncidenciasTheme {

        PantallaCarga()

    }
}
