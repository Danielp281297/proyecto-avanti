package com.example.avantitigestiondeincidencias.ui.screens.manuales

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.R
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment

@Composable
fun DescripcionPantallaLogin(numPantalla: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla Login")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable{ numPantalla(1) })
        TextoManual("La primera pantalla que aparece es la pantalla del Ingreso de usuario.\n")
        ImagenManual(R.drawable.login)
        TextoManual("Esta pantalla presenta los siguientes elementos:\n" +
                "\t• Una entrada de texto para el nombre de usuario\n" +
                "\t• Una entrada de texto para la contraseña de usuario\n" +
                "En esta pantalla, el usuario debe ingresar su usuario (creado previamente) para usar la aplicación. Si no se tiene una cuenta, el dueño del dispositivo debe comunicarse con quien será el administrador de la aplicación.\n" +
                "Para poder mejorar la experiencia del usuario, la aplicación no requiere distinguir el tipo de usuario. Tanto el administrador, como el técnico analista, como el cliente interno solo necesitan ingresar su nombre y contraseña de usuario y se mostrarán las funcionalidades respectivas de la aplicación.\n")


    }
}