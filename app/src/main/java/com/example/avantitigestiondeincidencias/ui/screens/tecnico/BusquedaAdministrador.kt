package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaAdministrador(navController: NavController)
{

    val scope = rememberCoroutineScope()

    var dataset = remember {
        mutableStateListOf<Ticket>()
    }

    var entradaBusquedaState = remember {
        mutableStateOf("")
    }

    var ticketstate = remember{
        mutableStateOf(false)
    }

    /*
    var pantallaCargaState = remember{
        mutableStateOf(false)
    }
    */

    // Corrutina para obtener los tickets
    LaunchedEffect(Unit)
    {

        withContext(Dispatchers.IO) {
            //pantallaCargaState.value = true
            TicketRequests().mostrarTablaTicket { tickets ->

                dataset.addAll(tickets)

            }
            //pantallaCargaState.value = false

        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.Transparent
                ),
                title = {
                    Text("Busqueda", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            )
        },
        //Color de fondo
        containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
    )
    {

        Column(modifier = Modifier.padding(25.dp))
        {
            Spacer(modifier = Modifier.padding(45.dp))
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = entradaBusquedaState.value,
                    onValueChange = {
                        entradaBusquedaState.value = it
                    },
                    label = {
                        Text(" Descripción a buscar...")
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.padding(5.dp))
                IconButton(modifier = Modifier, onClick = {})
                {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Boton Busqueda", modifier = Modifier.size(45.dp))
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.padding(5.dp))
            Column(modifier = Modifier.fillMaxSize())
            {

                LazyColumn(modifier = Modifier.fillMaxWidth(),
                    content = {

                        items(dataset.count()) { index ->

                            //Si el ticket no es abierto, se muestra en los ultimos tickets

                            ultimosTicketsLazyColumnContent(dataset[index], navController)

                        }

                    })
            }
        }

    }


}


@Preview(showBackground = true)
@Composable
fun BusquedaAdministradorPreview() {

    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {

        BusquedaAdministrador(navController)

    }
}
