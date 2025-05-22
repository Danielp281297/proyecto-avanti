package com.example.avantitigestiondeincidencias.ui.screens.manuales

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaIntroduccion() {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Introducción")
        TextoManual("La aplicación para la gestión de incidencias de la empresa AVANTI BY FRIGILUX, C.A., es una herramienta intuitiva y fácil de usar, a tal punto que no se necesitan conocimientos previos de programación. Todas las funciones para la gestión de incidencias por tickets, están diseñadas para las pantallas táctiles de los dispositivos móviles, con el objetivo de que el usuario tenga un periodo de capacitación.\n" +
                "\n" +
                "Entre las características que tiene la presente aplicación, destacan:\n" +
                "\n" +
                "\t• Diferentes funciones por usuario\n" +
                "\t• Creación de tickets\n" +
                "\t• Asignación de tickets \n" +
                "\t• Priorización de tickets\n" +
                "\t• Generación de informes\n" +
                "\t• Registro de las acciones ejecutadas hacia las incidencias\n" +
                "\n" +
                "El presente es un manual para que se familiarice con la presente aplicación móvil.  Si bien la aplicación se concibió con un diseño simple e intuitivo, se recomienda leer primero el presente manual antes de usarlo en su entorno de trabajo.\n")

    }
}