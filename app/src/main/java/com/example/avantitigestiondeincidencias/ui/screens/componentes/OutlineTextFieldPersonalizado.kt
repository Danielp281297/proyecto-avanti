package com.example.avantitigestiondeincidencias.ui.screens.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.R

@Composable
fun OutlinedTextFieldPersonalizado(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    readOnly: Boolean = false,
    empezarMayusculas: Boolean = true,
    singleLine: Boolean = true,
    supportingText: Boolean = false,
    imeActionNext: Boolean = true,
    password: Boolean = false,
    number: Boolean = false,
    email: Boolean = false,
    minimoCaracteres: Int = 0,
    maximoCaracteres: Int = 0,
)
{

    val focusRequester = remember{
        FocusRequester()
    }

    val contrasenaVisibleState = remember{
        mutableStateOf(if (password) false else true)
    }

    OutlinedTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = value,
        onValueChange = {

            onValueChange(it)
        },
        label = { label() },
        singleLine = singleLine,
        readOnly = readOnly,
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = Color.Black,
            focusedBorderColor = Color.Black
        ),
        trailingIcon = {
            if (password)
            {

                val image = if (contrasenaVisibleState.value == true)
                {
                    R.drawable.visible_icono
                }else
                    R.drawable.no_visible_icono

                IconButton(onClick = {

                    if (contrasenaVisibleState.value)
                    {
                        contrasenaVisibleState.value = false
                    }else
                        contrasenaVisibleState.value = true

                }) {
                    Icon(painter = painterResource(image), contentDescription = "", modifier = Modifier.size(25.dp))
                }

            }
        },
        supportingText = {
            if (supportingText) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.padding(0.dp))

                    Text(
                        text = "${value.length}/$maximoCaracteres",
                        color = if (value.length < (maximoCaracteres * 0.75).toInt() && value.length >= minimoCaracteres) Color.LightGray else Color.Red,
                        modifier = Modifier
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions().copy(
            capitalization = if (empezarMayusculas) KeyboardCapitalization.Sentences else KeyboardCapitalization.None,   // Se muestra el teclado al mayuscula, y en minusculas despues del primer caracter
            keyboardType = if (password) KeyboardType.Password
                                else if (number) KeyboardType.NumberPassword
                                else if (email) KeyboardType.Email
                                else KeyboardType.Text,
            imeAction = if (imeActionNext) ImeAction.Next else ImeAction.Done),
        visualTransformation = if(contrasenaVisibleState.value) VisualTransformation.None else PasswordVisualTransformation()

    )

}