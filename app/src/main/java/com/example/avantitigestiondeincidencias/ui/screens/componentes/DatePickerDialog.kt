package com.example.avantitigestiondeincidencias.ui.screens.componentes

import android.app.DatePickerDialog
import android.graphics.drawable.Drawable
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DatePickerDialog(modifier: Modifier, valueChannge: (String) -> Unit) {

    var anyo: Int = 0
    var mes: Int = 0
    var dias: Int = 0

    val calendar = Calendar.getInstance()
    anyo = calendar.get(Calendar.YEAR)
    mes = calendar.get(Calendar.MONTH)
    dias = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val context = LocalContext.current

    val fecha = remember{
        mutableStateOf<String>("")
    }

    val datePickerDialog = DatePickerDialog(context,
        {_: DatePicker, anyo: Int, mes: Int, diasMes: Int ->

            fecha.value = "$diasMes-${mes+1}-$anyo"

        }, anyo, mes, dias

    )

    Box(modifier = modifier
        .height(50.dp).fillMaxWidth()
        .clickable {
            datePickerDialog.apply {

            }
            datePickerDialog.show()

        }){

        Column(modifier = Modifier.fillMaxWidth().align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = fecha.value)
            HorizontalDivider()
        }

    }

    valueChannge(fecha.value)

}