package com.example.avantitigestiondeincidencias.ui.screens.usuario

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ViewModel.CambiarDatosUsuarioViewModel
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonCargaPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.OutlinedTextFieldPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.screens.PantallaCarga
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CambiarDatosUsuario(navController: NavController,
                        context: Context,
                        empleado: Empleado,
                        containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919),
                        viewModel: CambiarDatosUsuarioViewModel = viewModel()
)
{

    val image = R.drawable.editar_usuario_icon

    val mensajeError = viewModel.mensajeError.collectAsState()

    val idTipoUsuario = viewModel.idTipoUsuario.collectAsState()
    val nacionalidad = viewModel.nacionalidad.collectAsState()
    val cedula = viewModel.cedula.collectAsState()
    val primerNombre = viewModel.primerNombre.collectAsState()
    val segundoNombre = viewModel.segundoNombre.collectAsState()
    val primerApellido = viewModel.primerApellido.collectAsState()
    val segundoApellido = viewModel.segundoApellido.collectAsState()
    val correoElectronico = viewModel.correoElectronico.collectAsState()
    val idCodigoOperadoraTelefono = viewModel.idCodigoOperadoraTelefono.collectAsState()
    val extensionTelefono = viewModel.extensionTelefono.collectAsState()
    val idDepartamento = viewModel.idDepartamento.collectAsState()
    val nombreSede = viewModel.nombreSede.collectAsState()
    val idCargo = viewModel.idCargo.collectAsState()
    val idGrupoAtencion = viewModel.idGrupoAtencion.collectAsState()
    val nombreUsuario = viewModel.nombreUsuario.collectAsState()

    val listaOperadoraTelefono = viewModel.listaCodigoOperadoraTelefono.collectAsState()
    val listaCargos = viewModel.listaCargos.collectAsState()
    val listaDepartamentos = viewModel.listaDepartamentos.collectAsState()
    val listaSedes = viewModel.listaSede.collectAsState()
    val listaGrupoAtencion = viewModel.listaGrupoAtencion.collectAsState()

    val nacionalidadList = remember {
        mutableListOf('V', 'E')
    }

    var actualizarUsuarioState = remember{
        mutableStateOf(false)
    }

    val verticalScrollState = rememberScrollState()

    val validaciones = remember{
        mutableStateOf(false)
    }

    var cargandoContenidoSpinners = remember{
        mutableStateOf(true)
    }

    val scope = rememberCoroutineScope()

    Network.networkCallback(navController, context)

    // Se obtienen los datos para los Spinners
    LaunchedEffect(Unit) {

        // Esto se pone aqui, porque suele repetir los datos después de presionar el boton:
        viewModel.setIdTipoUsuario(empleado.usuario.idTipoUsuario)
        viewModel.setNacionalidad(empleado.nacionalidad)
        viewModel.setCedula(empleado.cedula.toString())
        viewModel.setPrimerNombre(empleado.primerNombre)
        viewModel.setSegundoNombre(empleado.segundoNombre)
        viewModel.setPrimerApellido(empleado.primerApellido)
        viewModel.setSegundoApellido(empleado.segundoApellido)
        viewModel.setCorreoElectronico(empleado.correoElectronico)
        //viewModel.setIdCodigoOperadoraTelefono(empleado.telefonoEmpleado.idCodigoOperadoraTelefono)
        viewModel.setExtensionTelefono(empleado.telefonoEmpleado.extension)
        viewModel.setNombreUsuario(empleado.usuario.nombre)

        withContext(Dispatchers.IO)
        {


            viewModel.obtenerCodigoOperadoraTelefono()
            viewModel.setIdCodigoOperadoraTelefono(empleado.telefonoEmpleado.idCodigoOperadoraTelefono)

            viewModel.obtenerCargos()
            viewModel.setIdCargo(empleado.idCargoEmpleado)

            viewModel.obtenerDepartamentos()
            viewModel.setIdDepartamento(empleado.idDepartamento)
            // Grupo de atencion
            if(empleado.usuario.idTipoUsuario == 1){

                viewModel.obtenerGrupoAtencion()

                // Se obtienen del grupo de atención
                viewModel.obtenerIdGrupoAtencion(empleado.id)

            }

            cargandoContenidoSpinners.value = false

        }

    }

    // Pantalla de carga
    if(cargandoContenidoSpinners.value)
    {
        PantallaCarga()
    }
    else
        ScaffoldSimplePersonalizado(
            tituloPantalla = "Editar Perfil",
            containerColor = containerColor
        ){

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .verticalScroll(state = verticalScrollState, enabled = true))
        {

            Spacer(modifier = Modifier.padding(50.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
            {
                Icon(
                    painter = painterResource(image),
                    contentDescription = "",
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                "Ingrese los datos correspondientes",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                //textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                "Datos personales",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Row(modifier = Modifier)
            {

                Column(modifier = Modifier.weight(1F)) {

                    Text("Nacionalidad", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.padding(2.dp))
                    Spinner(
                        modifier = Modifier,
                        itemList = nacionalidadList.map { it.toString() },
                        posicionInicial = nacionalidadList.indexOf(nacionalidad.value),
                        onItemSelected = {
                            viewModel.setNacionalidad(it[0])
                        }
                    )

                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier.weight(1F))
                {
                    Text("Cédula", fontWeight = FontWeight.Bold)
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = cedula.value,
                        onValueChange = { number ->

                            if (number.length <= 8)
                                viewModel.setCedula(number)

                        },
                        label = { Text("Número de cédula", fontSize = 13.sp) },
                        number = true
                    )

                }

            }
            // Nombre y apellido
            Text("Nombre completo", fontWeight = FontWeight.Bold)
            Row()
            {

                Column(modifier = Modifier.weight(1F))
                {
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier.fillMaxWidth(),
                        value = primerNombre.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { it.isLetter() } && newText.length <= 20)
                                viewModel.setPrimerNombre(newText)
                        },
                        label = { Text("Primer nombre", fontSize = 13.sp) },
                        supportingText = true,
                        maximoCaracteres = 20,
                        minimoCaracteres = 3,
                    )

                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier.weight(1F))
                {
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier.fillMaxWidth(),
                        value = segundoNombre.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { it.isLetter() } && newText.length <= 20)
                                viewModel.setSegundoNombre(newText)
                        },
                        label = { Text("Segundo nombre", fontSize = 13.sp) },
                        supportingText = true,
                        maximoCaracteres = 20,
                        minimoCaracteres = 3
                    )

                }

            }

            Row()
            {

                Column(modifier = Modifier.weight(1F))
                {
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier.fillMaxWidth(),
                        value = primerApellido.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { it.isLetter() } && newText.length <= 20)
                                viewModel.setPrimerApellido(newText)
                        },
                        label = { Text("Primer apellido", fontSize = 13.sp) },
                        supportingText = true,
                        maximoCaracteres = 20,
                        minimoCaracteres = 3,
                    )

                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier.weight(1F))
                {

                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier.fillMaxWidth(),
                        value = segundoApellido.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { it.isLetter() } && newText.length <= 20)
                                viewModel.setSegundoApellido(newText)
                        },
                        label = { Text("Segundo Apellido", fontSize = 13.sp) },
                        supportingText = true,
                        maximoCaracteres = 20,
                        minimoCaracteres = 3,
                    )

                }

            }

            Text("Correo electrónico", fontWeight = FontWeight.Bold)
            Row() {

                Column(modifier = Modifier.weight(1F))
                {

                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier.fillMaxWidth(),
                        value = correoElectronico.value,
                        onValueChange = { newText ->
                            // Si el texto no contiene espacios y es menor a 50 caracteres, se almacena en newText
                            if (newText.all { !it.isWhitespace() } && newText.length <= 255)
                                viewModel.setCorreoElectronico(newText)
                        },
                        label = { Text("Correo electrónico", fontSize = 13.sp) },
                        supportingText = true,
                        email = true,
                        maximoCaracteres = 255,
                        minimoCaracteres = 3,
                    )

                }
            }

            Text("Número de teléfono", fontWeight = FontWeight.Bold)
            Row()
            {


                Column(modifier = Modifier.weight(1F))
                {
                    Text("Extensión", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.padding(2.dp))
                    Spinner(
                        modifier = Modifier,
                        posicionInicial = idCodigoOperadoraTelefono.value,
                        itemList = listaOperadoraTelefono.value
                    ) {
                        viewModel.setIdCodigoOperadoraTelefono(listaOperadoraTelefono.value.indexOf(it))
                    }

                }

                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier.weight(1F))
                {
                    Text("Número", fontWeight = FontWeight.Bold)
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = extensionTelefono.value,
                        onValueChange = { number ->

                            // Cuando se deja la entrada en blanco, colapsa el programa. Por eso puse esta excepcion...
                            if (number.all { it.isDigit() && !it.isWhitespace() } && number.length <= 7) {
                                    viewModel.setExtensionTelefono(number)
                            }

                        },
                        label = { Text("Número", fontSize = 13.sp) },
                        number = true,
                    )

                }

            }

            Text(
                "Datos laborales",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            if (empleado.usuario.idTipoUsuario == 2) {
                Row()
                {
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Departamento", fontWeight = FontWeight.Bold)
                        Spinner(
                            modifier = Modifier,
                            itemList = listaDepartamentos.value.map{ it.nombre },
                            posicionInicial = empleado.idDepartamento,
                            onItemSelected = {
                                    viewModel.setIdDepartamento(listaDepartamentos.value.map { it.nombre }.indexOf(it))
                                    viewModel.setNombreSede(listaDepartamentos.value[idDepartamento.value].sede.nombre)
                            }
                        )

                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Sede", fontWeight = FontWeight.Bold)
                        OutlinedTextFieldPersonalizado(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = nombreSede.value,
                            readOnly = true,
                            singleLine = false,
                            onValueChange = {

                            },
                            label = { Text("Sede", fontSize = 13.sp) }
                        )

                    }
                }
            }
            Row()
            {
                Column(modifier = Modifier.weight(1F))
                {
                    Text("Cargo", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.padding(2.dp))
                    Spinner(
                        modifier = Modifier.fillMaxWidth(),
                        posicionInicial = empleado.idCargoEmpleado,
                        itemList = listaCargos.value
                    ) {
                        viewModel.setIdCargo(listaCargos.value.indexOf(it))
                    }

                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
            if (empleado.usuario.idTipoUsuario == 1) {
                Row()
                {
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Grupo de atención", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.padding(2.dp))
                        Spinner(
                            modifier = Modifier.fillMaxWidth(),
                            posicionInicial = idGrupoAtencion.value,
                            itemList = listaGrupoAtencion.value
                        ) {
                            viewModel.setIdGrupoAtencion(listaGrupoAtencion.value.indexOf(it))

                        }
                        Spacer(modifier = Modifier.padding(5.dp))

                    }
                }
            }

            Text(
                "Datos de usuario",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Row()
            {
                Column(modifier = Modifier.weight(1F))
                {
                    Text("Nombre de usuario", fontWeight = FontWeight.Bold)
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier.fillMaxWidth(),
                        value = nombreUsuario.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { !it.isWhitespace() && it.isLetter() || it.isDigit() } && newText.length <= 20)
                                viewModel.setNombreUsuario(newText)
                        },
                        label = { Text("Nombre de usuario", fontSize = 13.sp) },
                        imeActionNext = false,
                        supportingText = true,
                        maximoCaracteres = 20,
                        minimoCaracteres = 3,
                    )

                }
            }

            Spacer(modifier = espacioSpacer)

            BotonCargaPersonalizado(
                onClick = {

                    //Primero, se verifica que el nombre de usuario, la cedula y el correo electronico no este repetidos en la tablactualizarUsuarioState.value = true
                    viewModel.setMensajeErrorEditarPerfil()
                    validaciones.value = true

                },
                enabled = !actualizarUsuarioState.value,
                isLoading = actualizarUsuarioState.value
            ) {
                Text(text = "ACTUALIZAR DATOS", color = Color.White, fontFamily = montserratFamily)
            }
            Spacer(modifier = Modifier.padding(40.dp))

        }
    }

    if (idDepartamento.value > 0)
    {
        viewModel.setNombreSede(listaDepartamentos.value[idDepartamento.value].sede.nombre)
    }

    // Se validan las entradas para crear un nuevo usuario
    if (validaciones.value)
    {

        actualizarUsuarioState.value = true

        if(mensajeError.value.isBlank())
        {

            //LaunchedEffect(Unit) {
            CoroutineScope(Dispatchers.IO).launch {

                viewModel.validarDatosUsuarioActualizar(
                    idEmpleado = empleado.id,
                    idTelefonoEmpleado = empleado.idTeléfonoEmpleado,
                    idUsuario = empleado.idUsuario
                ) { avisoRepetido ->

                    scope.launch {
                        if (avisoRepetido.isBlank()) {
                            viewModel.actualizarDatos(empleado)
                            Toast.makeText(context, "Datos editados con éxito", Toast.LENGTH_SHORT).show()
                            Log.d("RESULTADO", "DATOS DE USUARIO EDITADO")
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, avisoRepetido, Toast.LENGTH_SHORT).show()
                        }

                        actualizarUsuarioState.value = false

                    }
                }

            }

        }
        else {
            Toast.makeText(context, mensajeError.value, Toast.LENGTH_SHORT).show()
            actualizarUsuarioState.value = false
        }

        validaciones.value = false

    }

}

@Preview(showBackground = true)
@Composable
fun CambiarDatosUsuarioPreview() {

    val navController = rememberNavController()
    val context = LocalContext.current
    AVANTITIGestionDeIncidenciasTheme {

        CambiarDatosUsuario(navController, context, Empleado())

    }
}
