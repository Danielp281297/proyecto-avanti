package com.example.avantitigestiondeincidencias.ui.screens.componentes

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun AutocompleteTextField(

    initialText: String,
    label: String,
    suggestions: List<String>,
    onClearResults: () -> Unit,
    imeActionNext: Boolean = true,
    modifier: Modifier = Modifier,
    onTextChange: (TextFieldValue) -> Unit,
) {
    var textState = remember { mutableStateOf(TextFieldValue(initialText)) }
    val focusManager = LocalFocusManager.current
    val suggestionsState = remember{ mutableStateOf(false) }
    val filteredSuggestions = remember(textState.value) {
        suggestions.filter { it.contains(textState.value.text, ignoreCase = true) }
    }

    val focusRequester = remember{
        FocusRequester()
    }


    Column(modifier = modifier) {

        TextField(
            modifier = modifier,
            value = textState.value,
            onValueChange = {
                textState.value = it
                onClearResults()
                suggestionsState.value = true
                onTextChange(it)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color.Black,
                focusedBorderColor = Color.Black),
            label = { Text(label) },
            keyboardOptions = KeyboardOptions().copy(
                keyboardType = KeyboardType.Text,
                imeAction = if (imeActionNext) ImeAction.Next else ImeAction.Done),
        )

        if (suggestionsState.value && filteredSuggestions.isNotEmpty() && textState.value.text.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .height(200.dp)
                    .padding(top = 8.dp)
            ) {
                items(filteredSuggestions.size) { suggestion ->
                    Text(
                        text = filteredSuggestions[suggestion],
                        modifier = Modifier.focusRequester(focusRequester)
                            .padding(4.dp)
                            .clickable {
                                textState.value = TextFieldValue(filteredSuggestions[suggestion])
                                suggestionsState.value = false
                                onTextChange(textState.value)
                                focusManager.clearFocus()
                            }
                    )

                }
            }
        }


    }

}