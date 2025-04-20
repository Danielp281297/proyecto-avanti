package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
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
import com.example.avantitigestiondeincidencias.ui.screens.componentes.SHA512
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import io.ktor.util.toLowerCasePreservingASCIIRules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearUsuario(navController: NavController)
{

    val context = LocalContext.current

    val limiteCedula = 31000000

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
        mutableStateOf("")
    }

    var primerNombreState = remember{
        mutableStateOf<String>("")
    }

    var segundoNombreState = remember{
        mutableStateOf<String>("")
    }

    var primerApellidoState = remember{
        mutableStateOf<String>("")
    }

    var segundoApellidoState = remember{
        mutableStateOf<String>("")
    }

    var correoElectronicoState = remember{
        mutableStateOf("")
    }

    var numeroTelefonoState = remember{
        mutableStateOf("")
    }

    var nombreUsuarioState = remember {
        mutableStateOf("")
    }

    var contrasenaVisibleState = remember {
        mutableStateOf(false)
    }

    var contrasenaState = remember{
        mutableStateOf("")
    }

    var confirmarContrasenaState = remember{
        mutableStateOf("")
    }

    var nombreSede = remember{
        mutableStateOf("")
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

    var ingresarbuttonState = remember{
        mutableStateOf(false)
    }

    var validaciones = remember{
        mutableStateOf(false)
    }

    var validarRepetidos = remember {
        mutableStateOf(false
        )
    }

    var mensajeValidacion = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

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

                                if(number.length <= 8)
                                    cedulaState.value = number

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
                            , keyboardOptions = KeyboardOptions().copy(keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Next)
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
                                if (newText.all{it.isLetter()} && newText.length <= 20)
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
                                if (newText.all{it.isLetter()} && newText.length <= 20)
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
                                if (newText.all{it.isLetter()} && newText.length <= 20)
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
                                if (newText.all{it.isLetter()} && newText.length <= 20)
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
                                // Si el texto no contiene espacios y es menor a 50 caracteres, se almacena en newText
                                if (newText.all { !it.isWhitespace() } && newText.length <= 255)
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
                            , keyboardOptions = KeyboardOptions().copy(keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Next)

                        )

                    }

                }

                Text("Datos laborales",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)

                if(idTipoUsuarioState.value == 2) {
                    idDepartamento.value = 0
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
                                if (newText.all { !it.isWhitespace() && it.isLetter() || it.isDigit() } && newText.length <= 20)
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
                                        text = "${nombreUsuarioState.value.length}/20",
                                        color = if (nombreUsuarioState.value.length < 15) Color.LightGray else Color.Red,
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
                                if (newText.all { !it.isWhitespace() } && newText.length <= 20)
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
                                if (newText.all { !it.isWhitespace() } && newText.length <= 20)
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

                        //Primero, se verifica que el nombre de usuario, la cedula y el correo electronico no este repetidos en la tabla
                        ingresarbuttonState.value = true
                        validaciones.value = true

                    }

                )
                {
                    if (ingresarbuttonState.value)
                    {
                        iconoCarga(Modifier)
                    }else
                    Text(text = "CREAR USUARIO", color = Color.White)
                }
                Spacer(modifier = Modifier.padding(40.dp))

            }

    }

    // Se validan las entradas para crear un nuevo usuario
    if (validaciones.value)
    {

            //Se valida el correo
            val cantidadArrobasCorreo: Int = correoElectronicoState.value.count{ it == '@' }
            val cantidadPuntosCorreo: Int = correoElectronicoState.value.count{ it == '.' }

            // Se comprueba que el nombre del empleado no tenga digitos
            if(cantidadArrobasCorreo != 1 || cantidadPuntosCorreo < 1)
            {
                mensajeValidacion.value = "Por favor, ingrese un correo electrónico valido."
            }
            else if(numeroTelefonoState.value.length != 7) // Si el numero de telefono no tiene una logitud valida, se avisa
            {
                mensajeValidacion.value = "Por favor, ingrese un numero de teléfono valido."
            }
            else if (cedulaState.value.toInt() < 0 || cedulaState.value.toInt() > limiteCedula) //Si la cedula es no es compatible, se muestra el aviso
            {
                mensajeValidacion.value = "Por favor, ingrese un número de cédula valido."

            }
            else if(contrasenaState.value != confirmarContrasenaState.value) //Si las contrasenas no coinciden, se muestra el aviso
            {
                // Mensaje de aviso
                mensajeValidacion.value = "Aviso: Las contraseñas no coinciden."
            }
            else if((cedulaState.value.isNotEmpty()                  &&
                        primerNombreState.value.isNotEmpty()             &&
                        segundoNombreState.value.isNotEmpty()            &&
                        primerApellidoState.value.isNotEmpty()           &&
                        segundoApellidoState.value.isNotEmpty()          &&
                        correoElectronicoState.value.isNotEmpty()        &&
                        numeroTelefonoState.value.isNotEmpty()           &&
                        nombreUsuarioState.value.isNotEmpty()            &&
                        contrasenaState.value.isNotEmpty())

                //El valor asociado a los spinners
                &&
                (idTipoUsuarioState.value > 0 &&
                        idCodigoOperadoraTelefonoState.value > 0 &&
                        idCargoEmpleadoState.value > 0)
                &&
                ((idTipoUsuarioState.value == 1 && idGrupoAtencionState.value > 0) || (idTipoUsuarioState.value == 2 && idDepartamento.value > 0) )

            )
            {
                // Mensaje de aviso
                Log.d("USUARIO", "CREADO")

                val usuarioEmpleado = Usuario(
                    nombre = nombreUsuarioState.value,
                    password = SHA512(contrasenaState.value),
                    idTipoUsuario = idTipoUsuarioState.value
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
                    correoElectronico = correoElectronicoState.value.toLowerCasePreservingASCIIRules(),  //Se convierte la cadena de texto en minusculas
                    telefonoEmpleado = telefonoEmpleado,
                    usuario = usuarioEmpleado,
                    idDepartamento = idDepartamento.value,
                    idCargoEmpleado = idCargoEmpleadoState.value
                )

                    if(idTipoUsuarioState.value == 1) // Tecnico
                    {

                        nuevoEmpleado.idDepartamento = nombreDepartamentoList.indexOf("Tecnología")
                        //... los datos del grupo de atencion
                        //Log.d("GRUPO DE ATENCION", idGrupoAtencionState.value.toString())
                        val grupoAtencion = GrupoAtencion(id = idGrupoAtencionState.value, grupoAtencion = grupoAtencionList[idGrupoAtencionState.value])

                        //... Se crea la fila en la tabla tecnico
                        tecnico.value = Tecnico(
                            grupoAtencion = grupoAtencion,
                            idGrupoAtencion =  idGrupoAtencionState.value,
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

                validarRepetidos.value = true

            }
            else{
                // Mensaje de acceso
                mensajeValidacion.value = "Por favor, complete los campos correspondientes"
            }




        if (mensajeValidacion.value.isNotEmpty()) {
            Toast.makeText(context, mensajeValidacion.value, Toast.LENGTH_SHORT).show()
            mensajeValidacion.value = ""
        }

        validaciones.value = false
    }

    if(validarRepetidos.value)
    {
        scope.launch {
            validarDatosUnicosRepetidos(nombreUsuarioState.value, correoElectronicoState.value, cedulaState.value,
                TelefonoEmpleado(
                    idCodigoExtensionTelefono = idCodigoOperadoraTelefonoState.value,
                    extension = numeroTelefonoState.value
                )) {

                when (it) {
                    0 -> crearNuevoUsuarioState.value = true                                // Sin valores repetidos
                    1 -> mensajeValidacion.value = "El nombre de usuario ya existe" // Nombre de Usuario
                    2 -> mensajeValidacion.value = "El correo electrónico ya existe"// Correo electronico
                    3 -> mensajeValidacion.value = "El número de cédula ya existe"  // Cedula del empleado
                    4 -> mensajeValidacion.value = "El número de teléfono ya existe"// Nuemro de telefono
                }

                ingresarbuttonState.value = false

            }

            if (mensajeValidacion.value.isNotEmpty()) {
                Toast.makeText(context, mensajeValidacion.value, Toast.LENGTH_SHORT).show()
                mensajeValidacion.value = ""

            }
        }

        validarRepetidos.value = false
    }

    if(crearNuevoUsuarioState.value){

        LaunchedEffect(Unit) {
            CoroutineScope(Dispatchers.IO).launch {
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

        ingresarbuttonState.value = false
        navController.popBackStack()
        Toast.makeText(context, "Usuario creado con éxito.", Toast.LENGTH_LONG).show()
        crearNuevoUsuarioState.value = false

    }

}

suspend fun validarDatosUnicosRepetidos(nombreUsuario: String,
                                correoElectronico: String,
                                cedula: String,
                                numeroTelefono: TelefonoEmpleado,
                                repetido: (Int) -> Unit) {

    var repetido = 0


    UsuarioRequest().buscarNombreUsuario(nombreUsuario) {

        if (it != null)
            repetido = 1

    }

    EmpleadoRequest().buscarCorreoElectronico(correoElectronico) {

        if (it != null)
            repetido = 2

    }


    EmpleadoRequest().buscarCedula(cedula) {

        if (it != null)
            repetido = 3

    }

    TelefonoRequest().buscarTelefonoEmpleado(numeroTelefono)
    {

        if (it != null)
            repetido = 4

    }

    repetido(repetido)
}

@Preview(showBackground = true)
@Composable
fun CrearUsuarioPreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {
        CrearUsuario(navController)
    }
}
