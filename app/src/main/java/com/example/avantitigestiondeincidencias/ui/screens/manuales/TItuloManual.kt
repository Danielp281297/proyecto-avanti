package com.example.avantitigestiondeincidencias.ui.screens.manuales

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily

@Composable
fun TituloManual(
    titulo:String,
    modifier: Modifier = Modifier,
    ){

    Column {
        Text(titulo, modifier = modifier, fontSize = 25.sp, fontFamily = montserratFamily)
        HorizontalDivider()
    }

}

@Preview(showBackground = true)
@Composable
fun TituloManualPreview(){

    AVANTITIGestionDeIncidenciasTheme {

        TituloManual("Prueba de escritura")

    }

}
