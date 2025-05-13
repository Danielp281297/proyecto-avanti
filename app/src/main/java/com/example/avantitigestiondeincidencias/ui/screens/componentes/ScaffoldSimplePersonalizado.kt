package com.example.avantitigestiondeincidencias.ui.screens.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldSimplePersonalizado(tituloPantalla: String, containerColor: Color, contenido: @Composable () -> Unit)
{

    Scaffold(
        containerColor = containerColor,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor),
                title = {
                    Text(tituloPantalla, modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFamily)
                }
            )
        }
    )
    {

        contenido()

    }

}