package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InicioCliente(navController: NavController)
{

    val context = LocalContext.current

    var idClienteInternoState = remember{
        mutableStateOf(3)
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

    // Corrutina para obtener los tickets
    fetchTicketDataCliente(idClienteInternoState.value, { tickets ->

        ticketsCliente.addAll(tickets)

    })


    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                title = {
                    Text("Inicio - Cliente Interno", modifier = Modifier.fillMaxWidth().padding(0.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            )
        }
    ) {

        Box(modifier = Modifier.fillMaxSize().padding(25.dp)
            )
        {

            Column(modifier = Modifier.fillMaxSize())
            {

                Spacer(modifier = Modifier.padding(45.dp))

                Text(text = "Bienvenido,")
                Text(text = " Últimos tickets: \n",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth())

                LazyColumn(modifier = Modifier.height(520.dp).fillMaxWidth(), content = {

                    items(ticketsCliente.count()) { index ->

                        TicketsCliente(ticketsCliente[index])

                    }
                })

            }

            FloatingActionButton(onClick = {

                mostrarFormularioNuevoTicket.value = true

            },
                                shape = RectangleShape,
                                containerColor = Color.Black,
                                modifier = Modifier.align(Alignment.BottomEnd).padding(0.dp, 50.dp)) {

                Icon(tint = Color.White, painter = painterResource(R.drawable.nuevo_ticket_logo), contentDescription = "Crear Nuevo Ticket", modifier = Modifier.size(40.dp))

            }

        }

        // Bottom Sheet
        if(mostrarFormularioNuevoTicket.value)
        {

            AlertDialog(
                modifier = Modifier.wrapContentHeight(),
                onDismissRequest = { mostrarFormularioNuevoTicket.value = false },
                content = {

                    // Se almacena los datos en un nuevo ticket
                    nuevoTicketFormulario(idClienteInternoState.value, {

                        mostrarFormularioNuevoTicket.value = false

                    })

                }

            )

        }

    }

    // Se muestra los datos actualizados cuando la tabla ticket sufre algun cambio
    LaunchedEffect(Unit)
    {

        withContext(Dispatchers.IO) {
            TicketRequests().realtimeTicketRequestClienteInternoId(scope, idClienteInternoState.value) { tickets ->

                ticketsCliente.clear()

                tickets.forEach { item ->

                    ticketsCliente.add(item)

                }

                tickets.reversed().forEach { item ->

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

        //Log.e("EN PROGRESO", "Ticket: ${ticket.id}")

        Notification().mostrarNotificacion(context, "${ticket.tipo.tipoTicket} - ${ticket.descripcion}"
            , "Su ${ticket.tipo.tipoTicket} está siendo atendida por ${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.primerApellido}")

    }

    if(ticket.idEstadoTicket == 4)
    {

        //Log.e("EN PROGRESO", "Ticket: ${ticket.id}")

        Notification().mostrarNotificacion(context, "${ticket.tipo.tipoTicket} - ${ticket.descripcion}"
            , "Su ${ticket.tipo.tipoTicket} fue resuelta por ${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.primerApellido}")

    }



}


@Composable
fun fetchTicketDataCliente(id: Int, lambda: (tickets: List<Ticket>) -> Unit) {

    var dataset = mutableListOf<Ticket>()

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
fun TicketsCliente(ticket: Ticket)
{

    var mostrarAlertDialogTicket = remember{
        mutableStateOf(false)
    }

    var empleado = Empleado()

    Box(modifier = Modifier.fillMaxWidth().clickable {

        mostrarAlertDialogTicket.value = true

    })
    {
        Column(modifier = Modifier.fillMaxWidth().padding(1.dp, 5.dp, 1.dp, 5.dp).border(1.dp, Color.Black))
        {

            Text(text = " Ticket: ${ticket.id}") // Numero de ticket
            Text(text = " Fecha: ${ticket.fecha}")
            Text(text = " Hora: ${ticket.hora}")
            Text(text = " Tipo de ticket: ${ticket.tipo.tipoTicket}")
            Text(text = " Descripción: ${ticket.descripcion}")
            Text(text = " Estado: ${ticket.estado.tipoEstado}")
            // Si el id del tecnico encargado es diferente de 1, se muestra su nombre completo
            Text(text = " Tecnico Encargado: " + if(ticket.tecnico.id > 1){ "${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.segundoNombre} ${ticket.tecnico.empleado.primerApellido} ${ticket.tecnico.empleado.segundoApellido}"} else " ")

            // Se obtine los datos del tecnico encargado
            LaunchedEffect(Unit)
            {
                withContext(Dispatchers.IO) {
                    //empleado = EmpleadoRequest().buscarEmpleadoById(ticket.idTecnico)
                }
            }



            //Text(text = " Técnico Encargado: ${empleado.primerNombre} ${empleado.segundoNombre} ${empleado.primerApellido} ${empleado.segundoApellido}")

        }
    }

    if (mostrarAlertDialogTicket.value)
    {

        AlertDialog(
            modifier = Modifier.wrapContentHeight(),
            onDismissRequest = { mostrarAlertDialogTicket.value = false },
            content = {

                TicketDesplegado(ticket)

            }

        )

    }

}


@Preview(showBackground = true)
@Composable
fun InicioClientePreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {

        InicioCliente(navController)

    }
}

