package com.example.avantitigestiondeincidencias.ui.screens.componentes

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Spinner(
    modifier: Modifier,
    itemList: List<String>,
    onItemSelected: (selectedItem: String) -> Unit
)
{

    var selectedItem = remember{
        mutableStateOf(itemList[0])
    }

    var expandido = remember{
        mutableStateOf(false)
    }


    ExposedDropdownMenuBox(
        expanded = expandido.value,
        onExpandedChange = {
            expandido.value = true
        }
    )
    {
        TextField(
            readOnly = true,
            value = selectedItem.value,
            onValueChange = { },
            label = {},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,          // Color de fondo
                focusedIndicatorColor = Color.Black,     // Color del marco cuando se pulsa el spinner
                trailingIconColor = Color.Black
            ),
            trailingIcon = {

                TrailingIcon(
                    expanded = expandido.value,

                )
            }
        )
        ExposedDropdownMenu(
            expanded = expandido.value,
            onDismissRequest = {
                expandido.value = false
            }
        )
        {
            itemList.forEach { option ->
                DropdownMenuItem(onClick = {
                    selectedItem.value = option
                    expandido.value = false
                })
                {

                    Text(text = option)

                    onItemSelected(selectedItem.value)
                }

            }
        }
    }

}
