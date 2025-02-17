package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

@Composable
fun TicketDesplegado(ticket: Ticket) {

    Column(modifier = Modifier.fillMaxWidth().padding(1.dp, 5.dp, 1.dp, 5.dp).border(1.dp, Color.Black).background(Color.White))
    {

        Text(text = "${ticket.tipo}", modifier = Modifier.padding(0.dp, 10.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp)

        Text(text = " Ticket: ${ticket.id}") // Numero de ticket
        Text(text = " Fecha: ${ticket.fecha}")
        Text(text = " Hora: ${ticket.hora}")
        Text(text = " Tipo de ticket: ${ticket.tipo}")
        Text(text = " Descripción: ${ticket.descripcion}")
        Text(text = " Estado: ${ticket.estado}")
        Text(text = " Técnico Encargado: ${if(ticket.nombreTecnico != "AVANTI BY FRIGILUX,") ticket.nombreTecnico else " "} ")

        Spacer(modifier = espacioSpacer)

        Button(modifier = modeloButton,

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            shape = RectangleShape,
            onClick = {

            }
        )
        {
            Text(text = "BORRAR TICKET", color = Color.White)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TicketDessplegadoPreview() {
    AVANTITIGestionDeIncidenciasTheme {

        TicketDesplegado(Ticket())

    }
}
