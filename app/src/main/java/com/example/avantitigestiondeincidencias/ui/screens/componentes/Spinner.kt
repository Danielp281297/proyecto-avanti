package com.example.avantitigestiondeincidencias.ui.screens.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlin.math.exp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Spinner(
    modifier: Modifier,
    itemList: List<String>,
    posicionInicial: Int = 0,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919),
    optionTextColor: Color = if (!isSystemInDarkTheme()) Color.Black else Color.LightGray,
    onItemSelected: (selectedItem: String) -> Unit
)
{

    var selectedItem = remember{
        mutableStateOf(itemList[posicionInicial])
    }

    var expandido = remember{
        mutableStateOf(false)
    }


    ExposedDropdownMenuBox(
        expanded = expandido.value,
        onExpandedChange = {
            expandido.value = !expandido.value  // depende del valor del expandido, se muestra o se ocultan las opciones
        },
        modifier = modifier
    )
    {
        TextField(
            modifier = modifier,
            readOnly = true,
            value = selectedItem.value,
            onValueChange = { },
            label = {},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = containerColor,          // Color de fondo
                focusedIndicatorColor = optionTextColor,     // Color del marco cuando se pulsa el spinner
                trailingIconColor = optionTextColor,
                textColor = optionTextColor
            ),
            trailingIcon = {

                TrailingIcon(
                    expanded = expandido.value
                )
            }
        )
        ExposedDropdownMenu(
            modifier = Modifier.background(containerColor),
            expanded = expandido.value,
            onDismissRequest = {
                expandido.value = false
            }
        )
        {
            itemList.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                    selectedItem.value = option
                    expandido.value = false
                })
                {

                    Text(text = option, color = optionTextColor, fontSize = 15.sp, fontFamily = montserratFamily)

                        onItemSelected(selectedItem.value)



                }

            }
        }
    }

}
