package com.example.avantitigestiondeincidencias.ui.screens.tecnico


import android.util.Log
import android.view.textclassifier.TextSelection
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.avantitigestiondeincidencias.modeloButton
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
                Button(modifier = Modifier.border(0.dp, Color.Transparent, RectangleShape),
                    onClick = {
                    ondismiss()
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                ) {
                    Text("CANCELAR")
                }
            },
            shape = RectangleShape,
            colors = DatePickerColors(
                containerColor = Color.White,
                titleContentColor = Color.White,
                headlineContentColor = Color.White,
                weekdayContentColor = Color.White,
                subheadContentColor = Color.White,
                navigationContentColor = Color.White,
                yearContentColor = Color.White,
                disabledYearContentColor = Color.White,
                currentYearContentColor = Color.White,
                selectedYearContentColor = Color.White,
                disabledSelectedYearContentColor = Color.White,
                selectedYearContainerColor = Color.White,
                disabledSelectedYearContainerColor = Color.White,
                dayContentColor = Color.White,
                disabledDayContentColor = Color.White,
                selectedDayContentColor = Color.White,
                disabledSelectedDayContentColor = Color.White,
                selectedDayContainerColor = Color.White,
                disabledSelectedDayContainerColor = Color.White,
                todayContentColor =Color.White,
                todayDateBorderColor = Color.White,
                dayInSelectionRangeContainerColor = Color.White,
                dayInSelectionRangeContentColor = Color.White,
                dividerColor = Color.White,
                dateTextFieldColors = TextFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    errorTextColor = Color.Black,
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    disabledContainerColor = Color.Black,
                    errorContainerColor = Color.Black,
                    cursorColor = Color.Black,
                    errorCursorColor = Color.Black,
                    textSelectionColors = TextSelectionColors(
                        handleColor = Color.Black,
                        backgroundColor = Color.Black
                    ),
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                    disabledIndicatorColor =Color.Black,
                    errorIndicatorColor = Color.Black,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    disabledLeadingIconColor = Color.Black,
                    errorLeadingIconColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                    disabledTrailingIconColor = Color.Black,
                    errorTrailingIconColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    disabledLabelColor = Color.Black,
                    errorLabelColor = Color.Black,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Black,
                    disabledPlaceholderColor = Color.Black,
                    errorPlaceholderColor = Color.Black,
                    focusedSupportingTextColor =Color.Black,
                    unfocusedSupportingTextColor = Color.Black,
                    disabledSupportingTextColor = Color.Black,
                    errorSupportingTextColor = Color.Black,
                    focusedPrefixColor = Color.Black,
                    unfocusedPrefixColor = Color.Black,
                    disabledPrefixColor = Color.Black,
                    errorPrefixColor = Color.Black,
                    focusedSuffixColor = Color.Black,
                    unfocusedSuffixColor = Color.Black,
                    disabledSuffixColor = Color.Black,
                    errorSuffixColor = Color.Black
                )
            ),
            confirmButton = {
                Button(modifier = Modifier.border(0.dp, Color.Transparent, RectangleShape),
                    enabled = mostrarSeleccionarButton.value,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    onClick = {
                        val milisegundos = datePickerState.selectedDateMillis
                        milisegundos?.let {
                            val instante = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                            fechaState.value = "${if (instante.dayOfMonth < 10) "0" else ""}${instante.dayOfMonth}-${if (instante.monthValue < 10) "0" else ""}${instante.monthValue}-${instante.year}"
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
