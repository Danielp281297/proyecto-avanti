package com.example.avantitigestiondeincidencias.ui.screens.componentes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.ui.screens.usuario.iconoCarga

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun BotonCargaPersonalizado(onClick: () -> Unit,
                       isLoading: Boolean,
                        enabled: Boolean = true,
                       CuerpoBoton: @Composable () -> Unit)
{
    val modeloButton = Modifier.fillMaxWidth().height(50.dp)

    Button(modifier = modeloButton.border(1.dp, if (!isSystemInDarkTheme()) Color.Transparent else Color.LightGray),

        colors = ButtonDefaults.buttonColors(
            containerColor = if (!isSystemInDarkTheme()) Color.Black else Color.Transparent
        ),
        shape = RectangleShape,
        enabled = enabled,
        onClick = {

            onClick()

        }
    )
    {

        if (isLoading)
        {
            iconoCarga(Modifier)

        }else
        {
            CuerpoBoton()
        }

    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun BotonPersonalizado(onClick: () -> Unit,
                       enabled: Boolean = true,
                            CuerpoBoton: @Composable () -> Unit)
{
    val modeloButton = Modifier.fillMaxWidth().height(50.dp)

    Button(modifier = modeloButton.border(1.dp, if (!isSystemInDarkTheme()) Color.Transparent else Color.LightGray),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (!isSystemInDarkTheme()) Color.Black else Color.Transparent
        ),
        shape = RectangleShape,
        onClick = {

            onClick()

        }
    )
    {

        CuerpoBoton()

    }
}
