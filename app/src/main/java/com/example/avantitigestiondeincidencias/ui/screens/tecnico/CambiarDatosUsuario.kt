package com.example.avantitigestiondeincidencias.ui.screens.tecnico

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.Supabase.TelefonoRequest
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonCargaPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.OutlinedTextFieldPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import io.ktor.util.toLowerCasePreservingASCIIRules
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
                        containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919))
{

    val image = R.drawable.editar_usuario_icon

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
        mutableStateOf(empleado.cedula.toString())
    }

    var primerNombreState = remember{
        mutableStateOf<String>(empleado.primerNombre)
    }

    var segundoNombreState = remember{
        mutableStateOf<String>(empleado.segundoNombre)
    }

    var primerApellidoState = remember{
        mutableStateOf<String>(empleado.primerApellido)
    }

    var segundoApellidoState = remember{
        mutableStateOf<String>(empleado.segundoApellido)
    }

    var correoElectronicoState = remember{
        mutableStateOf(empleado.correoElectronico)
    }

    var numeroTelefonoState = remember{
        mutableStateOf(empleado.telefonoEmpleado.extension)
    }

    var nombreUsuarioState = remember {
        mutableStateOf(empleado.usuario.nombre)
    }

    var nombreSede = remember{
        mutableStateOf("")
    }

    var actualizarUsuarioState = remember{
        mutableStateOf(false)
    }

    val focusRequester = remember{
        FocusRequester()
    }

    val verticalScrollState = rememberScrollState()

    var tecnicoNuevo = remember{
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

    var cargandoContenidoSpinners = remember{
        mutableStateOf(true)
    }

    Network.networkCallback(navController, context)


    // Se obtienen los datos para los Spinners
    LaunchedEffect(Unit) {

        withContext(Dispatchers.IO)
        {

            TelefonoRequest().seleccionarOperadorasTelefono().forEach { codigo ->
                codigoExtensionTelefonoList.add(codigo.operadora)
                idCodigoOperadoraTelefonoState.value = empleado.telefonoEmpleado.idCodigoOperadoraTelefono
            }

            EmpleadoRequest().seleccionarCargosEmpleado().forEach { cargo ->
                cargoEmpleadoList.add(cargo.tipoCargo)
                idCargoEmpleadoState.value = empleado.idCargoEmpleado
            }

            EmpleadoRequest().seleccionarDepartamentos().forEach {
                departamentosList.add(it)
                nombreDepartamentoList.add(it.nombre)
                idDepartamento.value = empleado.idDepartamento
            }

            // Grupo de atencion
            if(empleado.usuario.idTipoUsuario == 1){

                EmpleadoRequest().seleccionarGrupoAtencion().forEach { grupo ->
                    grupoAtencionList.add(grupo.grupoAtencion)
                }

                EmpleadoRequest().seleccionarIdGrupoAtencionByEmpleadoId(empleado.id){
                    idGrupoAtencionState.value = it
                }

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

                Column(modifier = Modifier.weight(1F))
                {
                    Text("Cédula", fontWeight = FontWeight.Bold)
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = cedulaState.value,
                        onValueChange = { number ->

                            if (number.length <= 8)
                                cedulaState.value = number

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
                        value = primerNombreState.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { it.isLetter() } && newText.length <= 20)
                                primerNombreState.value = newText
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
                        value = segundoNombreState.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { it.isLetter() } && newText.length <= 20)
                                segundoNombreState.value = newText
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
                        value = primerApellidoState.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { it.isLetter() } && newText.length <= 20)
                                primerApellidoState.value = newText
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
                        value = segundoApellidoState.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { it.isLetter() } && newText.length <= 20)
                                segundoApellidoState.value = newText
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
                        value = correoElectronicoState.value,
                        onValueChange = { newText ->
                            // Si el texto no contiene espacios y es menor a 50 caracteres, se almacena en newText
                            if (newText.all { !it.isWhitespace() } && newText.length <= 255)
                                correoElectronicoState.value = newText
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
                        posicionInicial = empleado.telefonoEmpleado.idCodigoOperadoraTelefono,
                        itemList = codigoExtensionTelefonoList
                    ) {
                        idCodigoOperadoraTelefonoState.value = codigoExtensionTelefonoList.indexOf(it)
                    }

                }

                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier.weight(1F))
                {
                    Text("Número", fontWeight = FontWeight.Bold)
                    OutlinedTextFieldPersonalizado(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = numeroTelefonoState.value,
                        onValueChange = { number ->

                            // Cuando se deja la entrada en blanco, colapsa el programa. Por eso puse esta excepcion...
                            if (number.all { it.isDigit() && !it.isWhitespace() } && number.length <= 7) {
                                    numeroTelefonoState.value = number
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
                            itemList = nombreDepartamentoList,
                            posicionInicial = empleado.idDepartamento,
                            onItemSelected = {

                                idDepartamento.value = nombreDepartamentoList.indexOf(it)
                                nombreSede.value = departamentosList[idDepartamento.value].sede.nombre
                                Log.d("DEPARTAMENTO", idDepartamento.value.toString())
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
                        itemList = cargoEmpleadoList
                    ) {
                        idCargoEmpleadoState.value = cargoEmpleadoList.indexOf(it)
                    }

                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
            if (empleado.usuario.idTipoUsuario == 1) {
                idDepartamento.value = 6
                Row()
                {
                    Column(modifier = Modifier.weight(1F))
                    {
                        Text("Grupo de atención", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.padding(2.dp))
                        Spinner(
                            modifier = Modifier.fillMaxWidth(),
                            posicionInicial = idGrupoAtencionState.value,
                            itemList = grupoAtencionList
                        ) {
                            idGrupoAtencionState.value = grupoAtencionList.indexOf(it)

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
                        value = nombreUsuarioState.value,
                        onValueChange = { newText ->
                            // Si el texto es menor a 50 caracteres, se almacena en newText
                            if (newText.all { !it.isWhitespace() && it.isLetter() || it.isDigit() } && newText.length <= 20)
                                nombreUsuarioState.value = newText
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

                    //Primero, se verifica que el nombre de usuario, la cedula y el correo electronico no este repetidos en la tabla
                    ingresarbuttonState.value = true
                    validaciones.value = true

                },
                isLoading = ingresarbuttonState.value
            ) {
                Text(text = "ACTUALIZAR DATOS", color = Color.White, fontFamily = montserratFamily)
            }
            Spacer(modifier = Modifier.padding(40.dp))

        }
    }

    if (idDepartamento.value > 0)
    {
        nombreSede.value = departamentosList[idDepartamento.value].sede.nombre
    }

    // Se validan las entradas para crear un nuevo usuario
    if (validaciones.value)
    {

        // Se crea el objeto
        val usuarioEmpleado = Usuario(
            nombre = nombreUsuarioState.value
        )

        val telefonoEmpleado = TelefonoEmpleado(
            id = empleado.idTeléfonoEmpleado,
            idCodigoOperadoraTelefono = idCodigoOperadoraTelefonoState.value,
            extension = numeroTelefonoState.value
        )

        val nuevoEmpleado = Empleado(
            id = empleado.id,
            cedula = if(cedulaState.value.isNotBlank()) { cedulaState.value.toInt() } else 0,
            primerNombre = primerNombreState.value,
            segundoNombre = segundoNombreState.value,
            primerApellido = primerApellidoState.value,
            segundoApellido = segundoApellidoState.value,
            correoElectronico = correoElectronicoState.value.toLowerCasePreservingASCIIRules(),  //Se convierte la cadena de texto en minusculas
            telefonoEmpleado = telefonoEmpleado,
            usuario = usuarioEmpleado,
            idDepartamento = if (empleado.usuario.idTipoUsuario == 1) {empleado.idDepartamento} else idDepartamento.value,
            idCargoEmpleado = idCargoEmpleadoState.value,
            idUsuario = empleado.idUsuario,
            idTeléfonoEmpleado = empleado.idTeléfonoEmpleado
        )

        Log.d("NUEVO USUARIO", nuevoEmpleado.toString())

        if(empleado.usuario.idTipoUsuario == 1)       // Tecnico
        {

            nuevoEmpleado.idDepartamento = nombreDepartamentoList.indexOf("Tecnología")
            //... los datos del grupo de atencion
            //Log.d("GRUPO DE ATENCION", idGrupoAtencionState.value.toString())
            val grupoAtencion = GrupoAtencion(
                id = idGrupoAtencionState.value,
                grupoAtencion = grupoAtencionList[idGrupoAtencionState.value]
            )

            //... Se crea la fila en la tabla tecnico
            tecnicoNuevo.value = Tecnico(
                grupoAtencion = grupoAtencion,
                idGrupoAtencion =  idGrupoAtencionState.value,
                empleado = nuevoEmpleado
            )

            // Se realizan las validaciones
            validarDatosErroneosTecnico(tecnicoNuevo.value){ error ->

                if (error.isNotBlank())
                {
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    ingresarbuttonState.value = false
                    validaciones.value = false
                }

            }

        }
        else if(empleado.usuario.idTipoUsuario == 2)  // Cliente Interno
        {

            //... Se crea un nuevo cliente interno
            clienteInterno.value = ClienteInterno(
                empleado = nuevoEmpleado
            )

            if(nombreSede.value.isBlank())
            {
                Toast.makeText(context, "Por favor, ingrese un departamento válido.", Toast.LENGTH_SHORT).show()
                ingresarbuttonState.value = false
                validaciones.value = false
            }

        }

        validarDatosErroneosEmpleado(nuevoEmpleado){ error ->

            if(error.isNotBlank())
            {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                ingresarbuttonState.value = false
                validaciones.value = false
            }
            else
            {
                actualizarUsuarioState.value = true
                validarRepetidos.value = false
            }
        }

        if(actualizarUsuarioState.value){

            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d("Actualizando Usuario...", "Actualización del nuevo usuario...")
                    if(empleado.usuario.idTipoUsuario == 1)
                    {
                        EmpleadoRequest().actualizarDatosTecnico(empleado, tecnicoNuevo.value)

                    } else if(empleado.usuario.idTipoUsuario == 2)
                    {
                        EmpleadoRequest().actualizarDatosEmpleado(empleado, clienteInterno.value.empleado)
                    }
                }
                navController.popBackStack()
                Toast.makeText(context, "Usuario actualizado con éxito.", Toast.LENGTH_LONG).show()

            }


        }

    }

}

fun validarDatosErroneosTecnico(tecnico: Tecnico, mensajeError:(String) -> Unit)
{

    if (tecnico.idGrupoAtencion == 0)
        mensajeError("Por favor, ingrese un grupo de atención válido.")


}

suspend fun validarDatosUsuarioActualizar(idEmpleado: Int,
                                          idTelefonoEmpleado: Int,
                                          idUsuario: Int,
                                          nombreUsuario: String,
                                          correoElectronico: String,
                                          cedula: Int,
                                          numeroTelefono: TelefonoEmpleado,
                                          resultados: (Int) -> Unit)
{

    var repetido = 0

    // Se comprueba que haya alguna congruencia pero con diferentes identificadores. De ser cierto, se retorna un valor para mostrar el mensaje
    // Hay que ser valores nulleables, para evitar problemas al generar la consulta, OJO...
    UsuarioRequest().seleccionarUsuarioByNombre(nombreUsuario){
        if (it != null && (it.nombre == nombreUsuario && it.id != idUsuario))  repetido = 1
    }

    EmpleadoRequest().buscarEmpleadoByCorreoElectronico(correoElectronico){
        if (it != null && (it.correoElectronico == correoElectronico && it.id != idUsuario)) repetido = 2
    }

    EmpleadoRequest().buscarEmpleadoByCedula(cedula){
        if (it != null && (it.cedula == cedula && it.id != idEmpleado)) repetido = 3
    }

    TelefonoRequest().buscarTelefonoEmpleado(numeroTelefono){

        if(it != null && (
            it.idCodigoOperadoraTelefono == numeroTelefono.idCodigoOperadoraTelefono &&
            it.extension == numeroTelefono.extension &&
            it.id != idTelefonoEmpleado))
        {
            repetido = 4
        }

    }

    resultados(repetido)

}

@Composable
fun validarDatosErroneosEmpleado(empleado: Empleado, mensajeError:(String) -> Unit)
{

    Log.d("EMPLEADO", empleado.toString())
    val limiteCedula = 31000000

    //Se valida el correo
    val cantidadArrobasCorreo: Int = empleado.correoElectronico.count{ it == '@' }
    val cantidadPuntosCorreo: Int = empleado.correoElectronico.count{ it == '.' }

    // Se comprueba que el nombre del empleado no tenga digitos
    if(cantidadArrobasCorreo != 1 || cantidadPuntosCorreo < 1)
    {
        mensajeError("Por favor, ingrese un correo electrónico válido.")
    }
    else if(empleado.telefonoEmpleado.extension.length != 7) // Si el numero de telefono no tiene una logitud valida, se avisa
    {
        mensajeError("Por favor, ingrese un numero de teléfono válido.")
    }
    else if (empleado.cedula.toInt() < 1 || empleado.cedula.toInt() > limiteCedula) //Si la cedula es no es compatible, se muestra el aviso
    {
        mensajeError("Por favor, ingrese un número de cédula válido.")
    }
    else if(empleado.telefonoEmpleado.idCodigoOperadoraTelefono == 0)
    {
        mensajeError("Por favor, ingrese un código de extensión válido.")
    }
    else if(empleado.idCargoEmpleado == 0)
    {
        mensajeError("Por favor, ingrese un cargo válido.")
    }
    else if((empleado.cedula.toString().isEmpty()               ||
                (empleado.primerNombre.isEmpty() || empleado.primerNombre.length < 3)                 ||
                (empleado.segundoNombre.isEmpty() || empleado.segundoNombre.length < 3)               ||
                (empleado.primerApellido.isEmpty() || empleado.primerApellido.length < 3)             ||
                (empleado.segundoApellido.isEmpty() || empleado.segundoApellido.length < 3)             ||
                (empleado.correoElectronico.isEmpty() || empleado.correoElectronico.length < 3)           ||
                (empleado.telefonoEmpleado.extension.isEmpty())  ||
                (empleado.usuario.nombre.isEmpty() || empleado.usuario.nombre.length < 3))
    )
    {
        mensajeError("Por favor, complete los campos correspondientes de forma correcta")
    }


    val scope = rememberCoroutineScope()

    scope.launch {

        validarDatosUsuarioActualizar(empleado.id,
            empleado.idTeléfonoEmpleado,
            empleado.idUsuario,
            empleado.usuario.nombre,
            empleado.correoElectronico,
            empleado.cedula,
            empleado.telefonoEmpleado){error ->

            when(error){
                0 -> mensajeError("")// Sin valores repetidos
                1 -> mensajeError("El nombre de usuario ya existe") // Nombre de Usuario
                2 -> mensajeError("El correo electrónico ya existe")
                3 -> mensajeError("El número de cédula ya existe")
                4 -> mensajeError("El número de teléfono ya existe")
            }

        }
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
