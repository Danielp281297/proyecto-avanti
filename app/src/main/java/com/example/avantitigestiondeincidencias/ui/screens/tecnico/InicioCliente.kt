package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Request.ApiServices
import com.example.avantitigestiondeincidencias.Request.Retrofit
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InicioCliente()
{

    var idUsuario = remember{
        mutableStateOf(1)
    }
    // Se crea la variable de estado con la lista
    // Se comprueba si la lista esta vacia, de ser asi, se crea un aviso en el centro
    var ticketsCliente = remember{
        mutableStateListOf<Ticket>()
    }

    var mostrarFormularioNuevoTicket = remember{
        mutableStateOf(false)
    }

    var state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded,
        //skipHalfExpanded = true
        skipHalfExpanded = true
    )

    val scope = rememberCoroutineScope()

    ticketsContent(idUsuario = 1, { ticket -> ticketsCliente.add(ticket) })

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                title = {
                    Text("Inicio - Cliente Interno", modifier = Modifier.fillMaxWidth().padding(0.dp), textAlign = TextAlign.Center)
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

                LazyColumn(modifier = Modifier.fillMaxHeight().fillMaxWidth(), content = {

                    items(ticketsCliente.count()) { index ->

                        TicketsCliente(ticketsCliente[index]!!)

                    }
                })

            }

            FloatingActionButton(onClick = {

                mostrarFormularioNuevoTicket.value = true

            },
                                shape = RectangleShape,
                                containerColor = Color.Black,
                                modifier = Modifier.align(Alignment.BottomEnd).padding(0.dp, 50.dp)) {

                Text(" + ", color = Color.White)

            }

        }

        // Bottom Sheet
        if(mostrarFormularioNuevoTicket.value)
        {

            AlertDialog(
                modifier = Modifier.wrapContentHeight(),
                onDismissRequest = { mostrarFormularioNuevoTicket.value = false },
                content = {
                    nuevoTicketFormulario({

                        mostrarFormularioNuevoTicket.value = false

                        ticketsCliente.clear()

                        ticketsContent(idUsuario = 1) { ticket ->
                            ticketsCliente.add(ticket)
                        }

                    })
                }

            )

        }

    }

}

fun ticketsContent(idUsuario: Int, function: (Ticket) -> Unit): MutableList<Ticket?> {

    var tickets = mutableListOf<Ticket?>()


    Retrofit.seleccionarTickets("http://192.168.0.104/Daniel/IncidenciasAvanti/PHP/seleccionar_tickets.php/" ,
        { retrofit ->

            val service = retrofit!!.create(ApiServices::class.java).getTickets()

            service.enqueue(object : Callback<List<Ticket>> {
                override fun onResponse(
                    p0: Call<List<Ticket>?>,
                    response: Response<List<Ticket>?>
                ) {
                    if (response.isSuccessful) {

                        val apiResponse = response.body()

                        apiResponse?.forEachIndexed { index, ticket ->

                            // Se comprueba el estado y la prioridad de los tickets
                            function(ticket)


                        }


                    }
                }

                override fun onFailure(
                    p0: Call<List<Ticket>?>,
                    p1: Throwable
                ) {
                    Log.e("Error: ", p1.message.toString())
                }

            })

        })

    return tickets

}

@Composable
fun TicketsCliente(ticket: Ticket)
{

    Box(modifier = Modifier.fillMaxWidth())
    {
        Column(modifier = Modifier.fillMaxWidth().padding(1.dp, 5.dp, 1.dp, 5.dp).border(1.dp, Color.Black))
        {

            Text(text = " Ticket: ${ticket.id}") // Numero de ticket
            Text(text = " Fecha: ${ticket.fecha}")
            Text(text = " Hora: ${ticket.hora}")
            Text(text = " Tipo de ticket: ${ticket.tipo}")
            Text(text = " Descripción: ${ticket.descripcion}")
            Text(text = " Estado: ${ticket.estado}")
            Text(text = " Técnico Encargado: ${ticket.nombreTecnico}")

        }
    }

}

@Preview(showBackground = true)
@Composable
fun CrearUsuarioPreview() {
    AVANTITIGestionDeIncidenciasTheme {

        InicioCliente()

    }
}

