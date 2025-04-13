package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFontSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaTecnico(navController: NavController)
{

    var entradaBusquedaState = remember {
        mutableStateOf("")
    }

    var ultimosTickets = remember{
        mutableStateListOf<Ticket>()
    }

    // Se obtiene los ultimos 50 tickets
    LaunchedEffect(Unit) {
        ultimosTickets.clear()
        withContext(Dispatchers.IO) {
            TicketRequests().mostrarTablaTicket { tickets ->
                tickets.forEach {
                    ultimosTickets.add(it)
                }
            }

        }
    }

    Log.d("ULTIMOS TICKETS", ultimosTickets.toString())

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                title = {
                    Text("Busqueda", modifier = Modifier.fillMaxWidth().padding(0.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                }
            )
        }
    ) {

        Column(modifier = Modifier.fillMaxSize().padding(15.dp))
        {
            Spacer(modifier = Modifier.padding(45.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = entradaBusquedaState.value,
                    onValueChange = {
                        entradaBusquedaState.value = it
                    },
                    label = {
                        Text(" Nombre de usuario a buscar...")
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.padding(5.dp))
                IconButton(modifier = Modifier, onClick = {})
                {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Boton Busqueda",
                        modifier = Modifier.size(45.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))

            LazyColumn(modifier = Modifier.height(520.dp).fillMaxWidth(), content = {

                items(ultimosTickets.count()) { index ->

                    TicketBusqueda(navController, ultimosTickets[index])

                }
            })
        }

    }

}

@Composable
fun TicketBusqueda(navController: NavController, ticket: Ticket)
{

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(Color.Transparent)
            .padding(1.dp, 5.dp, 1.dp, 5.dp)
            .clickable {

                //Se envia el ticket en formato JSON, y cuando se cambie de pantalla, se convierte en objeto Ticket otra vez
                val jsonState = Json.encodeToString(ticket)
                navController.navigate("ticketDesplegadoBusqueda" + "/${jsonState}")


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
            Text(text = ticket.descripcion, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "${ticket.fecha} - ${ticket.hora}", fontSize = 12.sp)
        }

        HorizontalDivider()

    }
    Spacer(modifier = Modifier.padding(5.dp))

}

@Composable
fun TicketDesplegadoBusqueda(navController: NavController, ticket: Ticket)
{
    ContenidoTicketDesplegado(navController, ticket)
    {
        
    }
}

@Preview(showBackground = true)
@Composable
fun BusquedaTecnicoPreview() {

    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {

        BusquedaTecnico(navController)

    }
}
