package com.example.avantitigestiondeincidencias.ViewModel

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Departamento
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.TelefonoEmpleado
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.Supabase.TelefonoRequest
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.ui.screens.componentes.SHA512
import io.ktor.util.toLowerCasePreservingASCIIRules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.plus

open class UsuarioFormularioViewModel: ViewModel() {

    private val limiteInferiorCedula = 3000000
    private val limiteSuperiorCedula = 31500000

    protected val _mensajeError = MutableStateFlow<String>("")
    val mensajeError: StateFlow<String> get() = _mensajeError.asStateFlow()

    private val _idTipoUsuario = MutableStateFlow<Int>(0)
    val idTipoUsuario: StateFlow<Int> get() = _idTipoUsuario.asStateFlow()

    private val _nacionalidad = MutableStateFlow<Char>('V')
    val nacionalidad: StateFlow<Char> get() = _nacionalidad.asStateFlow()

    private val _cedula = MutableStateFlow<String>("")
    val cedula: StateFlow<String> get() = _cedula.asStateFlow()

    private val _primerNombre = MutableStateFlow<String>("")
    val primerNombre: StateFlow<String> get() = _primerNombre.asStateFlow()

    private val _segundoNombre = MutableStateFlow<String>("")
    val segundoNombre: StateFlow<String> get() = _segundoNombre.asStateFlow()

    private val _primerApellido = MutableStateFlow<String>("")
    val primerApellido: StateFlow<String> get() = _primerApellido.asStateFlow()

    private val _segundoApellido = MutableStateFlow<String>("")
    val segundoApellido: StateFlow<String> get() = _segundoApellido.asStateFlow()

    private val _correoElectronico = MutableStateFlow<String>("")
    val correoElectronico: StateFlow<String> get() = _correoElectronico.asStateFlow()

    private val _idCodigoOperadoraTelefono = MutableStateFlow<Int>(0)
    val idCodigoOperadoraTelefono: StateFlow<Int> get() = _idCodigoOperadoraTelefono.asStateFlow()

    private val _extensionTelefono = MutableStateFlow<String>("")
    val extensionTelefono: StateFlow<String> get() = _extensionTelefono.asStateFlow()

    // DATOS PARA CLIENTE INTERNO
    private val _idDepartamento = MutableStateFlow<Int>(0)
    val idDepartamento: StateFlow<Int> get() = _idDepartamento.asStateFlow()

    private val _nombreSede = MutableStateFlow<String>("")
    val nombreSede: StateFlow<String> get() = _nombreSede.asStateFlow()

    private val _idCargo = MutableStateFlow<Int>(0)
    val idCargo: StateFlow<Int> get() = _idCargo.asStateFlow()

    // DATOS PARA TECNICO
    private val _idGrupoAtencion = MutableStateFlow<Int>(0)
    val idGrupoAtencion: StateFlow<Int> get() = _idGrupoAtencion.asStateFlow()

    private val _nombreUsuario = MutableStateFlow<String>("")
    val nombreUsuario: StateFlow<String> get() = _nombreUsuario.asStateFlow()

    private val _contrasenaUsuario = MutableStateFlow<String>("")
    val contrasenaUsuario: StateFlow<String> get() = _contrasenaUsuario.asStateFlow()

    private val _confirmarContrasenaUsuario = MutableStateFlow<String>("")
    val confirmarContrasenaUsuario: StateFlow<String> get() = _confirmarContrasenaUsuario.asStateFlow()



    private val _listaCodigoOperadoraTelefono = MutableStateFlow<List<String>>(listOf("-- Seleccione"))
    val listaCodigoOperadoraTelefono: StateFlow<List<String>> get() = _listaCodigoOperadoraTelefono.asStateFlow()

    private val _listaCargos = MutableStateFlow<List<String>>(listOf("-- Seleccione"))
    val listaCargos: StateFlow<List<String>> get() = _listaCargos.asStateFlow()

    private val _listaDepartamentos = MutableStateFlow<List<Departamento>>(emptyList())
    val listaDepartamentos: StateFlow<List<Departamento>> get() = _listaDepartamentos.asStateFlow()

    private val _listaSede = MutableStateFlow<List<String>>(listOf("-- Seleccione"))
    val listaSede: StateFlow<List<String>> get() = _listaSede.asStateFlow()

    private val _listaGrupoAtencion = MutableStateFlow<List<String>>(listOf("-- Seleccione"))
    val listaGrupoAtencion: StateFlow<List<String>> get() = _listaGrupoAtencion.asStateFlow()



