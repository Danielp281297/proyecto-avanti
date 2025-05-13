package com.example.avantitigestiondeincidencias.ui.screens.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import kotlin.collections.forEach


val rojo = Color(0xFFD50000)

val colorTicketAbierto = Color(0xFFFF9F00)
val colorTicketPendiente = Color(0xFFF4631E)
val colorTicketCerrados = Color(0xFF309898)
val colorTicketCancelados = Color(0xFF9E9E9E)

val colorIncidencias = Color(0xFFE52020)
val colorSolicitudes = Color(0xFFFBA518)
val colorMantenimiento = Color(0xFFF9CB43)
val colorControlCambio = Color(0xFFA89C29)

@Composable
fun pieChart(
      valor1: Int,
      valor2: Int,
      valor3: Int,
      valor4: Int,
      color1: Color,
      color2: Color,
      color3: Color,
      color4: Color,
      cajas: @Composable (Int, Int, Int, Int) -> Unit)
{
    val suma = valor1+valor2+valor3+valor4
    val grados1: Float = valor1.toFloat()/suma.toFloat()*360
    val grados2: Float = valor2.toFloat()/suma.toFloat()*360
    val grados3: Float = valor3.toFloat()/suma.toFloat()*360
    val grados4: Float = valor4.toFloat()/suma.toFloat()*360
    val grosorLinea = 40F

    MaterialTheme{

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            androidx.compose.foundation.Canvas(modifier = Modifier.size(140.dp))
            {
                drawArc(
                    color = color1,
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
                    color = color2,
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
                    color = color3,
                    startAngle = grados2+grados1,
                    sweepAngle = grados3,
                    useCenter = false,
                    style = Stroke(
                        width = grosorLinea,
                        cap = StrokeCap.Square,
                        join = StrokeJoin.Round
                    )
                )

                drawArc(
                    color = color4,
                    startAngle = grados3+grados2+grados1,
                    sweepAngle = grados4,
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
    cajas(valor1, valor2, valor3, valor4)
    //pieChartCajasTipoTickets(ticketsAbiertosContador, ticketsPendientesContador, ticketsCerradosContador, ticketsCanceladosContador)

}

@Composable
fun pieChartCajas(
    numero1: Int,
    numero2: Int,
    numero3: Int,
    numero4: Int,
    leyenda1: String,
    leyenda2: String,
    leyenda3: String,
    leyenda4: String,
    color1: Color,
    color2: Color,
    color3: Color,
    color4: Color,
    )
{
    Row(modifier = Modifier.fillMaxWidth().height(120.dp),
        horizontalArrangement = Arrangement.SpaceBetween)
    {

        cartaTickets(numero1, leyenda1, color1)
        cartaTickets(numero2, leyenda2, color2)
        cartaTickets(numero3, leyenda3, color3)
        cartaTickets(numero4, leyenda4, color4)

    }
}

@Composable
fun cartaTickets(numero: Int, titulo: String, color: Color)
{

    Column(modifier = Modifier.wrapContentWidth().width(80.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp))
    {
        Text(text = numero.toString(), color = color, fontWeight = FontWeight.Bold, fontSize = 30.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Text(text = titulo, color = color, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(0.dp), fontSize = 12.sp)
    }

}

fun ValoresPieChartsAdministrador(dataset: List<Ticket>,
                                          lambda: (ticketsAbiertos: Int,
                                                   ticketsPendientes: Int,
                                                   ticketsCerrados: Int,
                                                   ticketsCancelados: Int,
                                                   ticketsIndicencias: Int,
                                                   ticketsSolicitudes: Int,
                                                   ticketsMantenimieto: Int,
                                                   ticketsControlCambio: Int) -> Unit)
{

    var ticketsAbiertosContador = 0
    var ticketsPendientesContador = 0
    var ticketsCerradosContador = 0
    var ticketsCanceladosContador = 0

    var ticketsIncidenciasContador = 0
    var ticketsSolicitudesContador = 0
    var ticketsMantenimientoContador = 0
    var ticketsControlCambioContador = 0

    dataset.forEach { ticket ->

        when(ticket.idEstadoTicket)
        {
            1 -> ticketsAbiertosContador++
            3 -> ticketsPendientesContador++
            5 -> ticketsCerradosContador++
            6 -> ticketsCanceladosContador++
        }

        when(ticket.idTipoTicket)
        {
            1 -> ticketsIncidenciasContador++
            2 -> ticketsSolicitudesContador++
            3 -> ticketsMantenimientoContador++
            4 -> ticketsControlCambioContador++
        }

    }

    lambda(ticketsAbiertosContador,
        ticketsPendientesContador,
        ticketsCerradosContador,
        ticketsCanceladosContador,
        ticketsIncidenciasContador,
        ticketsSolicitudesContador,
        ticketsMantenimientoContador,
        ticketsControlCambioContador)

}
