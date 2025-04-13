package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.Supabase.TecnicoRequest
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController)
{
    
    var nombreUsuarioState = remember {
        mutableStateOf("ralonzo@gmail.com")
    }

    var contrasenaUsuarioState = remember {
        mutableStateOf("ralonzo1990")
    }
    
    var contrasenaVisibleState = remember {
        mutableStateOf(false)
    }

    var ingresarbuttonState = remember {
        mutableStateOf(false)
    }

    val focusRequester = remember{
        FocusRequester()
    }

    
    Scaffold(
        containerColor = Color.White,

    ) {


        Column(modifier = Modifier.fillMaxSize().padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Column(modifier = Modifier.fillMaxWidth().height(300.dp),
                verticalArrangement = Arrangement.SpaceEvenly) {
                Image(painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo de Avanti",
                    modifier = Modifier.size(144.dp).align(Alignment.CenterHorizontally))
                Text("AVANTI BY FRIGILUX, C.A. \n J-501548218 \n Gestión de incidencias",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.padding(15.dp))
            Column(modifier = Modifier.fillMaxWidth().height(320.dp),
                verticalArrangement = Arrangement.Center) {
                Text("Ingrese los datos correspondientes",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
                Text("Nombre de usuario", fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = nombreUsuarioState.value,
                    onValueChange = { newText ->
                        // Si el texto es menor a 50 caracteres, se almacena en newText
                        if (newText.length <= 20)
                            nombreUsuarioState.value = newText
                    },
                    label = { Text("Nombre de usuario", fontSize = 13.sp) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = Color.Black,
                        focusedBorderColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(
                        // Habilita el boton Next en el teclado, dirigiendo al siguiente campo de texto...
                        imeAction = ImeAction.Next)
                )
                Spacer(modifier = Modifier.padding(15.dp))
                Text("Contraseña", fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                    value = contrasenaUsuarioState.value,
                    onValueChange = { newText ->
                        // Si el texto es menor a 50 caracteres, se almacena en newText
                        if (newText.length <= 20)
                            contrasenaUsuarioState.value = newText
                    },
                    label = { Text("Contraseña", fontSize = 13.sp) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = Color.Black,
                        focusedBorderColor = Color.Black
                    ),
                    trailingIcon = {
                        val image = if (contrasenaVisibleState.value == true)
                        {
                            R.drawable.visible_icono
                        }else
                            R.drawable.no_visible_icono

                        IconButton(onClick = {

                            if (contrasenaVisibleState.value)
                            {
                                contrasenaVisibleState.value = false
                            }else
                                contrasenaVisibleState.value = true

                        }) {
                            Icon(painter = painterResource(image), contentDescription = "", modifier = Modifier.size(25.dp))
                        }


                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if(contrasenaVisibleState.value) VisualTransformation.None else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.padding(15.dp))
                Button(modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                        //Se obtiene los datos del empleado, en base al usuario y contrasena
                        ingresarbuttonState.value = true

                    }
                )
                {
                    Text(text = "INGRESAR", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "¿No está registrado? Comuníquese con el administrador", fontSize = 12.sp, textAlign = TextAlign.Center)
        }

    }

    val datosIncorrectosAlertDialogState = remember {
        mutableStateOf(false)
    }
    val enviarPantallaState = remember{
        mutableStateOf(false)
    }
    var data = remember{
        mutableStateOf<Empleado?>(null)
    }
    if (ingresarbuttonState.value)
    {
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {


                data.value = EmpleadoRequest().obtenerDatosUsuario(nombreUsuarioState.value, contrasenaUsuarioState.value)
                Log.d("DATA", data.value.toString())
                if(data.value != null)
                {


                    enviarPantallaState.value = true

                }
                else datosIncorrectosAlertDialogState.value = true

            }
            ingresarbuttonState.value = false
        }

    }

    if(datosIncorrectosAlertDialogState.value)
    {
        AlertDialog(
            shape = RectangleShape,
            onDismissRequest = {
                datosIncorrectosAlertDialogState.value = false
            },
            confirmButton = {
                Button(
                    modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {
                        datosIncorrectosAlertDialogState.value = false
                    })
                {
                    Text(text = "ACEPTAR")
                }
            },
            title = {
                Text("AVISO")
            },
            text = {
                Text("Datos Incorrectos. Intente de nuevo")
            }
        )
    }

    if (enviarPantallaState.value)
    {

        enviarPantalla(data.value!!, navController)
        enviarPantallaState.value = false
    }

}

@Composable
fun enviarPantalla(empleado: Empleado, navController: NavController)
{

    var json = Json.encodeToString(empleado)

            when(empleado.usuario.idTipoUsuario)
            {
                1 -> navController.navigate("principalTécnico" + "/${json}")
                2 -> navController.navigate("principalCliente" + "/${json}")
                3 -> navController.navigate("principalAdministrador" + "/${json}")
            }

}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {
        Login(navController)
    }
}
