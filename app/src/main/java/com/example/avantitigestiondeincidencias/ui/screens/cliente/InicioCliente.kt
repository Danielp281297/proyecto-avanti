package com.example.avantitigestiondeincidencias.ui.screens.cliente

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.ViewModel.InicioClienteViewModel
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.LoadingShimmerEffectScreen
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldConMenuLateral
import com.example.avantitigestiondeincidencias.ui.screens.componentes.TicketLoading
import com.example.avantitigestiondeincidencias.ui.screens.ticket.ContenidoTicketDesplegado
import com.example.avantitigestiondeincidencias.ui.screens.perfil.MenuLateralContenido
import com.example.avantitigestiondeincidencias.ui.screens.usuario.usuarioDeshabilitado
import com.example.avantitigestiondeincidencias.ui.screens.usuario.validarUsuarioInhabilitado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InicioCliente(
    clienteInterno: ClienteInterno,
    navController: NavController,
    viewModel: InicioClienteViewModel = viewModel(),
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
    )
{

    val context = LocalContext.current

    // Se crea la variable de estado con la lista
    // Se comprueba si la lista esta vacia, de ser asi, se crea un aviso en el centro
    val ticketsCliente = viewModel.clienteTickets.collectAsState()

    /*
    var ticketsCliente = remember{
        mutableStateListOf<Ticket>()
    }
    */

    var mostrarFormularioNuevoTicket = remember{
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()


    var buscandoTicketsCliente = remember {
        mutableStateOf(true)
    }

    Network.networkCallback(navController, context)

    // Se compureba que el usuario no fue inhabilitado
    usuarioDeshabilitado(navController, clienteInterno.empleado.usuario)


    TiempoHabilitarClienteInterno(containerColor)
    {

        // Se obtienen los datos de la base de datos, y se muestra en la pantalla
        fetchTicketDataCliente(viewModel, clienteInterno.empleado.id) {
            buscandoTicketsCliente.value = false
        }

        ScaffoldConMenuLateral(
            titulo = "Inicio - Cliente Interno",
            containerColor = containerColor,
            contenidoMenu = {
                MenuLateralContenido(
                    navController, context, clienteInterno.empleado,
                    perfil = {
                        // Se covierte el objeto en json para enviarlo a la pantalla
                        val json = Json { ignoreUnknownKeys = true }.encodeToString(clienteInterno)

                        navController.navigate("informacionPerfilCliente" + "/${json}")
                    },
                    manualUsuarioEvento = {
                        navController.navigate("ManualClienteInterno")
                    },
                )
        }, fondoPantalla =  {


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp)
            )
            {

                Column(modifier = Modifier.fillMaxSize())
                {
                    Spacer(modifier = Modifier.padding(45.dp))

                    Text(text = "Bienvenido, ${clienteInterno.empleado.usuario.nombre}")
                    Text(
                        text = " Últimos tickets: \n",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(), content = {

                            if (buscandoTicketsCliente.value) {
                                items(10)
                                {

                                    Column(modifier = Modifier.fillMaxSize()) {
                                        LoadingShimmerEffectScreen(
                                            true,
                                            {
                                                TicketLoading()
                                            },
                                            {}
                                        )
                                    }
                                }
                            } else {
                                items(ticketsCliente.value.count()) { index ->

                                    TicketsCliente(navController, ticketsCliente.value[index])

                                }
                            }
                        })

                }

                ExtendedFloatingActionButton(
                    onClick = {

                        mostrarFormularioNuevoTicket.value = true
                    },
                    text = {
                        Icon(
                            painter = painterResource(R.drawable.nuevo_ticket_icon),
                            contentDescription = "Crear Nuevo Ticket",
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    contentColor = Color.White,
                    backgroundColor = containerColor,
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(0.dp, 50.dp)
                        .align(Alignment.BottomEnd)
                        .border(1.dp, Color.LightGray, RectangleShape),
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                )

            }

        })
    }

    // Bottom Sheet
    if (mostrarFormularioNuevoTicket.value) {

        AlertDialog(
            modifier = Modifier.wrapContentHeight(),
            onDismissRequest = { mostrarFormularioNuevoTicket.value = false },
            content = {

                // Se almacena los datos en un nuevo ticket
                nuevoTicketFormulario(clienteInterno.id, containerColor, lambda = {

                    mostrarFormularioNuevoTicket.value = false

                })

            }

        )

    }

    validarUsuarioInhabilitado(navController, clienteInterno.empleado.usuario)

    // Se esperan los cambios para mostrarlos en la tabla en tiempo real
    LaunchedEffect(Unit)
    {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.realtimeClienteTickets(context, this, clienteInterno.id)
        }
    }

}

@Composable
fun fetchTicketDataCliente(viewModel: InicioClienteViewModel, id: Int, lambda: () -> Unit) {

    LaunchedEffect(Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            viewModel.obtenerClienteTickets(id)
            delay(100)
            lambda()

        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsCliente(navController: NavController, ticket: Ticket)
{

    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {

            val jsonState = Json.encodeToString(ticket)
            navController.navigate("TicketDesplegadoCliente" + "/${jsonState}")

        })
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp, 5.dp, 1.dp, 5.dp))
        {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text(text = " ${ticket.tipo.tipoTicket}: ${ticket.estado.tipoEstado}")
                Text(text = ticket.fecha)
            }
            //Text(text = " Ticket: ${ticket.id}") // Numero de ticket
            //Text(text = " Fecha: ${ticket.fecha}")
            //Text(text = " Hora: ${ticket.hora}")

            Text(text = " ${ticket.descripcion}", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            // Si el id del tecnico encargado es diferente de 1, se muestra su nombre completo
            Text(fontSize = 13.sp, text = " Técnico Encargado: " + if(ticket.tecnico.id > 1){ "${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.segundoNombre} ${ticket.tecnico.empleado.primerApellido} ${ticket.tecnico.empleado.segundoApellido}"} else " ")

            HorizontalDivider()

        }
    }

}



@Composable
fun TiempoHabilitarClienteInterno(
    containerColor: Color,
    casoContrario: @Composable () -> Unit
) {

    var tiempoFueraRango = remember {
        mutableStateOf(false)
    }

    val horaPeriodoInicio = LocalTime.of(8, 0)
    val horaPeriodoFin = LocalTime.of(17, 30)

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            do {

                val horaActual = LocalTime.now()

                // Se comprueba que la aplicacion haya sido aceptado en la hora
                if (horaActual.isBefore(horaPeriodoInicio) || horaActual.isAfter(horaPeriodoFin))
                    tiempoFueraRango.value = true
                else
                    tiempoFueraRango.value = false

                delay(5000)

            } while (true)

        }
    }

    if (tiempoFueraRango.value)
    {
        Log.d("TIEMPO", "FUERA DE RANGO")
        Column (modifier = Modifier
            .fillMaxSize()
            .background(containerColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Text("Hora fuera de rango. Ingrese de 8:00am a 5:30pm para poder crear tickets.",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold)
        }

    }
    else
    {
        casoContrario()
    }

}

@Preview(showBackground = true)
@Composable
fun InicioClientePreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {

        InicioCliente(ClienteInterno(), navController)

    }
}

