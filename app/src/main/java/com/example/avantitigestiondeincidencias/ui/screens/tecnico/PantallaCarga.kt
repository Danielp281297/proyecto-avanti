package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun PantallaCarga()
{

    Box(modifier = Modifier.fillMaxSize().background(Color.White))
    {

        Column(modifier = Modifier.align(Alignment.Center))
        {

            Text("CARGANDO", fontWeight = FontWeight.Bold)

        }

    }

}