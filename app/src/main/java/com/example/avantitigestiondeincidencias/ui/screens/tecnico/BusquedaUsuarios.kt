package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaUsuarios(navController: NavController)
{

    var tituloState = remember {
        mutableStateOf("")
    }

    var entradaBusquedaState = remember {
        mutableStateOf("")
    }

    var entradaTextoState = remember{
        mutableStateOf(false)
    }

    var botonTecnicoState = remember{
        mutableStateOf(false)
    }

    var botonClienteInternoState = remember{
        mutableStateOf(false)
    }

    var usuariosTecnicoLista = remember {
        mutableStateListOf<Tecnico>()
    }

    var usuariosClientesInternos = remember{
        mutableStateListOf<ClienteInterno>()
    }

    var buscarUsuarioState = remember{
        mutableStateOf(false)
    }

    /*
    LaunchedEffect(Unit)
    {
        withContext(Dispatchers.IO)
        {



            UsuarioRequest().seleccionarUsuariosTecnico{ tecnico ->
                usuariosTecnicoLista.addAll(tecnico)
            }
        }
    }
    */

    buscarTecnicos(entradaBusquedaState.value)
    {
        usuariosTecnicoLista.addAll(it)
    }

    buscarClientesInternos(entradaBusquedaState.value) {
        usuariosClientesInternos.addAll(it)
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

        Box(modifier = Modifier.fillMaxSize().padding(25.dp))
        {

            Column(modifier = Modifier)
            {
                Spacer(modifier = Modifier.padding(45.dp))
                Text("Ingrese el tipo de usuario para realizar la busqueda",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    //textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
                Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1F),
                        readOnly = !entradaTextoState.value,
                        value = entradaBusquedaState.value,
                        onValueChange = {
                            entradaBusquedaState.value = it
                        },
                        label = {
                            Text(" Usuario a buscar...")
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedLabelColor = Color.Black,
                            focusedBorderColor = Color.Black
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    IconButton(modifier = Modifier.size(45.dp),
                        enabled = entradaTextoState.value,
                        onClick = {

                        // Se busca los tickets por sus descripciones
                        buscarUsuarioState.value = true

                    })
                    {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Boton Busqueda", modifier = Modifier.size(45.dp))
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
                    tituloState.value,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(5.dp))

                if (botonClienteInternoState.value == true) {

                    tituloState.value = "CLIENTES INTERNOS"

                    LazyColumn(modifier = Modifier.fillMaxWidth().height(420.dp))
                    {

                        items(usuariosClientesInternos.count()) { index ->
                            TargetaUsuarioClienteInterno(usuariosClientesInternos[index], navController)
                        }

                    }


                }else if(botonTecnicoState.value){

                    tituloState.value = "TÉCNICOS"

                    LazyColumn(modifier = Modifier.fillMaxWidth().height(420.dp))
                    {

                        items(usuariosTecnicoLista.count()) { index ->
                            TargetaUsuarioTecnico(usuariosTecnicoLista[index], navController)
                        }

                    }

                }
            }

            ExtendedFloatingActionButton(onClick = {

                navController.navigate("crearUsuario")
                
            },
                text = {
                    Icon(
                        painter = painterResource(R.drawable.nuevo_usuario_logo),
                        contentDescription = "Crear Nuevo Ticket",
                        modifier = Modifier.size(25.dp)
                    )
                },
                contentColor = Color.White,
                backgroundColor = Color.White,
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.wrapContentWidth().padding(15.dp, 140.dp).align(Alignment.BottomEnd).border(1.dp, Color.LightGray, RectangleShape),
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            )

        }

    }

    // Si una de los dos botones son presionados, se habilita la entrada de texto...
    if(botonTecnicoState.value || botonClienteInternoState.value)
    {
        entradaTextoState.value = true
    }

    if (buscarUsuarioState.value)
    {
        if(botonTecnicoState.value)
        {

            usuariosTecnicoLista.clear()
            buscarTecnicos(entradaBusquedaState.value) {
                usuariosTecnicoLista.addAll(it)
            }

        }
        else if (botonClienteInternoState.value)
        {
            usuariosClientesInternos.clear()
            buscarClientesInternos(entradaBusquedaState.value) {
                usuariosClientesInternos.addAll(it)
            }
        }

        buscarUsuarioState.value = false
    }

}

@Composable
fun buscarClientesInternos(nombreUsuario: String, clientes: (List<ClienteInterno>) -> Unit)
{
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            if (nombreUsuario.isNotEmpty()) {
                EmpleadoRequest().buscarClienteByNombreUsuario(nombreUsuario) {
                    clientes(it)
                }
            }
            else{
                UsuarioRequest().seleccionarUsuariosClienteInterno {
                    clientes(it)
                }
            }

        }
    }
}

