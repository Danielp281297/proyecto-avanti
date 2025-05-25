package com.example.avantitigestiondeincidencias.ui.screens.administrador

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ViewModel.BusquedaUsuarioViewModel
import com.example.avantitigestiondeincidencias.ui.screens.componentes.LoadingShimmerEffectScreen
import com.example.avantitigestiondeincidencias.ui.screens.componentes.OutlinedTextFieldPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.screens.componentes.TicketLoading
import com.example.avantitigestiondeincidencias.ui.screens.ticket.fuenteLetraTicketDesplegado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaUsuarios(
    navController: NavController,
    context: Context,
    containerColor: Color = Color.Transparent,
    viewModel: BusquedaUsuarioViewModel = viewModel(),
    borderColorBottonText: Color = if(!isSystemInDarkTheme()) Color.Black else Color.LightGray
)
{
    val entradaBusqueda = viewModel.entradaBusqueda.collectAsState()
    val listaTecnicos = viewModel.listaTecnicos.collectAsState()
    val listaClientesInternos = viewModel.listaClientesInterno.collectAsState()
    val filtroBusqueda = viewModel.filtroBusqueda.collectAsState()
    val botonTecnicoState = viewModel.botonTecnico.collectAsState()
    val botonClienteInternoState = viewModel.botonClienteInterno.collectAsState()

    var tituloState = remember {
        mutableStateOf("")
    }

    var entradaTextoState = remember{
        mutableStateOf(false)
    }

    var buscarUsuarioState = remember{
        mutableStateOf(false)
    }

    var busquedaInicialState = remember{
        mutableStateOf(true)
    }

    val filtroList = remember{
        mutableStateListOf("Nombre de Usuario", "Nombre del empleado", "Número de cédula")
    }

    // Datos iniciales
    SeleccionarUsuariosTecnicos(viewModel) { busquedaInicialState.value = false }
    SeleccionarUsuariosClientesInternos(viewModel) { busquedaInicialState.value = false }

    ScaffoldSimplePersonalizado(
        tituloPantalla = "Usuarios",
        containerColor = containerColor
    )
    {

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(25.dp))
        {

            Column(modifier = Modifier)
            {
                Spacer(modifier = Modifier.padding(45.dp))
                Text("Ingrese el tipo de usuario para realizar la busqueda",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier.weight(1F),
                        value = entradaBusqueda.value,
                        onValueChange = {
                            viewModel.setEntradaBusqueda(it)
                        },
                        label = {
                            Text(if(botonTecnicoState.value  || botonClienteInternoState.value )
                                when(filtroBusqueda.value){
                                    0 -> "Usuario"
                                    1 -> "Primer nombre"
                                    2 -> "Cédula"
                                    else -> ""
                                } + " a buscar..."
                                else
                                " "
                            )},
                        readOnly = !entradaTextoState.value,
                        imeActionNext = false,
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
                Text(text =if(!viewModel.validarBuscador()) "Indique el tipo de usuario para usar el buscador" else "",
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp)
                Spinner(
                    modifier = Modifier.fillMaxWidth(),
                    itemList = filtroList,
                    onItemSelected = {
                        viewModel.setFiltroBusqueda(filtroList.indexOf(it) )
                    }
                )

                Spacer(modifier = Modifier.padding(5.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
                {
                    Button(
                        modifier = Modifier.border(width = 1.dp, color = borderColorBottonText, shape = RectangleShape),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RectangleShape,
                        onClick = {
                            viewModel.setBotonTecnico()
                        })
                    {
                        Text("Técnico", color = borderColorBottonText, fontFamily = montserratFamily)
                    }

                    Button(
                        modifier = Modifier.border(width = 1.dp, color = borderColorBottonText, shape = RectangleShape),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RectangleShape,
                        onClick = {
                            viewModel.setBotonClienteInterno()
                        })
                    {
                        Text("Cliente", color = borderColorBottonText, fontFamily = montserratFamily)
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

                if (botonClienteInternoState.value) {

                    tituloState.value = "CLIENTES INTERNOS"

                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F))
                    {

                        if(busquedaInicialState.value)
                        {
                            items(10)
                            {

                                Column(modifier = Modifier.fillMaxSize()) {
                                    LoadingShimmerEffectScreen(
                                        true,
                                        {
                                            TicketLoading()
                                        },
                                        {}
                                    )
                                }
                            }
                        }
                        else {
                            items(listaClientesInternos.value.count()) { index ->
                                TargetaUsuarioClienteInterno(listaClientesInternos.value[index], navController)
                            }
                        }

                    }


                }else if(botonTecnicoState.value){

                    tituloState.value = "TÉCNICOS"

                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F))
                    {

                        if(busquedaInicialState.value)
                        {
                            items(10)
                            {

                                Column(modifier = Modifier.fillMaxSize()) {
                                    LoadingShimmerEffectScreen(
                                        true,
                                        {
                                            TicketLoading()
                                        },
                                        {}
                                    )
                                }
                            }
                        }
                        else {
                            items(listaTecnicos.value.count()) { index ->
                                TargetaUsuarioTecnico(listaTecnicos.value[index], navController)
                            }
                        }

                    }

                }
                Spacer(modifier = Modifier.padding(50.dp))
            }

            ExtendedFloatingActionButton(onClick = {

                navController.navigate("crearUsuario")
                
            },
                text = {
                    Icon(
                        painter = painterResource(R.drawable.nuevo_usuario_logo),
                        contentDescription = "Crear Nuevo Usuario",
                        modifier = Modifier.size(25.dp)
                    )
                },
                contentColor = Color.White,
                backgroundColor = containerColor,
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(15.dp, 140.dp)
                    .align(Alignment.BottomEnd)
                    .border(1.dp, Color.LightGray, RectangleShape),
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            )

        }

    }

    // Si uno de los dos botones son presionados, se habilita la entrada de texto...
    if(botonTecnicoState.value || botonClienteInternoState.value)
    {
        entradaTextoState.value = true
    }

    if (buscarUsuarioState.value)
    {
        busquedaInicialState.value = true

        // Tecnicos
        if(botonTecnicoState.value)
        {
            //usuariosTecnicoLista.clear()
            if(entradaBusqueda.value.isNotEmpty()) {
                //Se buscan los usuarios por la entrada y por el tipo de usuario tecnico
                BuscarTecnicoUsuarios(viewModel, context)
                {
                    busquedaInicialState.value = false
                    buscarUsuarioState.value = false
                }
            }
            else
            {
                SeleccionarUsuariosTecnicos(viewModel){

                    busquedaInicialState.value = false
                    buscarUsuarioState.value = false
                }
            }


        }
        // Clientes Internos
        else if (botonClienteInternoState.value)
        {

            if(entradaBusqueda.value.isNotEmpty()) {

                BuscarClienteInternoUsuario(context, viewModel){

                    busquedaInicialState.value = false
                    buscarUsuarioState.value = false
                }

            }else{
                SeleccionarUsuariosClientesInternos(viewModel) {
                    busquedaInicialState.value = false
                    buscarUsuarioState.value = false
                }
            }
        }

    }

}

