package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearUsuario(navController: NavController)
{

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                title = {
                    Text("Crear usuario", modifier = Modifier.fillMaxWidth().padding(0.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            )
        }
    ) {

        Box(modifier = Modifier.fillMaxSize().padding(25.dp)
        )
        {

            Column(modifier = Modifier.fillMaxSize())
            {

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun CrearUsuarioPreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {
        CrearUsuario(navController)
    }
}
