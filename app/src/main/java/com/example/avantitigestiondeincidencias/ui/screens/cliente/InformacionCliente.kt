package com.example.avantitigestiondeincidencias.ui.screens.cliente

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ui.screens.administrador.EncabezadoInformacionUsuario
import com.example.avantitigestiondeincidencias.ui.screens.perfil.InformacionLaboralEmpleado
import com.example.avantitigestiondeincidencias.ui.screens.ticket.fuenteLetraTicketDesplegado
import com.example.avantitigestiondeincidencias.ui.screens.perfil.informacionPersonalEmpleado

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacionClienteInterno(clienteInterno: ClienteInterno)
{

    Column(modifier = Modifier)
    {
        EncabezadoInformacionUsuario(R.drawable.user_icon, "Cliente Interno")

        Text(text = "CLIENTE INTERNO: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = "${clienteInterno.id}",
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        informacionPersonalEmpleado(clienteInterno.empleado)

        //Spacer(modifier = Modifier.padding(5.dp))

        InformacionLaboralEmpleado(clienteInterno.empleado)

        Spacer(modifier = Modifier.padding(15.dp))

    }

}