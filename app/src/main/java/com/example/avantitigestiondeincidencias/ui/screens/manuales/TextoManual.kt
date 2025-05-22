package com.example.avantitigestiondeincidencias.ui.screens.manuales

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun TextoManual(
    contenido: String,
    modifier: Modifier = Modifier){

    Text(contenido, modifier = modifier, fontSize = 16.sp)

}