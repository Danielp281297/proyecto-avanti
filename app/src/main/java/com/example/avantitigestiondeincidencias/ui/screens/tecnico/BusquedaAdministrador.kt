package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaAdministrador()
{

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.Transparent
                ),
                title = {
                    Text("Busqueda", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            )
        },
        //Color de fondo
        containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
    )
    {



    }

}


@Preview(showBackground = true)
@Composable
fun BusquedaAdministradorPreview() {

    AVANTITIGestionDeIncidenciasTheme {

        BusquedaAdministrador()

    }
}
