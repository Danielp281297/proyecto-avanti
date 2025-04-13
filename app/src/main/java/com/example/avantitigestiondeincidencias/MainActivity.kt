package com.example.avantitigestiondeincidencias

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.BusquedaUsuarios
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.CrearUsuario
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.DatePickerScreen
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.HorizontalPagerBottomBarAdministrador
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.HorizontalPagerBottomBarTecnico
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.InformeTecnico
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.InicioAdministrador
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.InicioCliente
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.InicioTecnico
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.Login
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.PantallaNotificacion
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.PantallaPruebas
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.TicketDesplegadoAdministrador
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.TicketDesplegadoBusqueda
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.TicketDesplegadoCliente
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.TicketDesplegadoTecnico
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.nuevoTicketFormulario
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.showDatePicker
import com.example.avantitigestiondeincidencias.ui.screens.usuario.CrearUsuarioFormulario
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.serialization.json.Json

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
                    permisos(context)

                    //InicioCliente()
                    destination()
                    //InicioTecnico()
                    //showDatePicker(this)
                    //CrearUsuarioFormulario()
                    //InicioAdministrador()


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

    val permissionState = rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)

    val estadoPermiso = remember {
        mutableStateOf("Permiso no concedido")
    }
    // Si se concede el permiso, devuelve verdadero; y lo contrario es falso
    if (permissionState.status.isGranted)
    {
        estadoPermiso.value = "Permiso concedido"
    }
    else LaunchedEffect(true)
    {
        permissionState.launchPermissionRequest()
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
            //InformeTecnico()
            //PantallaNotificacion()
            //DatePickerScreen()
            //PantallaPruebas(navController)
            //BusquedaUsuarios(navController)
            //InicioCliente(navController)
            //nuevoTicketFormulario(3) { }
            //HorizontalPagerBottomBarAdministrador(Empleado(), navController = navController)
            //TicketDesplegadoAdministrador(navController, Ticket())
            //TicketDesplegadoTecnico(navController, Ticket())
        }

        composable("login")
        {

        }

        composable(route = "principalCliente"+ "/{empleado}",
            arguments = listOf(navArgument(name = "empleado")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            val empleado = Json.decodeFromString<Empleado>(navBackStackEntry.arguments!!.getString("empleado").toString())

            //HorizontalPagerBottomBarAdministrador(empleado, navController = navController)
            InicioCliente(empleado, navController)

        }

        composable("principalAdministrador" + "/{empleado}",
            arguments = listOf(navArgument(name = "empleado")
            {type = NavType.StringType}))
        { navBackStackEntry ->

            val empleado = Json.decodeFromString<Empleado>(navBackStackEntry.arguments!!.getString("empleado").toString())

            HorizontalPagerBottomBarAdministrador(empleado, navController = navController)
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

            val tecnico = Json.decodeFromString<Tecnico>(navBackStackEntry.arguments!!.getString("tecnico").toString())

            HorizontalPagerBottomBarTecnico(tecnico, navController = navController)
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

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AVANTITIGestionDeIncidenciasTheme {

    }
}