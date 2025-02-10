package com.example.avantitigestiondeincidencias.ui.screens.tecnico


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.paddingPantallas
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inicioTecnico()
{
    val datasetPruebas = remember {
         mutableStateListOf(
             mapOf(
                 "nombreCliente" to "nombre del cliente",
                 "pisoDepartamento" to "Piso y departamento",
                 "descripcionTicket" to "Descripcion del ticket",
                 "pioridadTicket" to "BAJA",
                 "fechaHora" to "Fecha y hora"
             ),
             mapOf(
                 "nombreCliente" to "nombre del cliente 2",
                 "pisoDepartamento" to "Piso y departamento",
                 "descripcionTicket" to "Descripcion del ticket",
                 "prioridadTicket" to "MEDIA",
                 "fechaHora" to "Fecha y hora"
             ),
             mapOf(
                 "nombreCliente" to "nombre del cliente 3",
                 "pisoDepartamento" to "Piso y departamento",
                 "descripcionTicket" to "Descripcion del ticket",
                 "prioridadTicket" to "ALTA",
                 "fechaHora" to "Fecha y hora"
             ),
             mapOf(
                 "nombreCliente" to "nombre del cliente 4",
                 "pisoDepartamento" to "Piso y departamento",
                 "descripcionTicket" to "Descripcion del ticket",
                 "prioridadTicket" to "URGENTE",
                 "fechaHora" to "Fecha y hora"
             )

         )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(

                ),
                title = {
                    Text("Inicio", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            )
        }
    )
    {

        Column(modifier = paddingPantallas)
        {
            Spacer(modifier = Modifier.padding(35.dp))

            pieChartTecnico(40, 120, 10)



            Spacer(modifier = espacioSpacer)
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight())
            {
                Text(text = "\n Últimos tickets: \n",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())

                LazyColumn(modifier = Modifier.fillMaxWidth(),
                    content = {

                        items(datasetPruebas.size)
                        { index ->

                            ultimosTicketsLazyColumnContent(datasetPruebas[index])

                        }

                    })
            }
        }

    }

}

@Composable
fun pieChartCajas(numero1: Int, numero2: Int, numero3: Int)
{
    Row(modifier = Modifier.fillMaxWidth().height(140.dp),
        horizontalArrangement = Arrangement.SpaceBetween)
    {

        cuadroNumeroTicketsTecnico(numero1, "Tickets del dia", Color.Yellow)
        cuadroNumeroTicketsTecnico(numero2, "Tickets abiertos", Color.Green)
        cuadroNumeroTicketsTecnico(numero3, "Ticket Urgentes", Color.Red)

    }
}

@Composable
fun pieChartTecnico(diaTicketContador: Int, abiertosTicketContador: Int, urgentesTicketContador: Int)
{
    val suma = diaTicketContador+abiertosTicketContador+urgentesTicketContador
    val grados1: Float = diaTicketContador.toFloat()/suma.toFloat()*360
    val grados2: Float =abiertosTicketContador.toFloat()/suma.toFloat()*360
    val grados3: Float =urgentesTicketContador.toFloat()/suma.toFloat()*360
    val grosorLinea = 50F

    MaterialTheme{

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            androidx.compose.foundation.Canvas(modifier = Modifier.size(200.dp))
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

    Spacer(modifier = espacioSpacer)

    pieChartCajas(diaTicketContador, abiertosTicketContador, urgentesTicketContador)

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
fun ultimosTicketsLazyColumnContent(ticket: Map<String, String>)
{

    val prioridadColorFondo = remember {
        mutableStateOf(when(ticket["prioridadTicket"]){
            "BAJA" -> Color.Green
            "MEDIA" -> Color.Yellow
            "ALTA" -> Color.Red
            "URGENTE" -> Color(141, 20, 20, 255)
            else -> Color.Green
        })
    }

    Column(modifier = Modifier.fillMaxWidth()
                        .padding(1.dp, 5.dp, 1.dp, 5.dp)
                        //.shadow(elevation = 2.dp)
                        )
    {

        Row(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
        {
            Column()
            {
                Text(text = ticket["nombreCliente"].toString(), fontWeight = FontWeight.Bold, modifier = Modifier.width(270.dp))
                Text(text = "piso y departamento", fontSize = 12.sp)
            }



            Box(modifier = Modifier.padding(0.1.dp, 1.dp, 0.1.dp, 1.dp).background(prioridadColorFondo.value))
            {
                Text(
                    text = ticket["prioridadTicket"].toString(),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(5.dp).fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            }
        Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
        {
            Text(text = "Descripcion del ticket para notificar una situacion desfavorable", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "fecha y hora", fontSize = 12.sp)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AVANTITIGestionDeIncidenciasTheme {

        inicioTecnico()

    }
}
