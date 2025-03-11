package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
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
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun InicioTecnico(navController: NavController)
{

    val context = LocalContext.current

    var idTecnicoState = remember{
        mutableStateOf(2)
    }
    // Se crea la variable de estado con la lista
    // Se comprueba si la lista esta vacia, de ser asi, se crea un aviso en el centro
    var ticketsTecnicoList = remember{
        mutableStateListOf<Ticket>()
    }

    val scope = rememberCoroutineScope()


    var pantallaCargaState = remember{
        mutableStateOf(false)
    }

    // Corrutina para obtener los tickets
    fetchTicketDataTecnico(idTecnicoState.value, { tickets ->

        ticketsTecnicoList.addAll(tickets)

    })


    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                title = {
                    Text("Inicio - Técnico", modifier = Modifier.fillMaxWidth().padding(0.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
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

                    items(ticketsTecnicoList.count()) { index ->

                        TicketsTecnico(ticketsTecnicoList[index], navController)

                    }
                })

            }



        }


    }

    // Se muestra los datos actualizados cuando la tabla ticket sufre algun cambio
    LaunchedEffect(Unit)
    {

        withContext(Dispatchers.IO) {

            TicketRequests().realtimeTicketRequestTecnicoId(scope, idTecnicoState.value) { tickets ->

                ticketsTecnicoList.clear()
                ticketsTecnicoList.addAll(tickets)

            }
        }
    }

}


@Composable
fun fetchTicketDataTecnico(id: Int, lambda: (tickets: List<Ticket>) -> Unit) {

    var dataset = mutableListOf<Ticket>()

    LaunchedEffect(Unit) {

        withContext(Dispatchers.IO) {

            TicketRequests().buscarTicketByTecnicoId(id) { tickets ->

                lambda(tickets)
            }

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsTecnico(ticket: Ticket, navController: NavController)
{

    var mostrarAlertDialogTicket = remember{
        mutableStateOf(false)
    }

    var empleado = Empleado()

    Box(modifier = Modifier.fillMaxWidth().clickable {

        //Se envia el ticket en formato JSON, y cuando se cambie de pantalla, se convierte en objeto Ticket otra vez
        val jsonState = Json.encodeToString(ticket)
        navController.navigate("TicketDesplegadoTécnico" + "/${jsonState}")

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

}

@Preview(showBackground = true)
@Composable
fun InicioTecnicoPreview() {

    val context = LocalContext.current
    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {

        InicioTecnico(navController)

    }
}