@Composable
fun buscarTecnicos(nombreUsuario: String, tecnicos: (List<Tecnico>) -> Unit)
{
    
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            if (nombreUsuario.isNotEmpty()) {
                EmpleadoRequest().buscarTecnicoByNombreUsuario(nombreUsuario) {
                    tecnicos(it)
                }
            }
            else{
                UsuarioRequest().seleccionarUsuariosTecnico{
                    tecnicos(it)
                }
            }

        }
    }
    
}

@Composable
fun TargetaUsuarioTecnico(
    tecnico: Tecnico,
    navController: NavController
) {

    Column(modifier = Modifier.fillMaxWidth().padding(5.dp).clickable {

        val json = Json.encodeToString(tecnico)
        navController.navigate("informacionPerfilTecnico" + "/${json}")

    })
    {

        Column(modifier = Modifier.padding(5.dp).fillMaxWidth())
        {
            //Text("${tecnico.empleado.departamento.sede.nombre}: ${tecnico.empleado.departamento.piso} - ${tecnico.empleado.departamento.nombre}")
            Text("Grupo de atención: ${tecnico.grupoAtencion.grupoAtencion}")
            Text("${tecnico.empleado.primerNombre} ${tecnico.empleado.segundoNombre} ${tecnico.empleado.primerApellido} ${tecnico.empleado.segundoApellido}",
                fontWeight = FontWeight.Bold)
            Text("Usuario: " + tecnico.empleado.usuario.nombre)
        }

        HorizontalDivider()

    }

}

@Composable
fun EncabezadoInformacionUsuario(icono: Int, titulo: String)
{
    Column()
    {

        Spacer(modifier = Modifier.padding(50.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Icon(
                painter = painterResource(icono),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        androidx.compose.material.Text(
            //"${tecnico.empleado.primerNombre} ${tecnico.empleado.segundoNombre} ${tecnico.empleado.primerApellido} ${tecnico.empleado.segundoApellido}",
            titulo,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))
        HorizontalDivider(modifier = Modifier.height(5.dp))
        Spacer(modifier = Modifier.padding(5.dp))

    }
}





@Composable
fun TargetaUsuarioClienteInterno(clienteInterno: ClienteInterno, navController: NavController)
{
    Column(modifier = Modifier.fillMaxWidth().padding(5.dp).clickable {

        val json = Json.encodeToString(clienteInterno)
        navController.navigate("informacionPerfilCliente" + "/${json}")

    })
    {

        Column(modifier = Modifier.padding(5.dp).fillMaxWidth())
        {
            Text("${clienteInterno.empleado.departamento.sede.nombre}: ${clienteInterno.empleado.departamento.piso} - ${clienteInterno.empleado.departamento.nombre}")
            Text("${clienteInterno.empleado.primerNombre} ${clienteInterno.empleado.segundoNombre} ${clienteInterno.empleado.primerApellido} ${clienteInterno.empleado.segundoApellido}",
                fontWeight = FontWeight.Bold)
            Text("Usuario: " + clienteInterno.empleado.usuario.nombre)
        }

        HorizontalDivider()

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