package com.example.avantitigestiondeincidencias.ui.screens.perfil

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonPersonalizado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuario(navController: NavController,
                  context: Context,
                  empleado: Empleado,
                  containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919),
                  contenidoPantalla: @Composable () -> Unit,
                  configurarPerfilAction: @Composable () -> Unit)
{

    var json = ""

    var configurarPerfilState = remember {
        mutableStateOf(false)
    }

    var cambiarContrasenaState = remember{
        mutableStateOf(false)
    }

    var deshabilitarUsuarioState = remember {
       mutableStateOf(false)
    }

    val scrollState = rememberScrollState()

    Network.networkCallback(navController, context)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor
                ),
                title = {
                    Text("Perfil",
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFamily)
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
        containerColor = containerColor
    )
    {

        Column(modifier = Modifier.padding(25.dp).fillMaxSize().verticalScroll(scrollState, true), verticalArrangement = Arrangement.SpaceBetween)
        {

            Column(modifier = Modifier.wrapContentHeight()) {
                // Lambda que permite crear los datos del empleado
                contenidoPantalla()

            }

            Column() {
                BotonPersonalizado(
                    onClick = {

                        configurarPerfilState.value = true

                    }
                )
                {

                    Text(text = "EDITAR PERFIL", color = Color.White)

                }
                if (empleado.usuario.habilitado) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    BotonPersonalizado(
                        onClick = {

                            cambiarContrasenaState.value = true

                        }
                    )
                    {

                        Text(text = "CAMBIAR CONTRASEÑA", color = Color.White)

                    }
                    if (empleado.usuario.id > 1) {
                        Spacer(modifier = Modifier.padding(5.dp))
                        BotonPersonalizado (
                            onClick = {

                                deshabilitarUsuarioState.value = true

                            }
                        )
                        {

                            Text(text = "DESHABILITAR USUARIO", color = Color.White)

                        }

                    }
                }
                Spacer(modifier = Modifier.padding(30.dp))
            }
        }
    }

    if (configurarPerfilState.value)
    {

        navController.popBackStack()
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

    if (deshabilitarUsuarioState.value)
    {

        val scope = rememberCoroutineScope()
        AlertDialogPersonalizado(
            titulo = "Deshabilitar Usuario",
            contenido = "¿Deseas deshabilitar el usuario?",
            onDismissRequest = {
                deshabilitarUsuarioState.value = false
            },
            aceptarAccion = {

                    deshabilitarUsuario(empleado.idUsuario)
                    deshabilitarUsuarioState.value = false
                    navController.popBackStack()

            },
            cancelarAccion = {

                androidx.compose.material.Text("CANCELAR", color = Color.Black, modifier = Modifier.clickable {

                    deshabilitarUsuarioState.value = false
                })

            }
        )

    }

}


fun deshabilitarUsuario(idUsuario: Int)
{
        CoroutineScope(Dispatchers.IO).launch {

            UsuarioRequest().deshabilitarUsuarioById(idUsuario)

        }
}

@Preview(showBackground = true)
@Composable
fun PerfilUsuarioPreview() {

    val navController = rememberNavController()
    val context = LocalContext.current
    AVANTITIGestionDeIncidenciasTheme {

        //PerfilUsuario(navController, context, Empleado(),{}, {})

    }
}