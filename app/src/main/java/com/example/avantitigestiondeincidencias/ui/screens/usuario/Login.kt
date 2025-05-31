package com.example.avantitigestiondeincidencias.ui.screens.usuario

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.DataStore.DataStore
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ViewModel.LoginViewModel
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonCargaPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.OutlinedTextFieldPersonalizado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    navController: NavController,
    context: Context,
    navigateToDos: (Int, String) -> Unit,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919),
    viewModel: LoginViewModel = viewModel()
)
{

    val nombreUsuarioState = viewModel.nombreUsuario.collectAsState()
    val contrasenaUsuarioState = viewModel.contrasena.collectAsState()

    var usuario = remember{

        mutableStateOf<Usuario?>(null)
    }

    var ingresarbuttonState = remember {
        mutableStateOf(false)
    }

    val focusRequester = remember{
        FocusRequester()
    }

    val datosIncorrectosAlertDialogState = remember {
        mutableStateOf(false)
    }

    val enviarPantallaState = remember{
        mutableStateOf(false)
    }



    var jsonState = remember {
        mutableStateOf<String>("")
    }

    var mostrarLogin = remember {
        mutableStateOf(false)
    }

    var scope = rememberCoroutineScope()


    // Se obtienen los datos de un usuario
    LaunchedEffect(Unit) {
        scope.launch {

            if (DataStore(context).getSesionAbierta()) {
                navigateToDos(DataStore(context).getTipoUsuario(), DataStore(context).getJson())
            }
            else mostrarLogin.value = true
        }
    }

    if (mostrarLogin.value)

    Scaffold(
        containerColor = containerColor,

    ) {

        Column(modifier = Modifier.fillMaxSize().padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Column(modifier = Modifier.fillMaxWidth().height(300.dp),
                verticalArrangement = Arrangement.SpaceEvenly) {
                Icon(imageVector = ImageVector.Companion.vectorResource(R.drawable.logo),
                    contentDescription = "Logo de Avanti",
                    modifier = Modifier.size(144.dp).align(Alignment.CenterHorizontally))
                Text("AVANTI BY FRIGILUX, C.A. \n J-501548218",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
                Text("Gestión de incidencias",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp)
            }
            Column(modifier = Modifier.fillMaxWidth().height(320.dp),
                verticalArrangement = Arrangement.Center) {
                Text("Ingrese los datos correspondientes",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp)
                Text("Nombre de usuario", fontWeight = FontWeight.Bold)
                OutlinedTextFieldPersonalizado(
                    modifier = Modifier.fillMaxWidth(),
                    value = nombreUsuarioState.value,
                    onValueChange = {newText ->

                            viewModel.setNombreUsuario(newText)
                    },
                    label = { Text("Nombre de usuario", fontSize = 13.sp) },
                    supportingText = false
                )
                Text("Contraseña", fontWeight = FontWeight.Bold)
                OutlinedTextFieldPersonalizado(
                    modifier = Modifier.fillMaxWidth(),
                    value = contrasenaUsuarioState.value,
                    password = true,
                    onValueChange = {newText ->

                            viewModel.setContrasena(newText)
                    },
                    label = { Text("Contraseña", fontSize = 13.sp) },
                    empezarMayusculas = false,
                    imeActionNext = false,
                    supportingText = false
                )
                Spacer(modifier = Modifier.padding(5.dp))
                BotonCargaPersonalizado({

                    //Se obtiene los datos del empleado, en base al usuario y contrasena
                    ingresarbuttonState.value = true

                }, enabled = !ingresarbuttonState.value,
                    isLoading = ingresarbuttonState.value)
                    {

                        Text(text = "INGRESAR", color = Color.White, fontFamily = montserratFamily)

                }

            }
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "¿No está registrado? Comuníquese con el administrador", fontSize = 12.sp, textAlign = TextAlign.Center)
        }

    }



    if (ingresarbuttonState.value)
    {

        if(!Network.isConnect(context))
        {
            Toast.makeText(context, "Sin conectividad. Compruebe la conexión a internet y vuelva a intentarlo.", Toast.LENGTH_SHORT).show()
            ingresarbuttonState.value = false
        }
        else {

            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO){

                    usuario.value = viewModel.validarDatosUsuario()

                    if (usuario.value != null)
                    {
                        Log.d("RESULTADO", "USUARIO ENCONTRADO")
                        viewModel.obtenerDatosEmpleado(usuario.value!!){ json ->

                            Log.d("RESULTADOS", json)
                            jsonState.value = json
                            enviarPantallaState.value = true

                        }
                    }
                    else
                    {
                        Log.d("RESULTADO", "USUARIO NO ENCONTRADO")
                        datosIncorrectosAlertDialogState.value = true
                    }


                }

                ingresarbuttonState.value = false
            }
        }


    }

    if(datosIncorrectosAlertDialogState.value)
    {
        AlertDialogPersonalizado(
            titulo = "AVISO",
            contenido = "Datos Incorrectos. Intente de nuevo",
            onDismissRequest = { datosIncorrectosAlertDialogState.value = false },
            aceptarAccion = {
                datosIncorrectosAlertDialogState.value = false
            },
            cancelarAccion = {}
        )

    }

    if (enviarPantallaState.value)
    {
        enviarPantallaState.value = false

        navController.popBackStack()
        // Se guardan los datos de las variables necesarias
        LaunchedEffect(Unit) {
            scope.launch {
                DataStore(context).setData(
                    tipo = usuario.value!!.idTipoUsuario,
                    json = jsonState.value,
                    sesionAbierta = true
                )

            }
        }
        navigateToDos(usuario.value!!.idTipoUsuario, jsonState.value)


    }

}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {

    val navController = rememberNavController()
    val context = LocalContext.current
    AVANTITIGestionDeIncidenciasTheme {
        //Login(navController, context)
    }
}
