package com.example.avantitigestiondeincidencias.ui.screens.manuales

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ImagenManual(imagenResource: Int)
{
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .height(400.dp)
                .align(Alignment.CenterHorizontally)
                .border(1.dp, if(!isSystemInDarkTheme()) Color.Black else Color.Transparent, RectangleShape),
            painter = painterResource(imagenResource),
            contentDescription = "Pantalla Login"
        )
    }
}