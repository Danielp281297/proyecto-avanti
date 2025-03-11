package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

@Composable
fun PantallaPruebas(navController: NavController)
{
    Box(modifier = Modifier.fillMaxSize())
    {

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        )
        {

            Button(onClick = {
                navController.navigate("principalAdministrador")
            }) {

                Text(text = "ADMINISTRADOR")

            }

            Button(onClick = {
                navController.navigate("principalCliente")
            }) {

                Text(text = "CLIENTE (3)")

            }

            Button(onClick = {

                navController.navigate("principalTécnico")

            }) {

                Text(text = "TECNICO (2)")

            }


        }

    }
}


@Preview(showBackground = true)
@Composable
fun PantallaPruebasPreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {

        PantallaPruebas(navController)

    }
}
