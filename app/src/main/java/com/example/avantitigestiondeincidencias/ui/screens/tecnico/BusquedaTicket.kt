package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.ui.screens.componentes.LoadingShimmerEffectScreen
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.TicketLoading
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaTicket(
    navController: NavController,
    context: Context,
    tecnico: Tecnico,
    clickAccion: (String) -> Unit,
    containerColor: Color = Color.Transparent)
{

    var entradaBusquedaState = remember {
        mutableStateOf("")
    }

    var dataset = remember{
        mutableStateListOf<Ticket>()
    }

    var showDatePickerState = remember {
        mutableStateOf(false)
    }

    var buscarTicketsTecnico = remember {
        mutableStateOf(true)
    }

    var banderaState = remember {
        mutableStateOf(0)
    }

    var fechaInicioState = remember {
        mutableStateOf("")
    }

    var fechaFinalState = remember {
        mutableStateOf("")
    }

    // Se obtiene los ultimos 50 tickets
    ScaffoldSimplePersonalizado(
        tituloPantalla = "Buscar ticket",
        containerColor = containerColor
    ){

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp))
        {
            Spacer(modifier = Modifier.padding(45.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = entradaBusquedaState.value,
                    onValueChange = {
                        entradaBusquedaState.value = it
                    },
                    label = {
                        Text(" Contenido del ticket...")
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = Color.Black,
                        focusedBorderColor = Color.Black
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.padding(5.dp))
                IconButton(modifier = Modifier, onClick = {

                    buscarTicketsTecnico.value = true

                })
                {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Boton Busqueda",
                        modifier = Modifier.size(45.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))

            Column() {
                Text("Fecha", modifier = Modifier.fillMaxWidth())
                Row(modifier = Modifier.fillMaxWidth())
                {

                    Column(modifier = Modifier.weight(1F))
                    {

                        Text("Fecha Inicial:")
                        Column(modifier = Modifier.clickable {
                            showDatePickerState.value = true
                            banderaState.value = 1
                        })
                        {

                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = fechaInicioState.value,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            )

                            HorizontalDivider(thickness = 1.dp)
                        }

                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Fecha Final:")
                        Column(modifier = Modifier.clickable {
                            showDatePickerState.value = true
                            banderaState.value = 2
                        })
                        {
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = fechaFinalState.value,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            )
                            HorizontalDivider(thickness = 1.dp)

                        }

                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
            }
            HorizontalDivider()
            //Mientras se cargan los tickets, se muestran una pantalla de carga
            LazyColumn(modifier = Modifier
                .height(400.dp)
                .fillMaxWidth(), content = {
                    if(buscarTicketsTecnico.value) {

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
                  else
                    {


                        items(dataset.count()) { index ->

                            TicketBusqueda(dataset[index]){ clickAccion(it) }

                        }

                    }

            })

        }

    }

    if (showDatePickerState.value)
    {
        var datePickerInput = ""


        DatePicker(
            showDialog = showDatePickerState.value,
            containerColor = containerColor,
            ondismiss = { showDatePickerState.value = false },
            fecha = {
            datePickerInput = it

            if(banderaState.value == 1)
            {
                fechaInicioState.value = datePickerInput
            }
            else if (banderaState.value == 2)
            {
                fechaFinalState.value = datePickerInput
            }

            showDatePickerState.value = false
        })

    }

    if(buscarTicketsTecnico.value)
    {
        buscarTicketsByTecnicoIDYFechas(
            tecnicoId = tecnico.id,
            fechaInicio = fechaInicioState.value,
            fechaFin = fechaFinalState.value,
            descripcion = entradaBusquedaState.value
        ) { tickets ->

            dataset.clear()
            dataset.addAll(tickets)
            // Se termina con la condicion en falso, para que funcione bien...
            buscarTicketsTecnico.value = false
        }

    }

}

@Composable
fun buscarTicketsByTecnicoIDYFechas(
    tecnicoId: Int,
    fechaInicio: String,
    fechaFin: String,
    descripcion: String,
    resultados:(List<Ticket>) -> Unit)
{

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            TicketRequests().buscarTicketsByTecnicoIdYFechas(
                id = tecnicoId,
                fechaInicio = fechaInicio,
                fechaFin = fechaFin,
                descripcion = descripcion,
                tickets = {
                    resultados(it)
                }
            )

        }
    }

}

@Composable
fun TicketBusqueda(ticket: Ticket, clickAccion: (String) -> Unit)
{

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(1.dp, 5.dp, 1.dp, 5.dp)
            .clickable {

                //Se envia el ticket en formato JSON, y cuando se cambie de pantalla, se convierte en objeto Ticket otra vez
                val json = Json.encodeToString(ticket)
                clickAccion(json)

            })
    {
        Spacer(modifier = Modifier.padding(5.dp))

        Row(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
        {
            Column()
            {

                Text(text = "${ticket.tipo.tipoTicket}: ${ ticket.estado.tipoEstado}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(210.dp))

            }

            // Prioridad del ticket
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

        Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
        {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = ticket.descripcion, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
            Text(text = "${ticket.fecha} - ${ticket.hora}", fontSize = 12.sp)
        }

        HorizontalDivider()

    }


}

@Composable
fun TicketDesplegadoBusqueda(navController: NavController, context: Context, ticket: Ticket)
{
    ContenidoTicketDesplegado(navController, context, ticket)
    {
        
    }
}

@Preview(showBackground = true)
@Composable
fun BusquedaTecnicoPreview() {

    val context = LocalContext.current
    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {

        //BusquedaTicket(navController, context, Tecnico())
        //TicketBusqueda(navController, Ticket()){}

    }
}
