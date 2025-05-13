package com.example.avantitigestiondeincidencias.ui.screens.tecnico

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonCargaPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.SHA512
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.rojo
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.launch
import kotlin.text.isWhitespace


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CambiarContrasena(
    navController: NavController,
    context: Context,
    usuario: Usuario,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919))
{

    val scope = rememberCoroutineScope()

    val ingresarbuttonState = remember{
        mutableStateOf(false)
    }

    var contrasenaState = remember {
        mutableStateOf("")
    }

    var confirmarContrasenaState = remember {
        mutableStateOf("")
    }

    var contrasenaVisibleState = remember{
        mutableStateOf(false)
    }

    val focusRequester = remember{
        FocusRequester()
    }

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
                        value = usuario.nombre,
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
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = contrasenaState.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { !it.isWhitespace() } && newText.length <= 20)
                                contrasenaState.value = newText
                        },
                        label = { Text("Contraseña", fontSize = 13.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedLabelColor = Color.Black,
                            focusedBorderColor = Color.Black
                        ),
                        supportingText = {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Spacer(modifier = Modifier.padding(0.dp))

                                Text(
                                    text = "${contrasenaState.value.length}/20",
                                    color = if (contrasenaState.value.length > 6) Color.LightGray else rojo,
                                    modifier = Modifier
                                )
                            }
                        },
                        trailingIcon = {
                            val image = if (contrasenaVisibleState.value == true) {
                                R.drawable.visible_icono
                            } else
                                R.drawable.no_visible_icono

                            IconButton(onClick = {

                                if (contrasenaVisibleState.value) {
                                    contrasenaVisibleState.value = false
                                } else
                                    contrasenaVisibleState.value = true

                            }) {
                                Icon(
                                    painter = painterResource(image),
                                    contentDescription = "",
                                    modifier = Modifier.size(25.dp)
                                )
                            }


                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (contrasenaVisibleState.value) VisualTransformation.None else PasswordVisualTransformation()
                    )

                }


                Column(modifier = Modifier)
                {
                    Text("Confirmar contraseña", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = confirmarContrasenaState.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { !it.isWhitespace() } && newText.length <= 20)
                                confirmarContrasenaState.value = newText
                        },
                        label = { Text("Confirmar contraseña", fontSize = 13.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedLabelColor = Color.Black,
                            focusedBorderColor = Color.Black
                        ),
                        supportingText = {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Spacer(modifier = Modifier.padding(0.dp))

                                Text(
                                    text = "${confirmarContrasenaState.value.length}/20",
                                    color = Color.LightGray,
                                    modifier = Modifier
                                )
                            }
                        },
                        trailingIcon = {
                            val image = if (contrasenaVisibleState.value == true) {
                                R.drawable.visible_icono
                            } else
                                R.drawable.no_visible_icono

                            IconButton(onClick = {

                                if (contrasenaVisibleState.value) {
                                    contrasenaVisibleState.value = false
                                } else
                                    contrasenaVisibleState.value = true

                            }) {
                                Icon(
                                    painter = painterResource(image),
                                    contentDescription = "",
                                    modifier = Modifier.size(25.dp)
                                )
                            }


                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (contrasenaVisibleState.value) VisualTransformation.None else PasswordVisualTransformation()
                    )
                    }



            }

            Column() {

                BotonCargaPersonalizado(
                    onClick = {
                        //Se obtiene los datos del empleado, en base al usuario y contrasena
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
    }

}

@Preview(showBackground = true)
@Composable
fun CambiarContrasenaPreview() {

    val navController = rememberNavController()
    val context = LocalContext.current

    AVANTITIGestionDeIncidenciasTheme {

        //CambiarContrasena(navController, context,  Usuario())

    }
}
