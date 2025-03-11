package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import java.util.Calendar
import java.util.Date

@Composable
fun DatePickerScreen()
{

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun datePickerDialog(function: () -> Unit)
{

    var anyo: Int = 0
    var mes: Int = 0
    var dias: Int = 0

    val calendar = Calendar.getInstance()
    anyo = calendar.get(Calendar.YEAR)
    mes = calendar.get(Calendar.MONTH)
    dias = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val context = LocalContext.current
    var showDialog = remember{
        mutableStateOf(false)
    }

    val fecha = remember{
        mutableStateOf<String>("")
    }



    val datePickerDiaog = DatePickerDialog(context,
        {_: DatePicker, anyo: Int, mes: Int, diasMes: Int ->

            fecha.value = "$diasMes-${mes+1}-$anyo"

        }, anyo, mes, dias

    )

    Button(
        onClick = { datePickerDiaog.show()}
    )
    {
        Text(text = "IMPRIMIR FECHA", modifier = Modifier.align(Alignment.CenterVertically))
    }

    Text(text = fecha.value)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker() {


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
    {



    }

}

@Preview(showBackground = true)
@Composable
fun DatePickerScreenPreview() {

    AVANTITIGestionDeIncidenciasTheme {

        datePickerDialog {  }

    }

}
