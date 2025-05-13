package com.example.avantitigestiondeincidencias.ui.screens.perfil

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.ui.screens.cliente.InformacionClienteInterno
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

@Composable
fun PerfilClienteInterno(navController: NavController, context: Context, clienteInterno: ClienteInterno)
{

    PerfilUsuario(
        navController = navController,
        context = context,
        empleado = clienteInterno.empleado,
        contenidoPantalla = {

            InformacionClienteInterno(clienteInterno)
        },
        configurarPerfilAction = {}
    )

}

@Preview(showBackground = true)
@Composable
fun PerfilClienteInternoPreview() {

    val navController = rememberNavController()
    val context = LocalContext.current

    AVANTITIGestionDeIncidenciasTheme {

        PerfilClienteInterno(navController, context, ClienteInterno())

    }
}