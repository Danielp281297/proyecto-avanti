package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.MenuLateralContenido
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldConMenuLateral
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InicioCliente(empleado: Empleado, navController: NavController)
{

    val context = LocalContext.current

    var clienteInterno = remember{
        mutableStateOf<ClienteInterno>(ClienteInterno())
    }

    // Se crea la variable de estado con la lista
    // Se comprueba si la lista esta vacia, de ser asi, se crea un aviso en el centro
    var ticketsCliente = remember{
        mutableStateListOf<Ticket>()
    }

    var mostrarFormularioNuevoTicket = remember{
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()


    var pantallaCargaState = remember{
        mutableStateOf(false)
    }

    //Se obtiene el id del cliente interno
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {

            clienteInterno.value = EmpleadoRequest().seleccionarClienteById(empleado.id)

        }
    }

    // Corrutina para obtener los tickets
    fetchTicketDataCliente(empleado.id, { tickets ->

        ticketsCliente.addAll(tickets)

    })



    ScaffoldConMenuLateral("Inicio - Cliente Interno", {
        MenuLateralContenido(clienteInterno.value.empleado, {}, {}, {})
    },{

        Box(
            modifier = Modifier.fillMaxSize().padding(25.dp)
        )
        {

            Column(modifier = Modifier.fillMaxSize())
            {
                Spacer(modifier = Modifier.padding(45.dp))

                Text(text = "Bienvenido, ${clienteInterno.value.empleado.usuario.nombre}")
                Text(
                    text = " Últimos tickets: \n",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                LazyColumn(modifier = Modifier.height(520.dp).fillMaxWidth(), content = {

                    items(ticketsCliente.count()) { index ->

                        TicketsCliente(navController, ticketsCliente[index])

                    }
                })

            }

            FloatingActionButton(
                onClick = {

                    mostrarFormularioNuevoTicket.value = true

                },
                shape = RectangleShape,
                containerColor = Color.White,
                modifier = Modifier.align(Alignment.BottomEnd).padding(0.dp, 50.dp)
            ) {

                Icon(
                    painter = painterResource(R.drawable.nuevo_ticket_icon),
                    contentDescription = "Crear Nuevo Ticket",
                    modifier = Modifier.size(40.dp)
                )

            }

        }
    })

    // Bottom Sheet
    if (mostrarFormularioNuevoTicket.value) {

        AlertDialog(
            modifier = Modifier.wrapContentHeight(),
            onDismissRequest = { mostrarFormularioNuevoTicket.value = false },
            content = {

                // Se almacena los datos en un nuevo ticket
                nuevoTicketFormulario(clienteInterno.value.id, {

                    mostrarFormularioNuevoTicket.value = false

                })

            }

        )

    }

    // Se muestra los datos actualizados cuando la tabla ticket sufre algun cambio
    LaunchedEffect(Unit)
    {

        withContext(Dispatchers.IO) {
            TicketRequests().realtimeTicketRequestClienteInternoId(scope, clienteInterno.value.id) { tickets ->

                ticketsCliente.clear()

                tickets.forEach { item ->

                    if (item.clienteInterno.id == clienteInterno.value.id)
                        ticketsCliente.add(item)

                }


                ticketsCliente.reversed().forEach { item ->

                    ticketsEstadosNotificaciones(context, item)

                }

            }
        }
    }

}

fun ticketsEstadosNotificaciones(context: Context, ticket: Ticket)
{

    // Se busca si el estado del ticket esta En Proceso

    if(ticket.idEstadoTicket == 2)
    {

        Notification().mostrarNotificacion(context, "${ticket.tipo.tipoTicket} - ${ticket.descripcion}"
            , "Su ${ticket.tipo.tipoTicket} está siendo atendida por ${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.primerApellido}")

    }

    if(ticket.idEstadoTicket == 4)
    {

        Notification().mostrarNotificacion(context, "${ticket.tipo.tipoTicket} - ${ticket.descripcion}"
            , "Su ${ticket.tipo.tipoTicket} fue resuelta por ${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.primerApellido}")

    }
}



@Composable
fun fetchTicketDataCliente(id: Int, lambda: (tickets: List<Ticket>) -> Unit) {

    LaunchedEffect(Unit) {

        withContext(Dispatchers.IO) {

            TicketRequests().buscarTicketByClienteInternoId(id) { tickets ->

                lambda(tickets)
            }

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsCliente(navController: NavController, ticket: Ticket)
{

    Box(modifier = Modifier.fillMaxWidth().clickable {

        val jsonState = Json.encodeToString(ticket)
        navController.navigate("TicketDesplegadoCliente" + "/${jsonState}")

    })
    {
        Column(modifier = Modifier.fillMaxWidth().padding(1.dp, 5.dp, 1.dp, 5.dp))
        {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text(text = " ${ticket.tipo.tipoTicket}: ${ticket.estado.tipoEstado}")
                Text(text = ticket.fecha)
            }
            //Text(text = " Ticket: ${ticket.id}") // Numero de ticket
            //Text(text = " Fecha: ${ticket.fecha}")
            //Text(text = " Hora: ${ticket.hora}")

            Text(text = " ${ticket.descripcion}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            // Si el id del tecnico encargado es diferente de 1, se muestra su nombre completo
            Text(fontSize = 13.sp, text = " Técnico Encargado: " + if(ticket.tecnico.id > 1){ "${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.segundoNombre} ${ticket.tecnico.empleado.primerApellido} ${ticket.tecnico.empleado.segundoApellido}"} else " ")

            HorizontalDivider()

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDesplegadoCliente(navController: NavController, ticket:Ticket)
{

    var borrarTicketState = remember {
        mutableStateOf(false)
    }

        ContenidoTicketDesplegado(navController, ticket) {

            if (ticket.idEstadoTicket == 1) {
                androidx.compose.material3.Button(
                    modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                        borrarTicketState.value = true

                    }
                )
                {
                    Text(text = "BORRAR TICKET", color = Color.White)
                }
            }

        }

    if (borrarTicketState.value)
    {
        LaunchedEffect(Unit) {

            TicketRequests().borrarTicket(ticket)

        }

        //navController.popBackStack()
        borrarTicketState.value = false

    }

}




@Preview(showBackground = true)
@Composable
fun InicioClientePreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {

        MenuLateralContenido(Empleado(), {}, {}, {})

    }
}

