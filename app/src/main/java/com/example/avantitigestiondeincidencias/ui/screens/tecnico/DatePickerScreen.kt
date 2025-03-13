package com.example.avantitigestiondeincidencias.ui.screens.tecnico


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

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

    var showDatePicker = false
    
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Row {
            com.example.avantitigestiondeincidencias.ui.screens.componentes.DatePickerDialog(modifier = Modifier.padding(15.dp).weight(1F))
            {
                fechaState.value = it
            }
            //Text("dfadsf")
            Text("dfadsf", modifier = Modifier.padding(15.dp).weight(1F))
        }
    }



    if (showDatePickerState.value == true)
    {



    }
    
}



@Preview(showBackground = true)
@Composable
fun DatePickerScreenPreview() {

    AVANTITIGestionDeIncidenciasTheme {

        DatePickerScreen()

    }

}
