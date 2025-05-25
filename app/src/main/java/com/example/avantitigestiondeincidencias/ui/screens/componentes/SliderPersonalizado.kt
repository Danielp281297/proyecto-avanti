package com.example.avantitigestiondeincidencias.ui.screens.componentes

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SliderPersonalizado(
    value: Float,
    onValueChange: (Float) -> Unit,
    enable: Boolean = true,
    punteros: Int,
    rango:  ClosedFloatingPointRange<Float>,
    pointerColor: Color =  if (isSystemInDarkTheme()) Color.LightGray else Color.Black

)
{
    Slider(value = value
        , onValueChange = { onValueChange(it) },
        enabled = enable,
        steps = punteros,
        valueRange = rango,
        modifier = Modifier.padding(start = 9.dp, end = 9.dp),
        colors = SliderColors(
            thumbColor = pointerColor,
            activeTrackColor = pointerColor,
            activeTickColor = pointerColor,
            inactiveTrackColor = Color.Transparent,
            inactiveTickColor = Color.Black,
            disabledThumbColor = Color.LightGray,
            disabledActiveTrackColor = Color.LightGray,
            disabledActiveTickColor = Color.LightGray,
            disabledInactiveTrackColor = Color.LightGray,
            disabledInactiveTickColor = Color.LightGray
        )

    )
}
