package com.example.avantitigestiondeincidencias.ui.screens.componentes

import android.util.Log
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun Spinner(
    modifier: Modifier,
    itemList: List<String>,
    onItemSelected: (selectedItem: String) -> Unit
)
{

    var selectedItem = remember{
        mutableStateOf("Seleccione")
    }

    var expandido = remember{
        mutableStateOf(false)
    }

    OutlinedButton(modifier = modifier,
        onClick = { expandido.value = true })
    {
        androidx.compose.material3.Text(
            text = selectedItem.value,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
    }
    androidx.compose.material.DropdownMenu(
        expanded = expandido.value,
        onDismissRequest = { expandido.value = false }
    ) {

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
