package com.example.avantitigestiondeincidencias.ui.screens.perfil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ui.screens.administrador.EncabezadoInformacionUsuario
import com.example.avantitigestiondeincidencias.ui.screens.ticket.fuenteLetraTicketDesplegado
import io.ktor.util.toUpperCasePreservingASCIIRules

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacionTecnico(tecnico: Tecnico)
{

    Column(modifier = Modifier)
    {
        EncabezadoInformacionUsuario(
            R.drawable.screwdriver_wrench_solid,
            "Técnico"
        )

        Text(text = "TÉCNICO: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = "${tecnico.id}",
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        //Informacion personal del empleado
        informacionPersonalEmpleado(tecnico.empleado)

        Text(text = "ÁREA: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = tecnico.grupoAtencion.grupoAtencion.toUpperCasePreservingASCIIRules(),
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        InformacionLaboralEmpleado(tecnico.empleado)
        Spacer(modifier = Modifier.padding(15.dp))
    }

}