    fun setMensajeError() { _mensajeError.value = datosIncorrectos() }

    fun setIdTipoUsuario(valor: Int) { _idTipoUsuario.value = valor }
    fun setNacionalidad(caracter: Char) { _nacionalidad.value = caracter}
    fun setCedula(entrada: String) { _cedula.value = entrada }
    fun setPrimerNombre(entrada: String) { _primerNombre.value = entrada }
    fun setSegundoNombre(entrada: String) { _segundoNombre.value = entrada }
    fun setPrimerApellido(entrada: String) { _primerApellido.value = entrada }
    fun setSegundoApellido(entrada: String) { _segundoApellido.value = entrada }
    fun setCorreoElectronico(entrada: String) { _correoElectronico.value = entrada }
    fun setIdCodigoOperadoraTelefono(valor: Int){ _idCodigoOperadoraTelefono.value = valor }
    fun setExtensionTelefono(entrada: String) { _extensionTelefono.value = entrada}
    fun setIdDepartamento(valor: Int) { _idDepartamento.value = valor }
    fun setNombreSede(entrada: String) { _nombreSede.value = entrada }
    fun setIdCargo(valor: Int) { _idCargo.value = valor }

    fun setIdGrupoAtencion(valor: Int) { _idGrupoAtencion.value = valor }

    fun setNombreUsuario(entrada:String) {
        if (entrada.all { !it.isWhitespace() && it!='\'' && it!='\"' && it!='=' && it.isLetter() || it.isDigit() } && entrada.length <= 20)
            _nombreUsuario.value = entrada
    }
    fun setContrasenaUsuario(entrada: String) {
        if (entrada.all { !it.isWhitespace()  && it!='\'' && it!='\"' && it!='=' } && entrada.length <= 20)
            _contrasenaUsuario.value = entrada
    }
    fun setConfirmarContrasenaUsuario(entrada: String) {
        if (entrada.all { !it.isWhitespace()  && it!='\'' && it!='\"' && it!='=' } && entrada.length <= 20)
            _confirmarContrasenaUsuario.value = entrada
    }

    // Se obtienen los datos de la base de datos
    suspend fun obtenerCodigoOperadoraTelefono() =
        _listaCodigoOperadoraTelefono.update { it + TelefonoRequest().seleccionarOperadorasTelefono().map { it.operadora } }

    suspend fun obtenerCargos() =
        _listaCargos.update { it + EmpleadoRequest().seleccionarCargosEmpleado().map{ it.tipoCargo } }

    suspend fun obtenerGrupoAtencion() =
        _listaGrupoAtencion.update { it + EmpleadoRequest().seleccionarGrupoAtencion().map{ it.grupoAtencion } }

    suspend fun obtenerDepartamentos() {

        _listaDepartamentos.update { it + Departamento(nombre = "Seleccione --") + EmpleadoRequest().seleccionarDepartamentos()}

        _listaSede.update { listaDepartamentos.value.map{ it.sede.nombre } }

    }

    suspend fun obtenerIdGrupoAtencion(idEmpleado: Int) =
        setIdGrupoAtencion(EmpleadoRequest().seleccionarIdGrupoAtencionByEmpleadoId(idEmpleado))


    // VALIDACIONES
    private fun validaridTipoUsuario(): Boolean = idTipoUsuario.value > 0
    protected fun validarCedula(): Boolean = (cedula.value.isNotBlank() && (cedula.value.isDigitsOnly() && cedula.value.toInt() > limiteInferiorCedula && cedula.value.toInt() < limiteSuperiorCedula))
    protected fun validarPrimerNombre(): Boolean = primerNombre.value.count() > 2
    protected fun validarSegundoNombre(): Boolean = segundoNombre.value.count() > 2
    protected fun validarPrimerApellido(): Boolean = primerApellido.value.count() > 2
    protected fun validarSegundoApellido(): Boolean = segundoApellido.value.count() > 2

    // Se comprueba que el correo sea un String de mas de 2 caracteres, tenga solo un arroba y tenga mas de un punto
    protected fun validarCorreo():Boolean = correoElectronico.value.isNotBlank() && correoElectronico.value.count() > 2 && correoElectronico.value.count{ it == '@'} == 1 && correoElectronico.value.count{ it == '.'} > 0
    protected fun validarIdCodigoOperadoraTelefono(): Boolean = idCodigoOperadoraTelefono.value > 0
    protected fun validarExtensionTelefono():Boolean = extensionTelefono.value.isNotBlank() && extensionTelefono.value.isDigitsOnly() && extensionTelefono.value.count() == 7
    protected fun validarIdCargo(): Boolean = idCargo.value > 0

