package com.example.avantitigestiondeincidencias

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
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
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.ui.screens.PedirPermiso
import com.example.avantitigestiondeincidencias.ui.screens.perfil.CambiarContrasena
import com.example.avantitigestiondeincidencias.ui.screens.perfil.CambiarDatosUsuario
import com.example.avantitigestiondeincidencias.ui.screens.administrador.CrearUsuario
import com.example.avantitigestiondeincidencias.ui.screens.administrador.HorizontalPagerBottomBarAdministrador
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.HorizontalPagerBottomBarTecnico
import com.example.avantitigestiondeincidencias.ui.screens.cliente.InicioCliente
import com.example.avantitigestiondeincidencias.ui.screens.usuario.Login
import com.example.avantitigestiondeincidencias.ui.screens.PantallaPruebas
import com.example.avantitigestiondeincidencias.ui.screens.perfil.PerfilClienteInterno
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.PerfilTecnico
import com.example.avantitigestiondeincidencias.ui.screens.administrador.TicketDesplegadoAdministrador
import com.example.avantitigestiondeincidencias.ui.screens.ticket.TicketDesplegadoBusqueda
import com.example.avantitigestiondeincidencias.ui.screens.cliente.TicketDesplegadoCliente
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.TicketDesplegadoTecnico
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

val paddingPantallas = Modifier
    .fillMaxSize()
    .padding(15.dp)
val espacioSpacer = Modifier.padding(7.5.dp)
val modeloButton = Modifier
    .fillMaxWidth()
    .height(50.dp)

//Shared preferences para almacenar si el usuario esta abierto, el json y el tipo de usuario

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AVANTITIGestionDeIncidenciasTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) {

                    // Primero, se pide que el usuario conceda permisos para acceder al almacenamiento del dispositivo,
                    // y a la llegada de notificaciones
                    PedirPermiso(this, Manifest.permission.POST_NOTIFICATIONS)
                    destination(this)

                }
            }
        }
    }
}

