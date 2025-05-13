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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp

@Composable
fun Presentacion()
{

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        Text("Presentación")
        Text("GALERÍAS AVANTI, constituida como AVANTI BY FRIGILUX, C.A., es una empresa de retail ubicada en Las Mercedes inaugurada el 18 de noviembre del 2022, \n" +
                "\n" +
                "La aplicación para la gestión de incidencias de la empresa ha sido desarrollada para usted, empleado de la misma; con una interfaz de usuario que asemeja la experiencia de usuario de la marca, y la utilización de base de datos con arquitectura cliente-servidor con api REST que permite el acceso a la información en tiempo real.\n" +
                "\n" +
                "Le agradecemos a la empresa AVANTI permitirnos las instalaciones para la creación de la presente, y a usted por confiar en el usuario de la misma, que ayuden a la gestión diaria de las incidencias, independientemente del departamento donde trabaja.\n" +
                "\n" +
                "Daniel Padilla.\n")

    }

}
