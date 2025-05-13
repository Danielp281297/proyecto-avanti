package com.example.avantitigestiondeincidencias.ui.screens.tecnico


import android.util.Log
import android.view.textclassifier.TextSelection
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonPersonalizado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
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
        BotonPersonalizado(
            onClick = {
                showDatePickerState.value = true
            }
        ) {
            Text("DATE PICKER DIALOG", fontFamily = montserratFamily)
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
        DatePicker(
            showDialog = showDatePickerState.value,
            ondismiss = { showDatePickerState.value = false },
            containerColor = Color.Transparent,
            fecha = {
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
fun DatePicker(
    showDialog: Boolean,
    containerColor: Color,
    optionTextColor: Color = if (!isSystemInDarkTheme()) Color.White else Color.LightGray,

    ondismiss: () -> Unit,
    fecha: (String) -> Unit)
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

    val color = if (!isSystemInDarkTheme()) Color.Black else Color.LightGray

    if (showDatePickerState.value == true)
    {

        DatePickerDialog(
            onDismissRequest = {
                ondismiss()
            },
            dismissButton = {
                Button(modifier = Modifier.background(Color.Transparent).border(1.dp, color),
                    onClick = {
                    ondismiss()
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                ) {
                    Text("CANCELAR", color = color)
                }
            },
            shape = RectangleShape,
            colors = DatePickerColors(
                containerColor = containerColor,
                titleContentColor = containerColor,
                headlineContentColor = containerColor,
                weekdayContentColor = containerColor,
                subheadContentColor = containerColor,
                navigationContentColor = containerColor,
                yearContentColor = containerColor,
                disabledYearContentColor = containerColor,
                currentYearContentColor = containerColor,
                selectedYearContentColor = containerColor,
                disabledSelectedYearContentColor = containerColor,
                selectedYearContainerColor = containerColor,
                disabledSelectedYearContainerColor = containerColor,
                dayContentColor = containerColor,
                disabledDayContentColor = containerColor,
                selectedDayContentColor = containerColor,
                disabledSelectedDayContentColor = containerColor,
                selectedDayContainerColor = containerColor,
                disabledSelectedDayContainerColor = containerColor,
                todayContentColor =containerColor,
                todayDateBorderColor = containerColor,
                dayInSelectionRangeContainerColor = containerColor,
                dayInSelectionRangeContentColor = containerColor,
                dividerColor = containerColor,


                dateTextFieldColors = TextFieldColors(
                    focusedTextColor = Color.Transparent,
                    unfocusedTextColor = Color.Transparent,
                    disabledTextColor = Color.Transparent,
                    errorTextColor = optionTextColor,
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    errorContainerColor = optionTextColor,
                    cursorColor = optionTextColor,
                    errorCursorColor = optionTextColor,
                    textSelectionColors = TextSelectionColors(
                        handleColor = optionTextColor,
                        backgroundColor = optionTextColor
                    ),
                    focusedIndicatorColor = optionTextColor,
                    unfocusedIndicatorColor = optionTextColor,
                    disabledIndicatorColor =optionTextColor,
                    errorIndicatorColor = optionTextColor,
                    focusedLeadingIconColor = optionTextColor,
                    unfocusedLeadingIconColor = optionTextColor,
                    disabledLeadingIconColor = optionTextColor,
                    errorLeadingIconColor = optionTextColor,
                    focusedTrailingIconColor = optionTextColor,
                    unfocusedTrailingIconColor = optionTextColor,
                    disabledTrailingIconColor = optionTextColor,
                    errorTrailingIconColor = optionTextColor,
                    focusedLabelColor = optionTextColor,
                    unfocusedLabelColor = optionTextColor,
                    disabledLabelColor = optionTextColor,
                    errorLabelColor = optionTextColor,
                    focusedPlaceholderColor = optionTextColor,
                    unfocusedPlaceholderColor = optionTextColor,
                    disabledPlaceholderColor = optionTextColor,
                    errorPlaceholderColor = optionTextColor,
                    focusedSupportingTextColor =optionTextColor,
                    unfocusedSupportingTextColor = optionTextColor,
                    disabledSupportingTextColor = optionTextColor,
                    errorSupportingTextColor = optionTextColor,
                    focusedPrefixColor = optionTextColor,
                    unfocusedPrefixColor = optionTextColor,
                    disabledPrefixColor = optionTextColor,
                    errorPrefixColor = optionTextColor,
                    focusedSuffixColor = optionTextColor,
                    unfocusedSuffixColor = optionTextColor,
                    disabledSuffixColor = optionTextColor,
                    errorSuffixColor = optionTextColor
                )
            ),
            confirmButton = {
                Button(
                    modifier = Modifier.
                                background(if(!mostrarSeleccionarButton.value) Color.LightGray else if (!isSystemInDarkTheme()) Color.Black else Color.Transparent).
                                border(1.dp, if (!isSystemInDarkTheme()) Color.Transparent else Color.LightGray),
                    enabled = mostrarSeleccionarButton.value,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
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
                    Text("SELECCIONAR", color = optionTextColor, fontFamily = montserratFamily)
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
