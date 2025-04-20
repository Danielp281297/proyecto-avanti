package com.example.avantitigestiondeincidencias

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.CambiarContrasena
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.CambiarDatosUsuario
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.CrearUsuario
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.HorizontalPagerBottomBarAdministrador
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.HorizontalPagerBottomBarTecnico
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.InformacionClienteInterno
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.InformacionTecnico
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.InicioCliente
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.Login
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.PantallaPruebas
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.PerfilClienteInterno
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.PerfilTecnico
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.TicketDesplegadoAdministrador
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.TicketDesplegadoBusqueda
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.TicketDesplegadoCliente
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.TicketDesplegadoTecnico
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.serialization.json.Json
import kotlin.system.exitProcess

val paddingPantallas = Modifier.fillMaxSize().padding(15.dp)
val espacioSpacer = Modifier.padding(7.5.dp)
val modeloButton = Modifier.fillMaxWidth().height(50.dp)

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AVANTITIGestionDeIncidenciasTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val context = LocalContext.current

                    // Primero, se pide que el usuario conceda permisos para acceder al almacenamiento del dispositivo,
                    // y a la llegada de notificaciones
                    Notification().createChannel(context)
                    permisos(context)
                    destination()


                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun permisos(context: Context)
{

    val permissionState =
        rememberMultiplePermissionsState(listOf(android.Manifest.permission.POST_NOTIFICATIONS, android.Manifest.permission.CAMERA))//rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)

    val estadoPermiso = remember {
        mutableStateOf("Permiso no concedido")
    }
    // Si se concede el permiso, devuelve verdadero; y lo contrario es falso
    if (permissionState.allPermissionsGranted)
    {
        estadoPermiso.value = "Permiso concedido"
    }
    else LaunchedEffect(Unit)
    {
        permissionState.launchMultiplePermissionRequest()
    }

    if (permissionState.shouldShowRationale && !permissionState.allPermissionsGranted)
    {
        exitProcess(0)
    }


}

@Composable
fun destination()
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "pantallaPruebas")
    {

        composable("pantallaPruebas")
        {

            //HorizontalPagerBottomBarTecnico(/*Empleado(), */navController)
            //Login(navController)
            //CrearUsuario(navController)
            //BorrarUsuarioPrueba()
            //InformeTecnico()
            //PantallaNotificacion()
            //DatePickerScreen()
            PantallaPruebas(navController)
            //BusquedaUsuarios(navController)
            //InicioCliente(navController)
            //nuevoTicketFormulario(3) { }
            //HorizontalPagerBottomBarAdministrador(Empleado(), navController = navController)
            //TicketDesplegadoAdministrador(navController, Ticket())
            //TicketDesplegadoTecnico(navController, Ticket())
            //CambiarContrasena(navController, Usuario())

        }

        composable("login")
        {

            Login(navController)

        }

        composable(route = "principalCliente"+ "/{clienteInterno}",
            arguments = listOf(navArgument(name = "clienteInterno")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            val clienteInterno = Json.decodeFromString<ClienteInterno>(navBackStackEntry.arguments!!.getString("clienteInterno").toString())

            InicioCliente(clienteInterno, navController)

        }

        composable("principalAdministrador" + "/{administrador}",
            arguments = listOf(navArgument(name = "administrador")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            val tecnico = Json.decodeFromString<Tecnico>(navBackStackEntry.arguments!!.getString("administrador").toString())

            HorizontalPagerBottomBarAdministrador(tecnico, navController = navController)
        }

        composable("ticketDesplegadoAdministrador/{ticket}",
                    arguments = listOf(navArgument(name = "ticket")
                    {type = NavType.StringType}))
        { navBackStackEntry ->

            val ticket = Json.decodeFromString<Ticket>(navBackStackEntry.arguments!!.getString("ticket").toString())

            TicketDesplegadoAdministrador(navController = navController, ticket)
        }

        composable("principalTécnico" + "/{tecnico}",
            arguments = listOf(navArgument(name = "tecnico")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            var tecnicoState = remember{
                mutableStateOf<Tecnico>(Json.decodeFromString<Tecnico>(navBackStackEntry.arguments!!.getString("tecnico").toString()))
            }

            HorizontalPagerBottomBarTecnico(tecnicoState.value, navController = navController)
        }

        composable("ticketDesplegadoTécnico/{ticket}",
            arguments = listOf(navArgument(name = "ticket")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            val ticket = Json.decodeFromString<Ticket>(navBackStackEntry.arguments!!.getString("ticket").toString())

            TicketDesplegadoTecnico(navController = navController, ticket)
        }

        composable("ticketDesplegadoCliente/{ticket}",
            arguments = listOf(navArgument(name = "ticket")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            val ticket = Json.decodeFromString<Ticket>(navBackStackEntry.arguments!!.getString("ticket").toString())

            TicketDesplegadoCliente(navController = navController, ticket)
        }

        composable("ticketDesplegadoBusqueda/{ticket}",
            arguments = listOf(navArgument(name = "ticket")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            val ticket = Json.decodeFromString<Ticket>(navBackStackEntry.arguments!!.getString("ticket").toString())

            TicketDesplegadoBusqueda(navController, ticket)
        }

        composable("crearUsuario")
        {
            CrearUsuario(navController)
        }

        composable(route = "cambiarContrasena"+ "/{usuario}",
            arguments = listOf(navArgument(name = "usuario")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            val usuario = Json.decodeFromString<Usuario>(navBackStackEntry.arguments!!.getString("usuario").toString())

            CambiarContrasena(navController, usuario)

        }

        composable(route = "informacionPerfilCliente"+ "/{clienteInterno}",
            arguments = listOf(navArgument(name = "clienteInterno")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            val clienteInterno = Json.decodeFromString<ClienteInterno>(navBackStackEntry.arguments!!.getString("clienteInterno").toString())

            PerfilClienteInterno(navController, clienteInterno)

        }

        composable("informacionPerfilTecnico" + "/{tecnico}",
            arguments = listOf(navArgument(name = "tecnico")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            var tecnicoState = remember{
                mutableStateOf<Tecnico>(Json.decodeFromString<Tecnico>(navBackStackEntry.arguments!!.getString("tecnico").toString()))
            }

            PerfilTecnico(navController, tecnicoState.value)

        }

        //Pantalla para cambiar datos del usuario
        composable("CambiarDatosUsuario" + "/{empleado}",
            arguments = listOf(navArgument(name = "empleado")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            val empleadoState = remember{
                mutableStateOf<Empleado>(Json.decodeFromString<Empleado>(navBackStackEntry.arguments!!.getString("empleado").toString()))
            }

            CambiarDatosUsuario(navController, empleadoState.value)

        }

        //Pantalla para los manuales

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AVANTITIGestionDeIncidenciasTheme {

    }
}