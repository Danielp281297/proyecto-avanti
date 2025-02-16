package com.example.avantitigestiondeincidencias.ui.screens.tecnico


import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Request.ApiServices
import com.example.avantitigestiondeincidencias.Request.OkHttpRequest
import com.example.avantitigestiondeincidencias.Request.Retrofit
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.inc


@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun InicioAdministrador()
{
    
    var dataset = remember {  //mutableListOf<Ticket>()
        mutableStateListOf<Ticket>()
    }

    var urgentesTicketsContador = remember {
        mutableStateOf<Int>(0)
    }

    var cerradosTicketsContador = remember {
        mutableStateOf<Int>(0)
    }

    var abiertosTicketsContador = remember {
        mutableStateOf<Int>(0)
    }

    // Se guardan los datos de la peticion en la lista dataset
    apiServiceTicketsAbiertos(dataset, { abiertos, cerrados, urgentes ->

        urgentesTicketsContador.value = urgentes
        cerradosTicketsContador.value = cerrados
        abiertosTicketsContador.value = abiertos

    })


    // Se obtiene los datos de la peticion, y se guarda en la dataset


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
        }
    )
    {

        Column(modifier = Modifier.padding(25.dp))
        {
            Spacer(modifier = Modifier.padding(45.dp))

            pieChartTecnico(abiertosTicketsContador.value, cerradosTicketsContador.value, urgentesTicketsContador.value)

            Spacer(modifier = espacioSpacer)
            Column(modifier = Modifier.fillMaxWidth().fillMaxHeight())
            {
                Text(text = " Últimos tickets: \n",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())

                LazyColumn(modifier = Modifier.fillMaxWidth(),
                    content = {

                        items(dataset.count()) { index ->

                            ultimosTicketsLazyColumnContent(dataset[index])

                        }

                    })
            }
        }

    }

}

fun apiServiceTicketsAbiertos(dataset: SnapshotStateList<Ticket>, cifras: (Int, Int, Int) -> Unit)
{

    GlobalScope.launch {

        do {

            var urgentes = 0
            var abiertos = 0
            var cerrados = 0


            Retrofit.seleccionarTickets("http://192.168.0.104/Daniel/IncidenciasAvanti/PHP/seleccionar_tickets.php/",
                { retrofit ->

                    val service = retrofit!!.create(ApiServices::class.java).getTickets()

                    dataset.clear()

                    service.enqueue(object : Callback<List<Ticket>> {
                        override fun onResponse(
                            p0: Call<List<Ticket>?>,
                            response: Response<List<Ticket>?>
                        ) {
                            if (response.isSuccessful) {

                                val apiResponse = response.body()

                                apiResponse?.forEachIndexed {index, ticket ->

                                    // Se comprueba el estado y la prioridad de los tickets
                                    if (ticket.prioridad == "URGENTE")
                                    {
                                        urgentes++
                                    }
                                    else
                                        if (ticket.estado == "Abierto")
                                        {
                                            abiertos++
                                        }
                                        else
                                            if (ticket.estado == "Cerrado")
                                            {
                                                cerrados++
                                            }

                                    cifras(abiertos, cerrados, urgentes)

                                    dataset.add(ticket)

                                }

                            }

                        }

                        override fun onFailure(
                            p0: Call<List<Ticket>?>,
                            p1: Throwable
                        ) {
                            Log.e("Error: ", p1.message.toString())
                        }

                    })

                })

            delay(10000)

        }while (true)


    }
}

@Composable
fun pieChartCajas(numero1: Int, numero2: Int, numero3: Int)
{
    Row(modifier = Modifier.fillMaxWidth().height(120.dp),
        horizontalArrangement = Arrangement.SpaceBetween)
    {

        cuadroNumeroTicketsTecnico(numero1, "Tickets del dia", Color.Yellow)
        cuadroNumeroTicketsTecnico(numero2, "Tickets abiertos", Color.Green)
        cuadroNumeroTicketsTecnico(numero3, "Ticket Urgentes", Color.Red)

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
                    color = Color.Yellow,
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
                    color = Color.Green,
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
                    color = Color.Red,
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

@Composable
fun cuadroNumeroTicketsTecnico(numero: Int, titulo: String, color: Color)
{

    Column(modifier = Modifier.fillMaxHeight().width(100.dp).border(width = 2.dp, color = color, shape = RoundedCornerShape(10.dp)),
        verticalArrangement = Arrangement.Center)
    {
        Text(text = numero.toString(), color = color, fontWeight = FontWeight.Bold, fontSize = 35.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Text(text = titulo, color = color, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), fontSize = 12.sp)
    }

}

@Composable
fun ultimosTicketsLazyColumnContent(ticket: Ticket)
{

    val context = LocalContentColor.current

    val prioridadColorFondo = remember {
        mutableStateOf(when(ticket.prioridad){
            "BAJA" -> Color.Green
            "MEDIA" -> Color.Yellow
            "ALTA" -> Color.Red
            "URGENTE" -> Color(141, 20, 20, 255)
            else -> Color.Green
        })
    }

    Column(modifier = Modifier.fillMaxWidth()
                        .padding(1.dp, 5.dp, 1.dp, 5.dp)
                        .clickable {

                            Log.d("ticket: ", "ticket ${ticket.id} pulsado")

        })
    {

        Row(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
        {
            Column()
            {
                Text(text = ticket.tipo, fontWeight = FontWeight.Bold, modifier = Modifier.width(240.dp))
                Text(text = "${ticket.piso} ${ticket.departamento} ", fontSize = 12.sp)
            }



            Box(modifier = Modifier.padding(0.1.dp, 1.dp, 0.1.dp, 1.dp).background(prioridadColorFondo.value))
            {
                Text(
                    text = ticket.prioridad.toString(),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(5.dp, 5.dp).fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            }
        Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
        {
            Text(text = ticket.descripcion, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "${ticket.fecha} - ${ticket.hora}", fontSize = 12.sp)
        }

    }

}

@Composable
fun toasty(id: Int) {

    val context = LocalContext.current
    Toast.makeText(context, id.toString(), Toast.LENGTH_SHORT).show()

}

@Composable
fun pantallaPrueba()
{

    val okHttpRequest = OkHttpRequest()

    Box(modifier = Modifier.fillMaxSize())
    {
        Button(onClick = {

            //okHttpRequest.peticionGET("http://10.0.2.2/Daniel/IncidenciasAvanti/View/seleccionar_tickets.php")

            Retrofit.seleccionarTickets("http://10.0.2.2/Daniel/IncidenciasAvanti/View/seleccionar_tickets.php/",
                                        { tickets ->

                                            Log.d("Tickets", tickets.toString())

                                        })

        }, content = { Text("PETICION") }, modifier = Modifier.padding(100.dp))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        )
@Composable
fun darkModeScreen()
{
    AVANTITIGestionDeIncidenciasTheme {
        InicioAdministrador()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AVANTITIGestionDeIncidenciasTheme {

        HorizontalPagerBottomBarTecnico()

    }
}
