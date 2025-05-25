package com.example.avantitigestiondeincidencias.ui.screens.perfil

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.ViewModel.CambiarContrasenaViewModel
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonCargaPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.OutlinedTextFieldPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.SHA512
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.rojo
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.text.isWhitespace


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CambiarContrasena(
    navController: NavController,
    context: Context,
    usuario: Usuario,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919),
    viewModel: CambiarContrasenaViewModel = viewModel()
    )
{

    val mensajeError = viewModel.mensajeError.collectAsState()

    val nombreUsuario = viewModel.nombreUsuario.collectAsState()
    val contrasenaUsuario = viewModel.contrasenaUsuario.collectAsState()
    val confirmarContrasena = viewModel.confirmarContrasenaUsuario.collectAsState()

    val scope = rememberCoroutineScope()

    val ingresarbuttonState = remember{
        mutableStateOf(false)
    }

    viewModel.setNombreUsuario(usuario.nombre)

    Network.networkCallback(navController, context)

    ScaffoldSimplePersonalizado(
        tituloPantalla = "Cambiar contraseña",
        containerColor = containerColor
    )
    {

        Column(modifier = Modifier
            .padding(25.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween)
        {

            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.padding(50.dp))
                Text("Ingrese los datos correspondientes",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
                Column(modifier = Modifier)
                {

                    Text("Nombre de usuario", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = nombreUsuario.value,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Nombre de usuario", fontSize = 13.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedLabelColor = Color.Black,
                            focusedBorderColor = Color.Black
                        )
                    )

                }

                Spacer(modifier = Modifier.padding(10.dp))
                Column(modifier = Modifier)
                {
                    Text("Contraseña", fontWeight = FontWeight.Bold)
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier.fillMaxWidth(),
                        value = contrasenaUsuario.value,
                        password = true,
                        onValueChange = {newText ->
                            if (newText.all { !it.isWhitespace() } && newText.length <= 20)
                                viewModel.setContrasenaUsuario(newText)
                        },
                        label = { Text("Contraseña", fontSize = 13.sp) },
                        empezarMayusculas = false,
                        imeActionNext = false,
                        supportingText = true,
                        maximoCaracteres = 20,
                        minimoCaracteres = 6
                    )

                }

                Column(modifier = Modifier)
                {
                    Text("Confirmar contraseña", fontWeight = FontWeight.Bold)
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier.fillMaxWidth(),
                        value = confirmarContrasena.value,
                        password = true,
                        onValueChange = {newText ->
                            if (newText.all { !it.isWhitespace() } && newText.length <= 20)
                                viewModel.setConfirmarContrasenaUsuario(newText)
                        },
                        label = { Text("Contraseña", fontSize = 13.sp) },
                        empezarMayusculas = false,
                        imeActionNext = false,
                        supportingText = true,
                        maximoCaracteres = 20,
                        minimoCaracteres = 6
                    )
                    }



            }

            Column(modifier = Modifier.fillMaxWidth()){

                Text("Especificaciones para la contraseña:", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text("Ser entre 6 y 20 caracteres\n" +
                        "\n" +
                        "Al menos 1 dígito de 0 a 9\n" +
                        "\n" +
                        "Al menos un símbolo especial ${'$'}%&/()!" +
                        "\n")

            }
            Spacer(modifier = espacioSpacer)

            Column() {

                BotonCargaPersonalizado(
                    onClick = {
                        //Se obtiene los datos del empleado, en base al usuario y contrasena
                        viewModel.setCambiarContrasenaMensajeError()
                        ingresarbuttonState.value = true
                    },
                    isLoading = ingresarbuttonState.value
                ) {
                    Text(text = "CAMBIAR CONTRASEÑA", color = Color.White)
                }
                Spacer(modifier = Modifier.padding(30.dp))
            }
        }
    }

    if (ingresarbuttonState.value)
    {

        if(mensajeError.value.isBlank())
        {

            AlertDialogPersonalizado(
                titulo = "CAMBIO DE CONTRASEÑA",
                contenido = "¿Desea cambiar su contraseña ahora?",
                onDismissRequest = { ingresarbuttonState.value = false },
                aceptarAccion = {

                    scope.launch {

                        viewModel.cambiarContrasenaUsuario { resultado ->
                            Toast.makeText(context, resultado, Toast.LENGTH_SHORT).show()
                        }
                    }

                    // Se regresa a la pantalla anterior
                    navController.popBackStack()
                    ingresarbuttonState.value = false
                },
                cancelarAccion = {

                    androidx.compose.material.Text("CANCELAR", color = Color.Black, modifier = Modifier.clickable {

                        ingresarbuttonState.value = false
                    })
                }

            )

        }
        else
        {
            Toast.makeText(context, mensajeError.value, Toast.LENGTH_SHORT).show()
            ingresarbuttonState.value = false
        }
        /*
        if(contrasenaState.value != confirmarContrasenaState.value) //Si las contrasenas no coinciden, se muestra el aviso
        {
            // Mensaje de aviso
            Toast.makeText(context, "Aviso: Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show()
            ingresarbuttonState.value = false
        }
        else if(contrasenaState.value.length < 6) //Si las contrasenas no coinciden, se muestra el aviso
        {
            // Mensaje de aviso
            Toast.makeText(context, "Contraseña muy corta. Intente de nuevo.", Toast.LENGTH_SHORT).show()
            ingresarbuttonState.value = false
        }
        else {

            var mensaje = ""
            AlertDialogPersonalizado(
                titulo = "CAMBIO DE CONTRASEÑA",
                contenido = "¿Desea cambiar su contraseña ahora?",
                onDismissRequest = { ingresarbuttonState.value = false },
                aceptarAccion = {

                    // Hashear la contrasena
                    usuario.password = SHA512(contrasenaState.value)
                    scope.launch {

                        UsuarioRequest().cambiarContrasenaUsuario(usuario)
                        {

                            if (it != null)
                                mensaje = "La contraseña fue cambiada con éxito."
                            else
                                mensaje = "La contraseña no fue cambiada. Intentelo de nuevo."

                        }

                    }

                    // Se regresa a la pantalla anterior
                    navController.popBackStack()
                    ingresarbuttonState.value = false
                },
                cancelarAccion = {

                    androidx.compose.material.Text("CANCELAR", color = Color.Black, modifier = Modifier.clickable {

                        ingresarbuttonState.value = false
                    })
                }

            )
        }
        */
    }

}

@Preview(showBackground = true)
@Composable
fun CambiarContrasenaPreview() {

    val navController = rememberNavController()
    val context = LocalContext.current

    AVANTITIGestionDeIncidenciasTheme {

        CambiarContrasena(navController, context,  Usuario())

    }
}
