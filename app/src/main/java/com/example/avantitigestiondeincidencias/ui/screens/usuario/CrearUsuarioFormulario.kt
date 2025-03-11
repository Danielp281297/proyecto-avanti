package com.example.avantitigestiondeincidencias.ui.screens.usuario

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CrearUsuarioFormulario()
{

    val tipoUsuarioLista = listOf("Técnico", "Cliente interno")
    val codigoOperadoraList = listOf("0412", "0426", "0416", "0424", "0414")
    val departamentoState = listOf("Centro de comunicaciones", "Contabilidad", "Audiovisual", "E-commerce", "Centro de Atención al cliente", "Tecnología", "Logística y Almacén", "Recursos Humanos", "Auditoría")
    val cargoState = listOf("Administrador", "Agente", "Analista", "Coordinador", "Gerente", "Supervisor", "Pasante")

    var primerNombreState = remember{
        mutableStateOf("")
    }

    var segundoNombreState = remember{
        mutableStateOf("")
    }

    var sedeState = remember{
        mutableStateOf("")
    }

    var nombreUsuarioState = remember{
        mutableStateOf("")
    }

    var contrasenaState = remember{
        mutableStateOf("")
    }

    var confirmarContrasenaState = remember{
        mutableStateOf("")
    }

    var alertDialogState = remember{
        mutableStateOf(false)
    }
    /*

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
            Row(modifier = Modifier.fillMaxWidth().padding(0.dp).height(50.dp))
            {

                Spinner(
                    modifier = Modifier.height(50.dp).weight(1F),
                    itemList = tipoUsuarioLista,
                    onItemSelected = { item ->


                    })

            }

            Spacer(modifier = Modifier.padding(15.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(0.dp).height(60.dp), horizontalArrangement = Arrangement.SpaceEvenly)
            {

                OutlinedTextField(
                    modifier = Modifier.weight(1F).fillMaxHeight(),
                    value = cedulaState.value,
                    onValueChange = { cedulaState.value = it },
                    label = { Text("Cédula", fontSize = 13.sp) },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone
                                                    , imeAction = ImeAction.Next),
                    placeholder = {  }
                )

                Spacer(modifier = espacioSpacer)

                OutlinedTextField(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    value = primerNombreState.value,
                    onValueChange = { primerNombreState.value = it },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    label = { Text("Primer Nombre", fontSize = 13.sp) },
                    placeholder = {  }
                )

            }

            Spacer(modifier = Modifier.padding(15.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(0.dp).height(60.dp), horizontalArrangement = Arrangement.SpaceEvenly)
            {

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = segundoNombreState.value ,
                    onValueChange = { segundoNombreState.value = it },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    label = { Text("Segundo Nombre", fontSize = 13.sp) },
                    placeholder = {  }
                )

                Spacer(modifier = espacioSpacer)

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = usuarioState.value.persona.primerApellido,
                    onValueChange = { usuarioState.value.persona.primerApellido = it },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    label = { Text("Primer Apellido", fontSize = 13.sp) },
                    placeholder = {  }
                )

            }

            Spacer(modifier = Modifier.padding(15.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(0.dp).height(60.dp), horizontalArrangement = Arrangement.SpaceEvenly)
            {

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = usuarioState.value.persona.segundoApellido,
                    onValueChange = { usuarioState.value.persona.segundoApellido = it },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    label = { Text("Segundo Apellido", fontSize = 13.sp) },
                    placeholder = {  }
                )

                Spacer(modifier = espacioSpacer)

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = usuarioState.value.persona.fechaNacimiento,
                    onValueChange = { usuarioState.value.persona.fechaNacimiento = it },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    label = { Text("Fecha de nacimiento", fontSize = 11.sp, modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start) },
                    placeholder = {  }
                )

            }

            Spacer(modifier = Modifier.padding(15.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(0.dp).height(60.dp), horizontalArrangement = Arrangement.SpaceEvenly)
            {

                Spinner(
                    modifier = Modifier.height(50.dp).weight(1F),
                    itemList = codigoOperadoraList,
                    onItemSelected = { item ->

                        usuarioState.value.persona.telefono.codigoOperadora = item

                    })

                Spacer(modifier = espacioSpacer)

                OutlinedTextField(
                    modifier = Modifier.weight(1F),
                    value = usuarioState.value.persona.telefono.extension,
                    onValueChange = { usuarioState.value.persona.telefono.extension = it },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    label = { Text("Extension", fontSize = 13.sp) },
                    placeholder = {  }
                )

            }

            Spacer(modifier = espacioSpacer)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = usuarioState.value.persona.direccion,
                onValueChange = { usuarioState.value.persona.direccion = it },
                keyboardOptions =
                    KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                label = { Text("Direccion", fontSize = 13.sp) },
                placeholder = {  }
            )

            Spacer(modifier = espacioSpacer)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = usuarioState.value.persona.correoElectronico,
                onValueChange = { usuarioState.value.persona.correoElectronico = it },
                keyboardOptions =
                    KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                label = { Text("Correo Electronico", fontSize = 13.sp) },
                placeholder = {  }
            )

            Spacer(modifier = Modifier.padding(15.dp))

            Text(text = "Datos del cargo",
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            Spacer(modifier = espacioSpacer)

            Text(text = "Departamento")

            Spacer(modifier = espacioSpacer)

            Spinner(
                modifier = Modifier.fillMaxWidth(),
                itemList = departamentoState,
                onItemSelected = { item ->

                    if(item == "Centro de comunicaciones" || item == "Contabilidad" || item == "Audiovisual" || item == "E-commerce" || item ==  "Centro de Atención al cliente" || item == "Tecnología")
                    {
                        sedeState.value = "Torre FC"
                    }
                    else if (item == "Logística y Almacén")
                    {
                        sedeState.value = "Galerias Avanti"
                    }
                    else if (item == "Recursos Humanos" || item == "Auditoría")
                        sedeState.value = "Edificio Caroni"

                })

            Spacer(modifier = espacioSpacer)

            Row(modifier = Modifier.fillMaxWidth()) {
                
                Column(Modifier.fillMaxHeight().weight(1.2f))
                {
                    Text(text = "Cargo")
                    Spinner(
                        modifier = Modifier.fillMaxWidth(),
                        itemList = cargoState,
                        onItemSelected = { item ->
                            


                        })

                }

                Spacer(modifier = espacioSpacer)

                Column(Modifier.fillMaxHeight().weight(1f))
                {
                    Text(text = "Sede")
                    TextField(
                        value = sedeState.value,
                        readOnly = true,
                        onValueChange = {  },
                        label = { }
                    )
                }

            }

            Spacer(modifier = espacioSpacer)

            Text("Datos de Usuario: ",
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            Spacer(modifier = espacioSpacer)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(60.dp),
                value = nombreUsuarioState.value,
                onValueChange = { nombreUsuarioState.value = it },
                keyboardOptions =
                    KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                label = { Text("Nombre de usuario") },
                placeholder = {  }
            )

            Spacer(modifier = Modifier.padding(15.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(60.dp),
                value = contrasenaState.value,
                onValueChange = { contrasenaState.value = it },
                keyboardOptions =
                    KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                label = { Text("Contraseña") },
                placeholder = {  }
            )

            Spacer(modifier = Modifier.padding(15.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(60.dp),
                value = confirmarContrasenaState.value,
                onValueChange = { confirmarContrasenaState.value = it },
                keyboardOptions =
                    KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                label = { Text("Confirmar Contraseña") },
                placeholder = {  }
            )

            Spacer(modifier = Modifier.padding(15.dp))

            Button(modifier = modeloButton,

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RectangleShape,
                onClick = {

                    alertDialogState.value = true

                }
            )
            {
                Text(text = "CREAR USUARIO", color = Color.White)
            }

            Spacer(modifier = Modifier.padding(15.dp))

        }


        // Si el estado es verdadero, aparece un alertDialog mostrando el JSON del objeto Usuario
        if (alertDialogState.value)
        {

            AlertDialog(onDismissRequest = { alertDialogState.value = false },
                content = {



                })

        }

    }
    */

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CrearUsuarioPreview() {
    AVANTITIGestionDeIncidenciasTheme {

        CrearUsuarioFormulario()

    }
}
