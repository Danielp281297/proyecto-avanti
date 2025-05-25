package com.example.avantitigestiondeincidencias.ui.screens.cliente

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.OutlinedTextFieldPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ViewModel.NuevoTicketFormularioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun nuevoTicketFormulario(
    idClienteInterno: Int,
    containerColor: Color = Color.Transparent,
    viewModel: NuevoTicketFormularioViewModel = viewModel(),
    lambda: () -> Unit)
{

    val context = LocalContext.current

    val tipoEvento = listOf("Incidencia", "Solicitud", "Mantenimiento", "Control de cambio")

    val descripcion = viewModel.descripcion.collectAsState()

    var insertarTicketState = remember{
        mutableStateOf(false)
    }

    Box(modifier = Modifier.wrapContentHeight().padding(0.dp).background(containerColor), contentAlignment = Alignment.Center)
    {
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(15.dp))
            {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
                {
                    Icon(
                        painter = painterResource(R.drawable.nuevo_ticket_icon),
                        contentDescription = "",
                        modifier = Modifier.size(50.dp)
                    )
                }

                Text(text = "Nuevo Ticket", modifier = Modifier.padding(0.dp, 10.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp)

                HorizontalDivider()

                Spacer(modifier = espacioSpacer)
                Text("Ingrese los campos correspondientes:",
                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                Spacer(modifier = espacioSpacer)

                Column(modifier = Modifier)
                {

                    Column(modifier = Modifier)
                    {
                        Text("Tipo de ticket")
                        Spinner(modifier = Modifier.fillMaxWidth(),
                            itemList = tipoEvento,
                            posicionInicial = 0,
                            onItemSelected = { option ->
                                viewModel.getIdTipoTicket(tipoEvento.indexOf(option) + 1)
                            })
                    }

                    Spacer(modifier = espacioSpacer)

                }

                Spacer(modifier = espacioSpacer)
                Text(text = " Descripción:")
                OutlinedTextFieldPersonalizado(
                    modifier = Modifier.fillMaxWidth(),
                    value = descripcion.value,
                    onValueChange = {newText ->
                        // Si el texto es menor a 50 caracteres, se almacena en newText
                        if (newText.length <= 100)
                            viewModel.getDescripcion(newText)
                    },
                    label = { Text("Indique aquí", fontSize = 13.sp) },
                    supportingText = true,
                    imeActionNext = false,
                    maximoCaracteres = 100,
                )

                Spacer(modifier = espacioSpacer)
                BotonPersonalizado(onClick = {
                    insertarTicketState.value = true
                })
                {
                    Text(text = "ABRIR TICKET", color = Color.White, fontFamily = montserratFamily)
                }

        }

        Spacer(modifier = espacioSpacer)

    }

    // Si se oprime el boton ABRIR TICKET, se guarda la informacion de la base de datos
    if (insertarTicketState.value) {
        
        LaunchedEffect(Unit) { 
            if(viewModel.validarContenidoTicket(context))
            {
                CoroutineScope(Dispatchers.IO).launch{

                    viewModel.abrirTicket(idClienteInterno)
                    lambda()
                }
            }
            insertarTicketState.value = false
        }

    }

}

@Preview(showBackground = true)
@Composable
fun nuevoTicketFormularioPreview() {
    AVANTITIGestionDeIncidenciasTheme {

        nuevoTicketFormulario(0){}

    }
}
