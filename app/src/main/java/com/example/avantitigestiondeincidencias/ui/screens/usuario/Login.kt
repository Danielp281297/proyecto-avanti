package com.example.avantitigestiondeincidencias.ui.screens.usuario

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.AnimatedImageDrawable
import android.util.Log
import android.widget.ImageView
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.DataStore.DataStore
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonCargaPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.OutlinedTextFieldPersonalizado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    navController: NavController,
    context: Context,
    navigateToDos: (Int, String) -> Unit,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919))
{
    
    var nombreUsuarioState = remember {
        mutableStateOf("")
    }

    var contrasenaUsuarioState = remember {
        mutableStateOf("")
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

    val datosIncorrectosAlertDialogState = remember {
        mutableStateOf(false)
    }

    val enviarPantallaState = remember{
        mutableStateOf(false)
    }

    var usuario = remember{
        mutableStateOf<Usuario?>(null)
    }

    var jsonState = remember {
        mutableStateOf("")
    }

    var mostrarLogin = remember {
        mutableStateOf(false)
    }

    var scope = rememberCoroutineScope()



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
                        if (newText.all { !it.isWhitespace() && it.isLetter() || it.isDigit() } && newText.length <= 20)
                            nombreUsuarioState.value = newText
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
                        if (newText.all { !it.isWhitespace() } && newText.length <= 20)
                            contrasenaUsuarioState.value = newText
                    },
                    label = { Text("Contraseña", fontSize = 13.sp) },
                    empezarMayusculas = false,
                    imeActionNext = false,
                    supportingText = false
                )
                Spacer(modifier = Modifier.padding(15.dp))
                BotonCargaPersonalizado({

                    //Se obtiene los datos del empleado, en base al usuario y contrasena
                    ingresarbuttonState.value = true

                }, isLoading = ingresarbuttonState.value)
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
                withContext(Dispatchers.IO) {

                    usuario.value =
                        EmpleadoRequest().obtenerDatosUsuario(nombreUsuarioState.value, contrasenaUsuarioState.value)

                    if (usuario.value != null) {

                        Log.d("USUARIO", "Encontrado")
                        enviarPantalla(usuario.value!!) { json ->
                            jsonState.value = json
                            enviarPantallaState.value = true

                        }

                    } else {
                        Log.d("USUARIO", "No encontrado")
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
        //pasarPantalla(usuario.value!!.idTipoUsuario, jsonState.value, navController)
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

@Composable
fun iconoCarga(modifier: Modifier)
{
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val imageView = ImageView(ctx)
            val drawable = ctx.resources.getDrawable(R.drawable.cargando, null)
            if (drawable is AnimatedImageDrawable) {
                drawable.start()
            }
            imageView.setImageDrawable(drawable)
            imageView
        }
    )
}

fun pasarPantalla(
    tipoUsuario: Int,
    json: String,
    navController: NavController)
{

    when(tipoUsuario)
    {
        // Ingresar a la pantalla de Tecnico
        1 -> navController.navigate("principalTécnico" + "/${json}")
        2 -> navController.navigate("principalCliente" + "/${json}")
        3 -> navController.navigate("principalAdministrador" + "/${json}")

    }

}

/*

when(tipoUsuario)
    {
        // Ingresar a la pantalla de Tecnico
        1 -> navController.navigate("principalTécnico" + "/${json}")

        2 -> navController.navigate("principalCliente" + "/${json}")

        3 -> navController.navigate("principalAdministrador" + "/${json}")

    }

 */

suspend fun enviarPantalla(usuario: Usuario, jsonFormato: (json: String) -> Unit)
{

    var json = " "

    when(usuario.idTipoUsuario)
    {

        1 -> {
            var tecnico = EmpleadoRequest().seleccionarTecnicobyUsuarioId(usuario.id)
            // Se crea el objeto json
            json = Json { ignoreUnknownKeys = true }.encodeToString(tecnico)
            }

        2 -> {
            var clienteInterno = EmpleadoRequest().seleccionarClienteInternobyUsuarioId(usuario.id)

            json = Json { ignoreUnknownKeys = true }.encodeToString(clienteInterno)
        }
        
        3 ->{
            var administrador = EmpleadoRequest().seleccionarTecnicobyUsuarioId(usuario.id)

            json = Json { ignoreUnknownKeys = true }.encodeToString(administrador)
        }
    }

    jsonFormato(json)
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