    protected fun validarIdDepartamento():Boolean = idDepartamento.value > 0

    protected fun validarIdGrupoAtencion(): Boolean = idGrupoAtencion.value > 0

    protected fun validarNombreUsuario(): Boolean = nombreUsuario.value.isNotBlank() && nombreUsuario.value.count() > 2 && nombreUsuario.value.count() <= 20
    protected fun validarContrasena(): Boolean = contrasenaUsuario.value.isNotBlank() && contrasenaUsuario.value.count{ it.isDigit() } > 0  && contrasenaUsuario.value.count{ !it.isLetterOrDigit() } > 0  && contrasenaUsuario.value.count() > 5 && contrasenaUsuario.value.count() <= 20
    protected fun validarConfirmarContrasena(): Boolean = contrasenaUsuario.value == confirmarContrasenaUsuario.value



    private fun datosIncorrectos(): String
    {
        return if (!validaridTipoUsuario())
            "Por favor, ingrese un tipo de usuario"
        else if(!validarCedula())
            "Cédula inválida, por favor intente de nuevo"
        else if (!validarPrimerNombre())
            "Por favor, ingrese un primer nombre válido"
        else if (!validarSegundoNombre())
            "Por favor, ingrese un segundo nombre válido"
        else if (!validarPrimerApellido())
            "Por favor, ingrese un primer apellido válido"
        else if (!validarSegundoApellido())
            "Por favor, ingrese un segundo apellido válido"
        else if(!validarCorreo())
            "Correo electrónico inválido, por favor intente de nuevo"
        else if(!validarIdCodigoOperadoraTelefono() || !validarExtensionTelefono())
            "Por favor, ingrese un número de teléfono válido"

        else if (idTipoUsuario.value == 2 && !validarIdDepartamento())
            "Por favor, ingrese un departamento"

        else if(!validarIdCargo())
            "Por favor, ingrese un cargo laboral"
        else if(idTipoUsuario.value == 1 && !validarIdGrupoAtencion())
            "Por favor, ingrese el grupo de atención"
        else if(!validarNombreUsuario())
            "Por favor, ingrese un nombre de usuario válido"
        else if(!validarContrasena())
            "Contraseña inválida, por favor intente de nuevo"
        else if(!validarConfirmarContrasena())
            "Las contraseñas no coinciden, por favor intente de nuevo"
        else
            ""

    }



    protected suspend fun crearUsuario(accionTecnico: suspend (Tecnico) -> Unit, accionClienteInterno: suspend (ClienteInterno) -> Unit)
    {

        // Se crea el objeto empleado
        val usuarioEmpleado = Usuario(
            nombre = nombreUsuario.value,
            password = SHA512(contrasenaUsuario.value),
            idTipoUsuario = idTipoUsuario.value
        )

        val telefonoEmpleado = TelefonoEmpleado(
            idCodigoOperadoraTelefono = idCodigoOperadoraTelefono.value,
            extension = extensionTelefono.value
        )

        val nuevoEmpleado = Empleado(
            cedula = cedula.value.toInt(),
            primerNombre = primerNombre.value,
            segundoNombre = segundoNombre.value,
            primerApellido = primerApellido.value,
            segundoApellido = segundoApellido.value,
            nacionalidad = nacionalidad.value,
            correoElectronico = correoElectronico.value.toLowerCasePreservingASCIIRules(),  //Se convierte la cadena de texto en minusculas
            telefonoEmpleado = telefonoEmpleado,
            usuario = usuarioEmpleado,
            idDepartamento = if(idTipoUsuario.value == 1) 6 else idDepartamento.value,
            idCargoEmpleado = idCargo.value
        )

        // Dependiendo del tipo de usuario, se guardan sus datos
        if(idTipoUsuario.value == 1) // T
        {

            val tecnico = Tecnico(
                idGrupoAtencion = idGrupoAtencion.value,
                empleado = nuevoEmpleado
            )

            accionTecnico(tecnico)

        }
        else if(idTipoUsuario.value == 2) //CL
        {
            val clienteInterno = ClienteInterno(
                empleado = nuevoEmpleado
            )

            accionClienteInterno(clienteInterno)

        }

    }

}