@Composable
fun SeleccionarUsuariosTecnicos(viewModel: BusquedaUsuarioViewModel, resultados: () -> Unit)
{
    LaunchedEffect(Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            viewModel.obtenerDatosTecnico()
            resultados()

        }

    }
}

@Composable
fun SeleccionarUsuariosClientesInternos(viewModel: BusquedaUsuarioViewModel, resultados: () -> Unit)
{
    LaunchedEffect(Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            viewModel.obtenerDatosClienteInterno()
            resultados()

        }

    }
}

@Composable
fun BuscarClienteInternoUsuario(context: Context, viewModel: BusquedaUsuarioViewModel, resultados: () -> Unit)
{
    val filtro = viewModel.filtroBusqueda.collectAsState()
    when (filtro.value)
    {
        // Nombre de usuario
        0 -> BuscarUsuariosClienteInternoPorNombre(viewModel) {
            resultados()
        }
        // Primer nombre del empleado
        1 -> BuscarUsuarioClienteInternoPorNombreEmpleado(viewModel) {
            resultados()
        }
        // Número de cédula
        2 -> BuscarUsuarioClienteInternoPorCedulaEmpleado(context, viewModel){
            resultados()
        }

    }
}

@Composable
fun BuscarTecnicoUsuarios(viewModel: BusquedaUsuarioViewModel, context: Context, resultados: () -> Unit)
{
    val filtro = viewModel.filtroBusqueda.collectAsState()
    when (filtro.value)
    {
        // Nombre de usuario
        0 -> BuscarUsuariosTecnicosPorNombre(viewModel) {
                resultados()
            }

        // Primer nombre del empleado
        1 -> BuscarUsuarioTecnicoPorNombreEmpleado(viewModel){
            resultados()
        }
        // Número de cédula
        2 -> BuscarUsuarioTecnicoPorCedulaEmpleado(context, viewModel) {
            resultados()
        }


    }
}

