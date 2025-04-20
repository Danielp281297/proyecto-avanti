package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.system.exitProcess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuario(navController: NavController,
                         empleado: Empleado,
                         contenidoPantalla: @Composable () -> Unit,
                         configurarPerfilAction: @Composable () -> Unit,
                         borrarCuentaAction: () -> Unit)
{



    var json = ""

    var configurarPerfilState = remember {
        mutableStateOf(false)
    }

    var cambiarContrasenaState = remember{
        mutableStateOf(false)
    }

    var borrarCuentaState = remember {
       mutableStateOf(false)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.White
                ),
                title = {
                    Text("Perfil", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(modifier = Modifier, onClick = {
                        navController.popBackStack()
                    }
                    ) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Volver")
                    }
                }
            )
        },
        //Color de fondo
        containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
    )
    {

        Column(modifier = Modifier.padding(25.dp).fillMaxSize().verticalScroll(scrollState, true), verticalArrangement = Arrangement.SpaceBetween)
        {

            Column(modifier = Modifier.wrapContentHeight()) {
                // Lambda que permite crear los datos del empleado
                contenidoPantalla()

            }

            Column() {
                Button(
                    modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                        configurarPerfilState.value = true

                    }
                )
                {

                    Text(text = "CONFIGURAR PERFIL", color = Color.White)

                }
                Spacer(modifier = Modifier.padding(5.dp))
                Button(
                    modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                        cambiarContrasenaState.value = true

                    }
                )
                {

                    Text(text = "CAMBIAR CONTRASEÑA", color = Color.White)

                }
                if (empleado.usuario.id > 1) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(
                        modifier = modeloButton,

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        ),
                        shape = RectangleShape,
                        onClick = {

                            borrarCuentaState.value = true

                        }
                    )
                    {

                        Text(text = "BORRAR CUENTA", color = Color.White)

                    }
                }
                Spacer(modifier = Modifier.padding(30.dp))
            }
        }
    }

    if (configurarPerfilState.value)
    {

        json = Json { ignoreUnknownKeys = true }.encodeToString(empleado)
        navController.navigate("CambiarDatosUsuario" + "/${json}")
        configurarPerfilAction()
        configurarPerfilState.value = false
    }

    if (cambiarContrasenaState.value)
    {

        json = Json { ignoreUnknownKeys = true }.encodeToString(empleado.usuario)

        navController.navigate("cambiarContrasena"+ "/${json}")

        cambiarContrasenaState.value = false
    }

    if (borrarCuentaState.value)
    {

        val scope = rememberCoroutineScope()
        AlertDialogPersonalizado(
            titulo = "Cerrar Sesión",
            contenido = "¿Deseas borrar el usuario? Al finalizar, se cerrará la aplicación ",
            onDismissRequest = {
                borrarCuentaState.value = false
            },
            aceptarAccion = {
                scope.launch {

                    borrarCuentaAction()
                    exitProcess(0)
                    borrarCuentaState.value = false

                }
            },
            cancelarAccion = {
                borrarCuentaState.value = false
            }
        )

    }

}

@Preview(showBackground = true)
@Composable
fun PerfilUsuarioPreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {

        PerfilUsuario(navController, Empleado(), {}, {}, {})

    }
}