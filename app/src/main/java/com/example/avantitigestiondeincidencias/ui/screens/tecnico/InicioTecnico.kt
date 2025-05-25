package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.ViewModel.InicioTecnicoViewModel
import com.example.avantitigestiondeincidencias.ui.screens.componentes.LoadingShimmerEffectScreen
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.TicketLoading
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun InicioTecnico(
    tecnico: Tecnico,
    navController: NavController,
    containerColor: Color = Color.Transparent,
    viewModel: InicioTecnicoViewModel = viewModel()
)
{
    // Se crea la variable de estado con la lista
    // Se comprueba si la lista esta vacia, de ser asi, se crea un aviso en el centro
    val ticketsAsignados = viewModel.ticketsAsignados.collectAsState()

    var buscarTicketsTecnico = remember {
        mutableStateOf(true)
    }

    val scope = rememberCoroutineScope()

    buscarTicketTecnico(viewModel, tecnico.id) {
        buscarTicketsTecnico.value = false
    }

    ScaffoldSimplePersonalizado(
        tituloPantalla = "Inicio - Técnico",
        containerColor = containerColor
    ){

        Box(modifier = Modifier.fillMaxSize().padding(25.dp)
        )
        {

            Column(modifier = Modifier.fillMaxSize())
            {

                Spacer(modifier = Modifier.padding(45.dp))

                Text(text = "Bienvenido, ${tecnico.empleado.usuario.nombre}")
                Text(text = " Últimos tickets: \n",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth())

                LazyColumn(modifier = Modifier.height(520.dp).fillMaxWidth(), content = {

                    if(buscarTicketsTecnico.value){
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
                    }else
                    {
                        items(ticketsAsignados.value.count()) { index ->

                            TicketsTecnico(ticketsAsignados.value[index], navController)

                        }
                    }

                })

            }
        }
    }
    // Se muestra los datos actualizados cuando la tabla ticket sufre algun cambio
    LaunchedEffect(Unit)
    {
        CoroutineScope(Dispatchers.IO).launch {

            viewModel.realtimeTicketsAsignados(scope, tecnico.id)
        }
    }

}

@Composable
fun buscarTicketTecnico(viewModel: InicioTecnicoViewModel, id: Int, resultados: () -> Unit)
{
    LaunchedEffect(Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            viewModel.obtenerTicketsAsignados(id)
            delay(100)
            resultados()

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsTecnico(ticket: Ticket, navController: NavController)
{

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(Color.Transparent)
            .padding(1.dp, 5.dp, 1.dp, 5.dp)
            .clickable {

                //Se envia el ticket en formato JSON, y cuando se cambie de pantalla, se convierte en objeto Ticket otra vez
                val jsonState = Json.encodeToString(ticket)
                navController.navigate("ticketDesplegadoTécnico" + "/${jsonState}")


            })
    {

        Row(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
        {
            Column()
            {

                Text(text = ticket.tipo.tipoTicket, fontWeight = FontWeight.Bold, modifier = Modifier.width(210.dp))

            }

            // Prioridad del ticket
            Box(modifier = Modifier.width(200.dp).border(width = 1.dp , shape = RectangleShape, color = Color.Black))
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

        Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
        {
            Text(text = ticket.descripcion, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(text = "${ticket.fecha} - ${ticket.hora}", fontSize = 12.sp)
        }

        HorizontalDivider()

    }
    Spacer(modifier = Modifier.padding(5.dp))

}

@Preview(showBackground = true)
@Composable
fun InicioTecnicoPreview() {

    val context = LocalContext.current
    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {

        InicioTecnico(Tecnico(), navController)

    }
}
