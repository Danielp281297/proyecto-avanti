package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.R
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

data class AdministradorOpciones(val label: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaUsuarios(navController: NavController)
{

    var entradaBusquedaState = remember {
        mutableStateOf("")
    }

    var botonTecnicoState = remember{
        mutableStateOf(false)
    }

    var botonClienteInternoState = remember{
        mutableStateOf(false)
    }

    var usuariosTecnicoLista = remember {
        mutableListOf<Tecnico>()
    }

    var usuariosClientesInternos = remember{
        mutableListOf<ClienteInterno>()
    }

    LaunchedEffect(Unit)
    {
        withContext(Dispatchers.IO)
        {
            //usuariosClientesInternos.clear()
            UsuarioRequest().seleccionarUsuariosClienteInterno { clientes ->
                usuariosClientesInternos.addAll(clientes)
            }

            UsuarioRequest().seleccionarUsuariosTecnico{ tecnico ->
                usuariosTecnicoLista.addAll(tecnico)
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.White
                ),
                title = {
                    Text("Usuarios", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            )
        },
        //Color de fondo
        containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
    )
    {

        //Spacer(modifier = Modifier.padding(35.dp))

        Box(modifier = Modifier.fillMaxSize().padding(15.dp))
        {

            Column(modifier = Modifier)
            {
                Spacer(modifier = Modifier.padding(45.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = entradaBusquedaState.value,
                        onValueChange = {
                            entradaBusquedaState.value = it
                        },
                        label = {
                            Text(" Nombre de usuario a buscar...")
                        },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    IconButton(modifier = Modifier, onClick = {})
                    {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Boton Busqueda",
                            modifier = Modifier.size(45.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
                {
                    Button(
                        modifier = Modifier.border(width = 1.dp, color = Color.Black, shape = RectangleShape),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RectangleShape,
                        onClick = {
                            botonTecnicoState.value = true
                            botonClienteInternoState.value = false
                            usuariosTecnicoLista.forEach {
                                Log.d("TECNICO ACTIVADO", it.toString())
                            }
                        })
                    {
                        Text("Técnico", color = Color.Black)
                    }

                    Button(
                        modifier = Modifier.border(width = 1.dp, color = Color.Black, shape = RectangleShape),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RectangleShape,
                        onClick = {
                            botonClienteInternoState.value = true
                            botonTecnicoState.value = false
                            usuariosClientesInternos.forEach {
                                //Log.d("CLINTE INTERNO", it.toString())
                            }

                        })
                    {
                        Text("Cliente", color = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    "Clientes Internos",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(5.dp))

                if (botonClienteInternoState.value == true) {

                    LazyColumn(modifier = Modifier.fillMaxWidth().background(Color.Green).height(420.dp))
                    {

                        items(usuariosClientesInternos.count()) { index ->
                            TargetaUsuarioClienteInterno(usuariosClientesInternos[index], navController)
                        }

                    }


                }else if(botonTecnicoState.value){

                    LazyColumn(modifier = Modifier.fillMaxWidth().background(Color.LightGray).height(420.dp))
                    {

                        items(usuariosTecnicoLista.count()) { index ->
                            TargetaUsuario(usuariosTecnicoLista[index], navController)
                        }

                    }

                }
            }

            FloatingActionButton(onClick = {

            },
                shape = RectangleShape,
                containerColor = Color.Black,
                modifier = Modifier.padding(0.dp, 50.dp).align(Alignment.BottomEnd)) {

                Text(" + ", color = Color.White)

            }

        }

    }

}

@Composable
fun TargetaUsuario(
    tecnico: Tecnico,
    controller: NavController
) {

    Column(modifier = Modifier.fillMaxWidth().padding(5.dp).
    border(width = 1.dp, color = Color.Black, shape = RectangleShape))
    {

        Column(modifier = Modifier.padding(5.dp).fillMaxWidth())
        {
            Text("${tecnico.empleado.departamento.sede.nombre}: ${tecnico.empleado.departamento.piso} - ${tecnico.empleado.departamento.nombre}")
            Text("${tecnico.empleado.primerNombre} ${tecnico.empleado.segundoNombre} ${tecnico.empleado.primerApellido} ${tecnico.empleado.segundoApellido}",
                fontWeight = FontWeight.Bold)
            Text("Usuario: " + tecnico.empleado.usuario.nombre)
        }


    }

}

@Composable
fun TargetaUsuarioClienteInterno(clienteInterno: ClienteInterno, navController: NavController)
{
    Column(modifier = Modifier.fillMaxWidth().padding(5.dp).
                            border(width = 1.dp, color = Color.Black, shape = RectangleShape))
    {

        Column(modifier = Modifier.padding(5.dp).fillMaxWidth())
        {
            Text("${clienteInterno.empleado.departamento.sede.nombre}: ${clienteInterno.empleado.departamento.piso} - ${clienteInterno.empleado.departamento.nombre}")
            Text("${clienteInterno.empleado.primerNombre} ${clienteInterno.empleado.segundoNombre} ${clienteInterno.empleado.primerApellido} ${clienteInterno.empleado.segundoApellido}",
                fontWeight = FontWeight.Bold)
            Text("Usuario: " + clienteInterno.empleado.usuario.nombre)
        }
        

    }
}

@Preview(showBackground = true)
@Composable
fun CuentaAdministraPreview() {

    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {

        BusquedaUsuarios(navController)

    }
}