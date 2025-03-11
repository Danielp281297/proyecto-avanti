package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

@Composable
fun PantallaNotificacion(){

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
    {

        Button(onClick = {

            Notification().mostrarNotificacion(context, "PUTO", "EL QUE LO LEA")

        })
        {
            Text(text = "NOTIFICACION")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PantallaNotificacionPreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {

        PantallaNotificacion()

    }
}
