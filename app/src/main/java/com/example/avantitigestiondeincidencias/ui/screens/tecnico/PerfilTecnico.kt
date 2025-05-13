package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.ui.screens.perfil.InformacionTecnico
import com.example.avantitigestiondeincidencias.ui.screens.perfil.PerfilUsuario
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilTecnico(
    navController: NavController,
    context: Context,
    tecnico: Tecnico,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919))
{

    PerfilUsuario(
        navController = navController,
        context = context,
        empleado = tecnico.empleado,
        containerColor = containerColor,
        contenidoPantalla = {

            InformacionTecnico(tecnico)
        },
        configurarPerfilAction = {}
    )

}

@Preview(showBackground = true)
@Composable
fun PerfilTecnicoPreview() {

    val navController = rememberNavController()
    val context = LocalContext.current

    AVANTITIGestionDeIncidenciasTheme {

        PerfilTecnico(navController, context, Tecnico())

    }
}