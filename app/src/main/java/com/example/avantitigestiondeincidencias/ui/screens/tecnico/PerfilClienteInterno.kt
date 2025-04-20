package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

@Composable
fun PerfilClienteInterno(navController: NavController, clienteInterno: ClienteInterno)
{

    PerfilUsuario(
        navController = navController,
        empleado = clienteInterno.empleado,
        contenidoPantalla = {

            InformacionClienteInterno(clienteInterno)
        },
        configurarPerfilAction = {},
        borrarCuentaAction = {}
    )

}

@Preview(showBackground = true)
@Composable
fun PerfilClienteInternoPreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {

        PerfilClienteInterno(navController, ClienteInterno())

    }
}