package com.example.avantitigestiondeincidencias.ui.screens.usuario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CrearUsuarioFormulario()
{

    var cedula = remember{
        mutableStateOf("")
    }

    var tipoUsuario = remember {
        mutableStateOf(-1)
    }



    var sedeState = rememberSaveable{
        mutableStateOf(" ")
    }

    val seleccionar = rememberSaveable { mutableStateOf("Seleccionar") }
    val tipoUsuarioLista = listOf("Técnico", "Cliente interno")


    Scaffold(containerColor = Color.White, topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(Color.White),
            title = {
                Text("Crear Usuario", modifier = Modifier.fillMaxWidth().padding(0.dp), textAlign = TextAlign.Center)
            }
        )
    })
    {

        Column(modifier = Modifier.fillMaxSize().padding(25.dp)
            .verticalScroll(rememberScrollState())) {

            Spacer(modifier = Modifier.padding(45.dp))

            Text("Por favor, ingrese los campos correspondientes: ",
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            Spacer(modifier = espacioSpacer)

            Text("Tipo de usuario: ")
            Row(modifier = Modifier.fillMaxWidth().padding(0.dp).height(50.dp), horizontalArrangement = Arrangement.SpaceEvenly)
            {

                Spinner(
                    modifier = Modifier.weight(1f),
                    itemList = tipoUsuarioLista,
                    onItemSelected = { item ->


                    })

                Spacer(modifier = espacioSpacer)

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = cedula.value,
                    onValueChange = { },
                    label = { Text("Cédula") },
                    placeholder = {  }
                )

            }

            Spacer(modifier = Modifier.padding(15.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(0.dp).height(50.dp), horizontalArrangement = Arrangement.SpaceEvenly)
            {

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = cedula.value,
                    onValueChange = { },
                    label = { Text("Primer Nombre", fontSize = 13.sp) },
                    placeholder = {  }
                )

                Spacer(modifier = espacioSpacer)

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = cedula.value,
                    onValueChange = { },
                    label = { Text("Segundo Nombre", fontSize = 13.sp) },
                    placeholder = {  }
                )

            }

            Spacer(modifier = Modifier.padding(15.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(0.dp).height(50.dp), horizontalArrangement = Arrangement.SpaceEvenly)
            {

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = cedula.value,
                    onValueChange = { },
                    label = { Text("Primer Apellido", fontSize = 13.sp) },
                    placeholder = {  }
                )

                Spacer(modifier = espacioSpacer)

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = cedula.value,
                    onValueChange = { },
                    label = { Text("Segundo Apellido", fontSize = 13.sp) },
                    placeholder = {  }
                )

            }

            Spacer(modifier = Modifier.padding(15.dp))

            Text(text = "Datos del empleado",
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            Spacer(modifier = espacioSpacer)

            Text(text = "Departamento")

            Spacer(modifier = espacioSpacer)

            Spinner(
                modifier = Modifier.fillMaxWidth(),
                itemList = tipoUsuarioLista,
                onItemSelected = { item ->


                })

            Spacer(modifier = espacioSpacer)

            Row(modifier = Modifier.fillMaxWidth()) {
                
                Column(Modifier.weight(1f))
                {
                    Text(text = "Cargo")
                    Spinner(
                        modifier = Modifier.fillMaxWidth(),
                        itemList = tipoUsuarioLista,
                        onItemSelected = { item ->


                        })

                }

                Spacer(modifier = espacioSpacer)

                Column(Modifier.weight(1f))
                {
                    Text(text = "Sede")
                    TextField(
                        value = sedeState.value,
                        onValueChange = { },
                        label = { }
                    )
                }

            }

            Spacer(modifier = espacioSpacer)

            Text("Datos de Usuario: ",
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            Spacer(modifier = espacioSpacer)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                value = cedula.value,
                onValueChange = { },
                label = { Text("Nombre de usuario") },
                placeholder = {  }
            )

            Spacer(modifier = Modifier.padding(15.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                value = cedula.value,
                onValueChange = { },
                label = { Text("Contraseña") },
                placeholder = {  }
            )

            Spacer(modifier = Modifier.padding(15.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                value = cedula.value,
                onValueChange = { },
                label = { Text("Confirmar Contraseña") },
                placeholder = {  }
            )

            Spacer(modifier = Modifier.padding(15.dp))

            Button(modifier = modeloButton,

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RectangleShape,
                onClick = { }
            )
            {
                Text(text = "CREAR USUARIO", color = Color.White)
            }

            Spacer(modifier = Modifier.padding(15.dp))

        }

    }

}

@Preview(showBackground = true)
@Composable
fun CrearUsuarioPreview() {
    AVANTITIGestionDeIncidenciasTheme {

        CrearUsuarioFormulario()

    }
}
