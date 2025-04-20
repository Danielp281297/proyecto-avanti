package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.modeloButton
import io.ktor.util.toUpperCasePreservingASCIIRules

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacionTecnico(tecnico: Tecnico)
{

    val scrollState = rememberScrollState()

    Column(modifier = Modifier)
    {
        EncabezadoInformacionUsuario(
            com.example.avantitigestiondeincidencias.R.drawable.screwdriver_wrench_solid,
            "Técnico"
        )

        Text(text = "TÉCNICO: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = "${tecnico.id}",
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

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