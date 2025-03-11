package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

data class AdministradorOpciones(val label: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuentaAdministrador()
{



    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.Transparent
                ),
                title = {
                    Text("Usuarios", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            )
        },
        //Color de fondo
        containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
    )
    {

        Spacer(modifier = Modifier.padding(35.dp))

        Box(modifier = Modifier.fillMaxSize().padding(15.dp))
        {



            FloatingActionButton(onClick = {

            },
                shape = RectangleShape,
                containerColor = Color.Black,
                modifier = Modifier.align(Alignment.BottomEnd).padding(0.dp, 50.dp)) {

                Text(" + ", color = Color.White)

            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun CuentaAdministraPreview() {

    AVANTITIGestionDeIncidenciasTheme {

        CuentaAdministrador()

    }
}