@Composable
fun BuscarUsuarioClienteInternoPorCedulaEmpleado(context: Context, viewModel: BusquedaUsuarioViewModel, resultados: () -> Unit){

    // Si es un dato conformado por numeros, de hace la consulta
    val entrada = viewModel.entradaBusqueda.collectAsState()
    if (entrada.value.isDigitsOnly())
    {
        LaunchedEffect(Unit) {
            CoroutineScope(Dispatchers.IO).launch{

                viewModel.seleccionarUsuarioClienteInternoPorCedula()
                resultados()

            }
        }
    }
    else
    // En caso contrario se muestran los usuarios con mensaje de error
    {
        Toast.makeText(context, "Dato inválido, intente de nuevo", Toast.LENGTH_SHORT).show()
        SeleccionarUsuariosClientesInternos(viewModel){
            resultados()
        }
    }

}

@Composable
fun BuscarUsuarioTecnicoPorCedulaEmpleado(context: Context, viewModel: BusquedaUsuarioViewModel, resultados: () -> Unit){

    // Si es un dato conformado por numeros, de hace la consulta
    val entrada = viewModel.entradaBusqueda.collectAsState()
    if (entrada.value.isDigitsOnly())
    {
        LaunchedEffect(Unit) {
            CoroutineScope(Dispatchers.IO).launch{

                viewModel.seleccionarUsuarioTecnicoPorCedula()
                resultados()

            }
        }
    }
    else
    // En caso contrario se muestran los usuarios con mensaje de error
    {
        Toast.makeText(context, "Dato inválido, intente de nuevo", Toast.LENGTH_SHORT).show()
        SeleccionarUsuariosTecnicos(viewModel){
            resultados()
        }

    }

}

@Composable
fun BuscarUsuarioClienteInternoPorNombreEmpleado(viewModel: BusquedaUsuarioViewModel, resultados: () -> Unit)
{
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch{

           viewModel.seleccionarUsuarioClienteInternoPorPrimerNombreEmpleado()
            resultados()
        }
    }
}

@Composable
fun BuscarUsuarioTecnicoPorNombreEmpleado(viewModel: BusquedaUsuarioViewModel, resultados: () -> Unit)
{
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch{

            viewModel.seleccionarUsuarioTecnicoPorPrimerNombre()
            resultados()

        }
    }
}

@Composable
fun BuscarUsuariosClienteInternoPorNombre(viewModel: BusquedaUsuarioViewModel, resultados: () -> Unit)
{
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            viewModel.seleccionarUsuarioClienteInternoPorNombreUsuario()
                resultados()

        }
    }
}

@Composable
fun BuscarUsuariosTecnicosPorNombre(viewModel: BusquedaUsuarioViewModel, resultados: () -> Unit)
{

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            viewModel.seleccionarUsuarioTecnicoPorNombre()
            resultados()

        }
    }
}

@Composable
fun TargetaUsuarioTecnico(
    tecnico: Tecnico,
    navController: NavController
) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable {

            val json = Json.encodeToString(tecnico)
            navController.navigate("informacionPerfilTecnico" + "/${json}")

        })
    {

        Column(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth())
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
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable {

            val json = Json.encodeToString(clienteInterno)
            navController.navigate("informacionPerfilCliente" + "/${json}")

        })
    {

        Column(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth())
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

        //BusquedaUsuarios(navController)

    }
}