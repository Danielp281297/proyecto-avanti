package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformeTecnico()
{

    val datePicketState = rememberDatePickerState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                title = {
                    Text("Informe", modifier = Modifier.fillMaxWidth().padding(0.dp), textAlign = TextAlign.Center)
                }
            )
        }
    ) {

        Column(modifier = Modifier.padding(25.dp).fillMaxSize())
        {

            Spacer(modifier = Modifier.padding(40.dp))

            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly)
            {

                Text(text = "Ingrese los campos correspondientes para imprimir el informe.")
                Row(modifier = Modifier.fillMaxWidth())
                {
                    Column(modifier = Modifier.fillMaxWidth().weight(1F)) {
                        Text(text = "Fecha inicial")
                        com.example.avantitigestiondeincidencias.ui.screens.componentes.DatePickerDialog(modifier = Modifier) {  }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Column(modifier = Modifier.fillMaxWidth().weight(1F)) {
                        Text(text = "Fecha final")
                        com.example.avantitigestiondeincidencias.ui.screens.componentes.DatePickerDialog(modifier = Modifier) {  }

                    }
                }

                Button(modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                    })
                {
                    Text(text = "CARGAR DATOS")
                }

                LazyColumn(modifier = Modifier.fillMaxWidth().height(400.dp).background(Color.LightGray))
                {  }

                Button(enabled = false,
                    modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                    })
                {
                    Text(text = "GUARDAR INFORME")
                }


            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun showDatePicker(context: Context){

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "${String.format("%02d", dayOfMonth)}-${String.format("%02d", month + 1)}-$year"
        }, year, month, day
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Selected Date: ${date.value}")
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = "Open Date Picker")
        }

        androidx.compose.material3.DatePickerDialog(content = { }, 
                                                    onDismissRequest = { },
                                                    confirmButton = { })
    }

}


@Preview(showBackground = true)
@Composable
fun InformeTecnicoPreview() {

    val context = LocalContext.current

    AVANTITIGestionDeIncidenciasTheme {

        InformeTecnico()

    }
}
