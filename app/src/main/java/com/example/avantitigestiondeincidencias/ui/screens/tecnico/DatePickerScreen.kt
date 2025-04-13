package com.example.avantitigestiondeincidencias.ui.screens.tecnico


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerScreen()
{

    var showDatePickerState = remember {
        mutableStateOf(false)
    }
    var fechaState = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {

        Text(text = fechaState.value)
        Button(onClick = {
            showDatePickerState.value = true
        }) {
            Text("DATE PICKER DIALOG")
        }

        Column (modifier = Modifier.clickable {
            showDatePickerState.value = true
        }, verticalArrangement = Arrangement.Bottom)
        {
            Text(text = fechaState.value, modifier = Modifier.fillMaxWidth().height(50.dp), textAlign = TextAlign.Center)
            HorizontalDivider(thickness = 2.dp)
        }

    }

    if (showDatePickerState.value == true) {
        Log.d("FECHA", "ABIERTA")
        DatePicker(showDatePickerState.value, {
            showDatePickerState.value = false
        }, {
            fechaState.value = it
            showDatePickerState.value = false
        })
    }
    
}

fun convertirMilisegundosAFecha(milisegundos: Long): String {
    val formatter = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
    val fecha = Date(milisegundos)
    return formatter.format(fecha)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(showDialog: Boolean, ondismiss: () -> Unit, fecha: (String) -> Unit)
{
    var showDatePickerState = remember {
        mutableStateOf(showDialog)
    }

    var fechaState = remember {
        mutableStateOf("")
    }

    var mostrarSeleccionarButton = remember {
        mutableStateOf(false)
    }

    var datePickerState = rememberDatePickerState()



    if (showDatePickerState.value == true)
    {

        DatePickerDialog(
            onDismissRequest = {
                ondismiss()
            },
            dismissButton = {
                Button(onClick = {
                    ondismiss()
                }) {
                    Text("CANCELAR")
                }
            },
            confirmButton = {
                Button( enabled = mostrarSeleccionarButton.value,
                    onClick = {
                        val milisegundos = datePickerState.selectedDateMillis
                        milisegundos?.let {
                            val instante = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                            fechaState.value = convertirMilisegundosAFecha(milisegundos)//"${instante.dayOfMonth}/${instante.monthValue}/${instante.year}"
                            fecha(fechaState.value)
                        }
                        showDatePickerState.value = false
                    }) {
                    Text("SELECCIONAR")
                }
            })
        {

            DatePicker(state = datePickerState)

            if (datePickerState.selectedDateMillis != null)
            {

                mostrarSeleccionarButton.value = true

            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerScreenPreview() {

    AVANTITIGestionDeIncidenciasTheme {

        DatePickerScreen()

    }

}
