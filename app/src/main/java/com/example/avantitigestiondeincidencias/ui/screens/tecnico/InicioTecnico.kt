package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.R
import android.widget.Toolbar
import androidx.annotation.Dimension
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
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
         mutableStateListOf<Map<String, String>>(
             mapOf(
                 "nombreCliente" to "nombre del cliente",
                 "pisoDepartamento" to "Piso y departamento",
                 "descripcionTicket" to "Descripcion del ticket",
                 "pioridadTicket" to "BAJA",
                 "fechaHora" to "Fecha y hora"
             ),
             mapOf(
                 "nombreCliente" to "nombre del cliente",
                 "pisoDepartamento" to "Piso y departamento",
                 "descripcionTicket" to "Descripcion del ticket",
                 "pioridadTicket" to "BAJA",
                 "fechaHora" to "Fecha y hora"
             ),
             mapOf(
                 "nombreCliente" to "nombre del cliente",
                 "pisoDepartamento" to "Piso y departamento",
                 "descripcionTicket" to "Descripcion del ticket",
                 "pioridadTicket" to "BAJA",
                 "fechaHora" to "Fecha y hora"
             ),
             mapOf(
                 "nombreCliente" to "nombre del cliente",
                 "pisoDepartamento" to "Piso y departamento",
                 "descripcionTicket" to "Descripcion del ticket",
                 "pioridadTicket" to "BAJA",
                 "fechaHora" to "Fecha y hora"
             )

         )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue
                ),
                title = {
                    Text("Inicio")
                }
            )
        }
    )
    {

        Column(modifier = paddingPantallas)
        {
            Spacer(modifier = Modifier.padding(35.dp))
            Column(modifier = Modifier.fillMaxWidth().height(270.dp).background(Color.Red))
            {
                Text("Bienvenido")

            }
            Spacer(modifier = espacioSpacer)
            Row(modifier = Modifier.fillMaxWidth().height(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween)
            {

                cuadroNumeroTicketsTecnico(100, "Tickets del dia", Color.Yellow)
                cuadroNumeroTicketsTecnico(100, "Tickets abiertos", Color.Green)
                cuadroNumeroTicketsTecnico(100, "Ticket Urgentes", Color.Red)

            }
            Spacer(modifier = espacioSpacer)
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight())
            {
                Text(text = "Ultimos tickets:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())

                LazyColumn(modifier = Modifier.fillMaxWidth(),
                    content = {

                        items(datasetPruebas.count())
                        { index ->

                            ultimosTicketsLazyColumnContent()

                        }

                    })
            }
        }

    }

}

@Composable
fun graficaTprtaTecnico(numero1: Int, numero2: Int, numero3: Int)
{
    val suma = numero1+numero2+numero3
    val grados1=numero1.toFloat()/suma.toFloat()*360
    val grados2=numero2.toFloat()/suma.toFloat()*360
    val grados3=numero3.toFloat()/suma.toFloat()*360
    /*
    Canvas(modifier = Modifier.fillMaxSize()) {
        val diametro=if(size.width>size.height) size.height else size.width
        drawArc(startAngle = 0f,sweepAngle = grados1,useCenter = true,color= Color.Red,size = Size(diametro,diametro))
        drawArc(startAngle = grados1,sweepAngle = grados2,useCenter = true,color= Color.Green,size = Size(diametro,diametro))
        drawArc(startAngle = grados1+grados2,sweepAngle = grados3,useCenter = true,color= Color.Blue,size = Size(diametro,diametro))
    }
    */
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
fun ultimosTicketsLazyColumnContent()
{

    Column(modifier = Modifier.fillMaxWidth()
                        .padding(0.dp, 5.dp, 0.dp, 5.dp)
                        .border(width = 1.dp, color = Color.Black))
    {

        Row(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
        {
            Column()
            {
                Text(text = "nombre del cliente", fontWeight = FontWeight.Bold, modifier = Modifier.width(270.dp))
                Text(text = "piso y departamento", fontSize = 12.sp)
            }
            Box(modifier = Modifier.padding(1.dp, 1.dp, 1.dp, 1.dp). background(Color.Blue))
            {
                Text(
                    text = "PRIORIDAD",
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
