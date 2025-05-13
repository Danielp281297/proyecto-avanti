package com.example.avantitigestiondeincidencias.ui.screens.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun AlertDialogPersonalizado(
    titulo: String,
    contenido: String,
    onDismissRequest: () -> Unit,
    aceptarAccion: () -> Unit,
    cancelarAccion: @Composable () -> Unit,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919),
    OptionsTextColors: Color = if (!isSystemInDarkTheme()) Color.Black else Color.White)
{
    AlertDialog(
        shape = RectangleShape,
        containerColor = containerColor,
        onDismissRequest = onDismissRequest,
        confirmButton = {

            androidx.compose.material3.Text("ACEPTAR", color = OptionsTextColors, modifier = Modifier.clickable {

                aceptarAccion()

            })

        },
        dismissButton = {
                cancelarAccion()
        },
        title = {
            androidx.compose.material3.Text(titulo)
        },
        text = {
            androidx.compose.material3.Text(contenido)
        }
    )
}