@Composable
fun destination(context: Context)
{

    val context = LocalContext.current

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "pantallaPruebas")//"pantallaPruebas")
    {


        composable("pantallaPruebas")
        {

            //HorizontalPagerBottomBarTecnico(/*Empleado(), */navController)
            /*
            Login(navController, context, { tipo, json ->
                navController.popBackStack()
                when(tipo)
                {
                    // Ingresar a la pantalla de Tecnico
                    1 -> navController.navigate("principalTécnico" + "/${json}")

                    2 -> navController.navigate("principalCliente" + "/${json}")

                    3 -> navController.navigate("principalAdministrador" + "/${json}")

                }

            })
            */
            //CrearUsuario(navController, context)
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
            //ManualClienteInterno()

        }

        composable("login")
        {

            Login(navController, context, { tipo, json ->
                navController.popBackStack()
                when(tipo)
                {
                    // Ingresar a la pantalla de Tecnico
                    1 -> navController.navigate("principalTécnico" + "/${json}")

                    2 -> navController.navigate("principalCliente" + "/${json}")

                    3 -> navController.navigate("principalAdministrador" + "/${json}")

                }

            })

        }

            composable(
                route = "principalCliente" + "/{clienteInterno}",
                arguments = listOf(
                    navArgument(name = "clienteInterno")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                val clienteInterno = Json.decodeFromString<ClienteInterno>(
                    navBackStackEntry.arguments!!.getString("clienteInterno").toString()
                )

                InicioCliente(clienteInterno, navController)

            }

            composable(
                "principalAdministrador" + "/{administrador}",
                arguments = listOf(
                    navArgument(name = "administrador")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                val tecnico =
                    Json.decodeFromString<Tecnico>(navBackStackEntry.arguments!!.getString("administrador").toString())

                HorizontalPagerBottomBarAdministrador(navController, context, tecnico)
            }

            composable(
                "ticketDesplegadoAdministrador/{ticket}",
                arguments = listOf(
                    navArgument(name = "ticket")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                val ticket = Json.decodeFromString<Ticket>(navBackStackEntry.arguments!!.getString("ticket").toString())

                TicketDesplegadoAdministrador(navController = navController, context, ticket)
            }

            composable(
                "principalTécnico" + "/{tecnico}",
                arguments = listOf(
                    navArgument(name = "tecnico")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                var tecnicoState = remember {
                    mutableStateOf<Tecnico>(
                        Json.decodeFromString<Tecnico>(
                            navBackStackEntry.arguments!!.getString("tecnico").toString()
                        )
                    )
                }

                HorizontalPagerBottomBarTecnico(navController = navController, context, tecnicoState.value)
            }

            composable(
                "ticketDesplegadoTécnico/{ticket}",
                arguments = listOf(
                    navArgument(name = "ticket")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                val ticket = Json.decodeFromString<Ticket>(navBackStackEntry.arguments!!.getString("ticket").toString())

                TicketDesplegadoTecnico(navController = navController, context, ticket)
            }

            composable(
                "ticketDesplegadoCliente/{ticket}",
                arguments = listOf(
                    navArgument(name = "ticket")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                val ticket = Json.decodeFromString<Ticket>(navBackStackEntry.arguments!!.getString("ticket").toString())

                TicketDesplegadoCliente(navController = navController, ticket)
            }

            composable(
                "ticketDesplegadoBusqueda/{ticket}",
                arguments = listOf(
                    navArgument(name = "ticket")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                val ticket = Json.decodeFromString<Ticket>(navBackStackEntry.arguments!!.getString("ticket").toString())

                TicketDesplegadoBusqueda(navController, context, ticket)
            }

            composable("crearUsuario")
            {
                CrearUsuario(navController, context)
            }

            composable(
                route = "cambiarContrasena" + "/{usuario}",
                arguments = listOf(
                    navArgument(name = "usuario")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                val usuario =
                    Json.decodeFromString<Usuario>(navBackStackEntry.arguments!!.getString("usuario").toString())

                CambiarContrasena(navController, context, usuario)

            }

            composable(
                route = "informacionPerfilCliente" + "/{clienteInterno}",
                arguments = listOf(
                    navArgument(name = "clienteInterno")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                val clienteInterno = Json.decodeFromString<ClienteInterno>(
                    navBackStackEntry.arguments!!.getString("clienteInterno").toString()
                )

                PerfilClienteInterno(navController, context, clienteInterno)

            }

            composable(
                "informacionPerfilTecnico" + "/{tecnico}",
                arguments = listOf(
                    navArgument(name = "tecnico")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                var tecnico = remember {
                    mutableStateOf<Tecnico>(
                        Json.decodeFromString<Tecnico>(
                            navBackStackEntry.arguments!!.getString("tecnico").toString()
                        )
                    )
                }

                PerfilTecnico(navController, context, tecnico.value)

            }

            //Pantalla para cambiar datos del usuario
            composable(
                "CambiarDatosUsuario" + "/{empleado}",
                arguments = listOf(
                    navArgument(name = "empleado")
                    { type = NavType.StringType })
            )
            { navBackStackEntry ->

                val empleadoState = remember {
                    mutableStateOf<Empleado>(
                        Json.decodeFromString<Empleado>(
                            navBackStackEntry.arguments!!.getString("empleado").toString()
                        )
                    )
                }

                CambiarDatosUsuario(navController, context, empleadoState.value)

            }

            //Pantalla para los manuales

    }

}

@Composable
fun obtenerUsuariobyEmpleadoID(idUsuario: Int, resultado: (Usuario) -> Unit)
{

    LaunchedEffect(Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            UsuarioRequest().seleccionarUsuarioById(idUsuario){
                resultado(it)
            }

        }

    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AVANTITIGestionDeIncidenciasTheme {

    }
}