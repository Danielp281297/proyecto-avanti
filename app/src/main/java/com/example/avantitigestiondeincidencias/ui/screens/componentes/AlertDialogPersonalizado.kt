package com.example.avantitigestiondeincidencias.ui.screens.componentes

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun AlertDialogPersonalizado(titulo: String, contenido: String, onDismissRequest: () -> Unit, aceptarAccion: () -> Unit, cancelarAccion: () -> Unit)
{
    AlertDialog(
        shape = RectangleShape,
        containerColor = Color.White,
        onDismissRequest = onDismissRequest,
        confirmButton = {

            androidx.compose.material3.Text("ACEPTAR", color = Color.Black, modifier = Modifier.clickable {

                aceptarAccion()

            })

        },
        dismissButton = {

            androidx.compose.material3.Text("CANCELAR", color = Color.Black, modifier = Modifier.clickable {

                cancelarAccion()

            })

        },
        title = {
            androidx.compose.material3.Text(titulo)
        },
        text = {
            androidx.compose.material3.Text(contenido)
        }
    )
}