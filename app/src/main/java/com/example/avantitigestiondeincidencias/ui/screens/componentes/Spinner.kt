package com.example.avantitigestiondeincidencias.ui.screens.componentes

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
    selectedItem: String,
    onItemSelected: (selectedItem: String) -> Unit
)
{

    var expandido = remember{
        mutableStateOf(false)
    }

    OutlinedButton(modifier = modifier,
        onClick = { expandido.value = true })
    {
        androidx.compose.material3.Text(
            text = selectedItem,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
    }
    androidx.compose.material.DropdownMenu(
        expanded = expandido.value,
        onDismissRequest = { expandido.value = false }
    ) {

        itemList.forEach {
            DropdownMenuItem(onClick = {
                expandido.value = false
            })
            {
                Text(text = it)

                onItemSelected(it)
            }

        }

    }

}
