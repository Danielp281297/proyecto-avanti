package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.CargoEmpleado
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Departamento
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.GrupoAtencion
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.TelefonoEmpleado
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.Supabase.TelefonoRequest
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearUsuario(navController: NavController)
{

    val limiteCedula = 30000000

    val image = R.drawable.nuevo_usuario_logo

    val tipoUsuarioList = remember {
        mutableListOf<String>("-- Seleccione", "Técnico", "Cliente Interno")
    }

    var codigoExtensionTelefonoList = remember{
        mutableListOf<String>("-- Seleccione")
    }

    var cargoEmpleadoList = remember {
        mutableListOf<String>("-- Seleccione")
    }

    var grupoAtencionList = remember {
        mutableListOf<String>("-- Seleccione")
    }

    var departamentosList = remember{
        mutableListOf<Departamento>(Departamento())
    }

    var nombreDepartamentoList = remember{
        mutableListOf<String>("-- Seleccione")
    }

    var idTipoUsuarioState = remember {
        mutableStateOf(0)
    }

    var idCodigoOperadoraTelefonoState = remember {
        mutableStateOf(0)
    }

    var idCargoEmpleadoState = remember{
        mutableStateOf(0)
    }

    var idGrupoAtencionState = remember {
        mutableStateOf(0)
    }

    var idDepartamento = remember{
        mutableStateOf(0)
    }

    var cedulaState = remember {
        mutableStateOf("99999999")
    }

    var primerNombreState = remember{
        mutableStateOf<String>("Jaime")
    }

    var segundoNombreState = remember{
        mutableStateOf<String>("Luis")
    }

    var primerApellidoState = remember{
        mutableStateOf<String>("Delano")
    }

    var segundoApellidoState = remember{
        mutableStateOf<String>("Caceres")
    }

    var correoElectronicoState = remember{
        mutableStateOf("jdelano@gmail.com")
    }

    var numeroTelefonoState = remember{
        mutableStateOf("2566270")
    }

    var nombreUsuarioState = remember {
        mutableStateOf("jdelano")
    }

    var contrasenaVisibleState = remember {
        mutableStateOf(false)
    }

    var contrasenaState = remember{
        mutableStateOf("jdelano2000")
    }

    var confirmarContrasenaState = remember{
        mutableStateOf("jdelano2000")
    }

    var nombreSede = remember{
        mutableStateOf("")
    }

    var entradasNoVaciasState = remember {
        mutableStateOf(false)
    }

    var crearNuevoUsuarioState = remember{
        mutableStateOf(false)
    }

    val focusRequester = remember{
        FocusRequester()
    }

    val verticalScrollState = rememberScrollState()

    var tecnico = remember{
        mutableStateOf(Tecnico())
    }

    var clienteInterno = remember{
        mutableStateOf(ClienteInterno())
    }

    // Se obtienen los datos para los Spinners
    LaunchedEffect(Unit) {

        withContext(Dispatchers.IO)
        {

            TelefonoRequest().seleccionarOperadorasTelefono().forEach { codigo ->
                codigoExtensionTelefonoList.add(codigo.operadora)
            }

            EmpleadoRequest().seleccionarCargosEmpleado().forEach { cargo ->
                cargoEmpleadoList.add(cargo.tipoCargo)
            }

            EmpleadoRequest().seleccionarGrupoAtencion().forEach { grupo ->
                grupoAtencionList.add(grupo.grupoAtencion)
            }

            EmpleadoRequest().seleccionarDepartamentos().forEach {
                departamentosList.add(it)
                nombreDepartamentoList.add(it.nombre)
            }

        }

    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                title = {
                    Text("Crear usuario", modifier = Modifier.fillMaxWidth().padding(0.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            )
        }
    ) {

            Column(modifier = Modifier.fillMaxSize().padding(25.dp).verticalScroll(state = verticalScrollState, enabled = true))
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
                Text("Ingrese los datos correspondientes",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    //textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)

                Text("Datos personales",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
                Row(modifier = Modifier)
                {
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Tipo de usuario", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.padding(2.dp))
                        Spinner(
                            modifier = Modifier,
                            itemList = tipoUsuarioList,
                            onItemSelected = {
                                idTipoUsuarioState.value = tipoUsuarioList.indexOf(it)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Cédula", fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = cedulaState.value,
                            onValueChange = { number ->

                                // Cuando se deja la entrada en blanco, colapsa el programa. Por eso puse esta excepcion...
                                try
                                {
                                    cedulaState.value = number.replace(Regex("[^0-9]"), "")
                                }
                                catch(e: Exception)
                                {}
                                finally {
                                    cedulaState.value = number
                                }

                            },
                            label = { Text("Número de cédula", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = if (false) "Número de cédula fuera de rango" else "",
                                        color = if (false) Color.Red else Color.LightGray,
                                        modifier = Modifier
                                    )
                                }
                            }
                            , keyboardOptions = KeyboardOptions().copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                        )

                    }

                }
                // Nombre y apellido
                Text("Nombre completo", fontWeight = FontWeight.Bold)
                Row()
                {

                    Column(modifier = Modifier.weight(1F))
                    {

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = primerNombreState.value,
                            onValueChange = { newText ->
                                // Si el texto es menor a 50 caracteres, se almacena en newText
                                if (newText.length <= 20)
                                    primerNombreState.value = newText
                            },
                            label = { Text("Primer nombre", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = "${primerNombreState.value.length}/20",
                                        color = if (primerNombreState.value.length < 15) Color.LightGray else Color.Red,
                                        modifier = Modifier
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions().copy(imeAction = ImeAction.Next)
                        )

                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Column(modifier = Modifier.weight(1F))
                    {

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = segundoNombreState.value,
                            onValueChange = { newText ->
                                // Si el texto es menor a 50 caracteres, se almacena en newText
                                if (newText.length <= 20)
                                    segundoNombreState.value = newText
                            },
                            label = { Text("Segundo nombre", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = "${segundoNombreState.value.length}/20",
                                        color = if (segundoNombreState.value.length < 15) Color.LightGray else Color.Red,
                                        modifier = Modifier
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions().copy(imeAction = ImeAction.Next)
                        )

                    }

                }

                Row()
                {

                    Column(modifier = Modifier.weight(1F))
                    {

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = primerApellidoState.value,
                            onValueChange = { newText ->
                                // Si el texto es menor a 50 caracteres, se almacena en newText
                                if (newText.length <= 20)
                                    primerApellidoState.value = newText
                            },
                            label = { Text("Primer apellido", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = "${primerApellidoState.value.length}/20",
                                        color = if (primerApellidoState.value.length < 15) Color.LightGray else Color.Red,
                                        modifier = Modifier
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions().copy(imeAction = ImeAction.Next)
                        )

                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Column(modifier = Modifier.weight(1F))
                    {

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = segundoApellidoState.value,
                            onValueChange = { newText ->
                                // Si el texto es menor a 50 caracteres, se almacena en newText
                                if (newText.length <= 20)
                                    segundoApellidoState.value = newText
                            },
                            label = { Text("Segundo apellido", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = "${segundoApellidoState.value.length}/20",
                                        color = if (segundoApellidoState.value.length < 15) Color.LightGray else Color.Red,
                                        modifier = Modifier
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions().copy(imeAction = ImeAction.Next)
                        )

                    }

                }

                Text("Correo electrónico", fontWeight = FontWeight.Bold)
                Row() {

                    Column(modifier = Modifier.weight(1F))
                    {

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = correoElectronicoState.value,
                            onValueChange = { newText ->
                                // Si el texto es menor a 50 caracteres, se almacena en newText
                                if (newText.length <= 255)
                                    correoElectronicoState.value = newText
                            },
                            label = { Text("Correo electrónico", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = "${correoElectronicoState.value.length}/255",
                                        color = if (correoElectronicoState.value.length < 250) Color.LightGray else Color.Red,
                                        modifier = Modifier
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions().copy(imeAction = ImeAction.Next)
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
                            itemList = codigoExtensionTelefonoList
                        ) {
                            idCodigoOperadoraTelefonoState.value = codigoExtensionTelefonoList.indexOf(it)
                        }

                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Número", fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = numeroTelefonoState.value,
                            onValueChange = { number ->

                                // Cuando se deja la entrada en blanco, colapsa el programa. Por eso puse esta excepcion...
                                if(number.length <= 7)
                                {
                                    try {

                                        numeroTelefonoState.value = number.replace(Regex("[^0-9]"), "")
                                    } catch (e: Exception) {
                                    } finally {
                                        numeroTelefonoState.value = number
                                    }
                                }

                            },
                            label = { Text("Número", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = "${numeroTelefonoState.value.length}/7",
                                        color = Color.LightGray,
                                        modifier = Modifier
                                    )
                                }
                            }
                            , keyboardOptions = KeyboardOptions().copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)

                        )

                    }

                }

                Text("Datos laborales",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)

                Row()
                {
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Departamento", fontWeight = FontWeight.Bold)
                        Spinner(
                            modifier = Modifier,
                            itemList = nombreDepartamentoList,
                            onItemSelected = {

                                idDepartamento.value = nombreDepartamentoList.indexOf(it)
                                nombreSede.value = departamentosList[idDepartamento.value].sede.nombre
                            }
                        )

                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Sede", fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = nombreSede.value,
                            onValueChange = {},
                            label = { Text("Sede", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = "${segundoNombreState.value.length}/7",
                                        color = Color.LightGray,
                                        modifier = Modifier
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions().copy(imeAction = ImeAction.Next)

                        )

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
                            itemList = cargoEmpleadoList
                        ) {
                            idCargoEmpleadoState.value = cargoEmpleadoList.indexOf(it)
                        }

                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                if (idTipoUsuarioState.value == 1) {
                    Row()
                    {
                        Column(modifier = Modifier.weight(1F))
                        {
                            Text("Grupo de atención", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.padding(2.dp))
                            Spinner(
                                modifier = Modifier,
                                itemList = grupoAtencionList
                            ) {
                                idGrupoAtencionState.value = grupoAtencionList.indexOf(it)
                            }
                            Spacer(modifier = Modifier.padding(5.dp))

                        }
                    }
                }

                Text("Datos de usuario",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)

                Row()
                {
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Nombre de usuario", fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = nombreUsuarioState.value,
                            onValueChange = { newText ->
                                // Si el texto es menor a 50 caracteres, se almacena en newText
                                if (newText.length <= 20)
                                    nombreUsuarioState.value = newText
                            },
                            label = { Text("Nombre de usuario", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = "${primerNombreState.value.length}/20",
                                        color = if (primerNombreState.value.length < 15) Color.LightGray else Color.Red,
                                        modifier = Modifier
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions().copy(imeAction = ImeAction.Next)
                        )

                    }
                }
                Row()
                {
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Contraseña", fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = contrasenaState.value,
                            onValueChange = { newText ->
                                // Si el texto es menor a 50 caracteres, se almacena en newText
                                if (newText.length <= 20)
                                    contrasenaState.value = newText
                            },
                            label = { Text("Contraseña", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = "${contrasenaState.value.length}/20",
                                        color = if (contrasenaState.value.length < 15) Color.LightGray else Color.Red,
                                        modifier = Modifier
                                    )
                                }
                            },
                            trailingIcon = {
                                val image = if (contrasenaVisibleState.value == true)
                                {
                                    R.drawable.visible_icono
                                }else
                                    R.drawable.no_visible_icono

                                IconButton(onClick = {

                                    if (contrasenaVisibleState.value)
                                    {
                                        contrasenaVisibleState.value = false
                                    }else
                                        contrasenaVisibleState.value = true

                                }) {
                                    Icon(painter = painterResource(image), contentDescription = "", modifier = Modifier.size(25.dp))
                                }


                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = if(contrasenaVisibleState.value) VisualTransformation.None else PasswordVisualTransformation()
                        )

                    }
                }
                Row()
                {
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Confirmar contraseña", fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            value = confirmarContrasenaState.value,
                            onValueChange = { newText ->
                                // Si el texto es menor a 50 caracteres, se almacena en newText
                                if (newText.length <= 20)
                                    confirmarContrasenaState.value = newText
                            },
                            label = { Text("Confirmar contraseña", fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Color.Black
                            ),
                            supportingText = {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier.padding(0.dp))

                                    Text(
                                        text = "${confirmarContrasenaState.value.length}/20",
                                        color = if (confirmarContrasenaState.value.length < 15) Color.LightGray else Color.Red,
                                        modifier = Modifier
                                    )
                                }
                            },
                            trailingIcon = {
                                val image = if (contrasenaVisibleState.value == true)
                                {
                                    R.drawable.visible_icono
                                }else
                                    R.drawable.no_visible_icono

                                IconButton(onClick = {

                                    if (contrasenaVisibleState.value)
                                    {
                                        contrasenaVisibleState.value = false
                                    }else
                                        contrasenaVisibleState.value = true

                                }) {
                                    Icon(painter = painterResource(image), contentDescription = "", modifier = Modifier.size(25.dp))
                                }


                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = if(contrasenaVisibleState.value) VisualTransformation.None else PasswordVisualTransformation()
                        )

                    }
                }

                Spacer(modifier = espacioSpacer)

                Button(modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                        // Si los valores asociados a los Spinners es igual a 0, no aplica

                        val usuarioEmpleado = Usuario(
                            nombre = nombreUsuarioState.value,
                            password = contrasenaState.value,
                            idTipoUsuario = idTipoUsuarioState.value
                        )

                        val departamentoEmpleado = Departamento(
                            id = idDepartamento.value
                        )

                        val cargoEmpleado = CargoEmpleado(
                            id = idCargoEmpleadoState.value
                        )

                        val telefonoEmpleado = TelefonoEmpleado(
                            idCodigoExtensionTelefono = idCodigoOperadoraTelefonoState.value,
                            extension = numeroTelefonoState.value
                        )

                        val nuevoEmpleado = Empleado(
                            cedula = cedulaState.value.toInt(),
                            primerNombre = primerNombreState.value,
                            segundoNombre = segundoNombreState.value,
                            primerApellido = primerApellidoState.value,
                            segundoApellido = segundoApellidoState.value,
                            correoElectronico = correoElectronicoState.value,
                            telefonoPersona = telefonoEmpleado,
                            departamento = departamentoEmpleado,
                            cargoEmpleado = cargoEmpleado,
                            usuario = usuarioEmpleado,
                        )

                        if(idTipoUsuarioState.value == 1)       // Tecnico
                        {

                            //... los datos del grupo de atencion
                            val grupoAtencion = GrupoAtencion(grupoAtencion = grupoAtencionList[idGrupoAtencionState.value - 1])

                            //... Se crea la fila en la tabla tecnico
                            tecnico.value = Tecnico(
                                grupoAtencion = grupoAtencion,
                                empleado = nuevoEmpleado
                            )

                        }
                        else if(idTipoUsuarioState.value == 2)  // Cliente Interno
                        {

                            //... Se crea un nuevo cliente interno
                            clienteInterno.value = ClienteInterno(
                                empleado = nuevoEmpleado
                            )

                        }

                        crearNuevoUsuarioState.value = true

                    }
                )
                {
                    Text(text = "CREAR USUARIO", color = Color.White)
                }
                Spacer(modifier = Modifier.padding(40.dp))

            }

    }

    if(crearNuevoUsuarioState.value){

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                Log.d("Nuevo Usuario...", "Creacion del nuevo usuario...")
                if(idTipoUsuarioState.value == 1)
                {
                    UsuarioRequest().insertarUsuarioTecnico(tecnico.value)

                } else if(idTipoUsuarioState.value == 2)
                {
                    UsuarioRequest().insertarUsuarioClienteInterno(clienteInterno.value)
                }
            }
        }
        crearNuevoUsuarioState.value = false

    }

}

@Preview(showBackground = true)
@Composable
fun CrearUsuarioPreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {
        CrearUsuario(navController)
    }
}
