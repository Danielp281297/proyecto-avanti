package com.example.avantitigestiondeincidencias.ui.screens.manuales

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.R

@Composable
fun Presentacion()
{

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        Icon(imageVector = ImageVector.Companion.vectorResource(R.drawable.logo),
            contentDescription = "Logo de Avanti",
            modifier = Modifier.size(144.dp).align(Alignment.CenterHorizontally))
        androidx.compose.material3.Text(
            "AVANTI BY FRIGILUX, C.A. \n J-501548218",
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        androidx.compose.material3.Text(
            "Gestión de incidencias",
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.padding(15.dp))
        TituloManual("Presentación")
        TextoManual("La aplicación para la gestión de incidencias de la empresa GALERÍAS AVANTI, constituida como AVANTI BY FRIGILUX, C.A.\n" +
                "\n" +
                " Esta es una aplicación desarrollada para usted, empleado de la misma; con una interfaz de usuario que asemeja la experiencia de usuario de la marca, y la utilización de base de datos con arquitectura cliente-servidor que permite el acceso a la información en tiempo real.\n" +
                "\n" +
                "Daniel Padilla.\n")

    }

}
