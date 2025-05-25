package com.example.avantitigestiondeincidencias.ui.screens.administrador


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.ViewModel.InicioAdministradorViewModel
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.ui.screens.componentes.LoadingShimmerEffectScreen
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.TicketLoading
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun InicioAdministrador(/*empleado: Empleado, */
                        navController: NavController,
                        containerColor: Color = Color.Transparent,
                        viewModel: InicioAdministradorViewModel = viewModel())
{



    val ticketsAbiertos = viewModel.ticketAbiertos.collectAsState()

    var buscarTicketsAbiertos = remember {
        mutableStateOf(true)
    }

    obtenerTicketsAbiertos(viewModel) {
        buscarTicketsAbiertos.value = false
    }
    ScaffoldSimplePersonalizado(
        tituloPantalla = "Inicio",
        containerColor = containerColor
    ) {

        Column(modifier = Modifier.padding(25.dp))
        {
            Spacer(modifier = Modifier.padding(45.dp))

            Spacer(modifier = espacioSpacer)
            Text(text = " Últimos tickets: \n",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())

            LazyColumn(modifier = Modifier
                .height(480.dp)
                .fillMaxWidth(),
                content = {

                    if(buscarTicketsAbiertos.value)
                    {
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
                    }
                    else {
                        items(ticketsAbiertos.value.count()) { index ->

                            //Si el ticket no es abierto, se muestra en los ultimos tickets
                            ultimosTicketsLazyColumnContent(ticketsAbiertos.value[index], false, navController)

                        }
                    }

                })
        }

    }

    //Mostrar los cambios en tiempo real
    RealtimeTicketsAbiertos(viewModel)

}

@Composable
fun RealtimeTicketsAbiertos(viewModel: InicioAdministradorViewModel){

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit)
    {
        delay(1000)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.realtimeTicketAbiertos(scope)
        }
    }
}

@Composable
fun obtenerTicketsAbiertos(viewModel: InicioAdministradorViewModel, resultado: () -> Unit){

    LaunchedEffect(Unit) {

        CoroutineScope(Dispatchers.IO).launch{
            viewModel.obtenerTicketsAbiertos()
            delay(100)
            resultado()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun ultimosTicketsLazyColumnContent(ticket: Ticket, mostrarPrioridad: Boolean, navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(1.dp, 5.dp, 1.dp, 5.dp)
            .clickable {

                //Se envia el ticket en formato JSON, y cuando se cambie de pantalla, se convierte en objeto Ticket otra vez
                val jsonState = Json.encodeToString(ticket)
                navController.navigate("TicketDesplegadoAdministrador" + "/${jsonState}")


            })
    {

        Row(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
        {
            Column()
            {

                Text(text = ticket.tipo.tipoTicket + if (ticket.estado.id > 1) ": ${ticket.estado.tipoEstado}" else " ", fontWeight = FontWeight.Bold, modifier = Modifier.width(210.dp))

            }


            // Prioridad del ticket
            if(mostrarPrioridad) {
                Box(modifier = Modifier
                    .width(200.dp)
                    .border(width = 1.dp, shape = RectangleShape, color = Color.Black))
                {

                    Text(
                        text = ticket.prioridad.nivel,
                        fontSize = 13.sp,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }

        Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
        {
            Text(text = ticket.descripcion, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "${ticket.fecha} - ${ticket.hora}", fontSize = 12.sp)

            if(ticket.idTecnico > 1)
            {
                Row {
                    Text(text = "Encargado: ", fontWeight = FontWeight.Bold)
                    Text(text = "${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.primerApellido}")
                }

            }

        }

        HorizontalDivider()

    }
    Spacer(modifier = Modifier.padding(5.dp))

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    val context = LocalContext.current
    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {

        InicioAdministrador(navController)

    }
}
