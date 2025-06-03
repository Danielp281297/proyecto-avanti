package com.example.avantitigestiondeincidencias.ViewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BusquedaUsuarioViewModel: ViewModel() {

    private val _entradaBusqueda = MutableStateFlow<String>("")
    val entradaBusqueda: StateFlow<String> get() = _entradaBusqueda.asStateFlow()

    private val _listaTecnicos = MutableStateFlow<List<Tecnico>>(emptyList())
    val listaTecnicos: StateFlow<List<Tecnico>> get() = _listaTecnicos.asStateFlow()

    private val _listaClientesInterno = MutableStateFlow<List<ClienteInterno>>(emptyList())
    val listaClientesInterno: StateFlow<List<ClienteInterno>> get() = _listaClientesInterno.asStateFlow()

    private val _filtroBusqueda = MutableStateFlow<Int>(0)
    val filtroBusqueda: StateFlow<Int> get() = _filtroBusqueda.asStateFlow()

    private val _botonTecnico = MutableStateFlow<Boolean>(false)
    val botonTecnico: StateFlow<Boolean> get() = _botonTecnico.asStateFlow()

    private val _botonClienteInterno = MutableStateFlow<Boolean>(false)
    val botonClienteInterno: StateFlow<Boolean> get() = _botonClienteInterno.asStateFlow()

    private val _mostrarFiltrosHabilitado = MutableStateFlow<Boolean>(false)
    val mostrarFiltrosHabilitado: StateFlow<Boolean> get() = _mostrarFiltrosHabilitado.asStateFlow()

    fun setMostrarFiltrosHabilitado(){
        _mostrarFiltrosHabilitado.value = !_mostrarFiltrosHabilitado.value
    }

    fun setEntradaBusqueda(entrada: String) { _entradaBusqueda.value = entrada }
    fun setFiltroBusqueda(valor: Int) { _filtroBusqueda.value = valor }
    fun validarBuscador(): Boolean { return botonTecnico.value && botonClienteInterno.value }

    fun setBotonTecnico()
    {
        _botonTecnico.value = true
        _botonClienteInterno.value = false
    }

    fun setBotonClienteInterno()
    {
        _botonClienteInterno.value = true
        _botonTecnico.value = false
    }

    suspend fun obtenerDatosTecnico()
    {

        EmpleadoRequest().seleccionarTecnicos{
            _listaTecnicos.value = it
        }

    }

    suspend fun obtenerDatosClienteInterno(){

        EmpleadoRequest().seleccionarUsuariosClienteInterno {
            _listaClientesInterno.value = it
        }

    }

    suspend fun seleccionarUsuarioTecnicoPorNombre(){
        EmpleadoRequest().seleccionarUsuariosTecnicosByNombre(entradaBusqueda.value){
            _listaTecnicos.value = it
        }
    }

    suspend fun seleccionarUsuarioTecnicoPorPrimerNombre(){
        EmpleadoRequest().seleccionarTecnicoPorNombreEmpleado(entradaBusqueda.value){
            _listaTecnicos.value = it
        }

    }

    suspend fun seleccionarUsuarioTecnicoPorCedula(){
        EmpleadoRequest().seleccionarTecnicoPorCedula(entradaBusqueda.value){
            _listaTecnicos.value = it
        }
    }

    suspend fun seleccionarUsuarioClienteInternoPorCedula(){

        EmpleadoRequest().seleccionarClienteInternoPorCedula(entradaBusqueda.value){

            _listaClientesInterno.value = it

        }

    }

    suspend fun seleccionarUsuarioClienteInternoPorPrimerNombreEmpleado(){

        EmpleadoRequest().seleccionarClienteInternoPorNombreEmpleado(entradaBusqueda.value){
            _listaClientesInterno.value = it
        }

    }

    suspend fun seleccionarUsuarioClienteInternoPorNombreUsuario()
    {
        EmpleadoRequest().seleccionarUsuariosClientesInternoByNombre(entradaBusqueda.value){
            _listaClientesInterno.value = it
        }
    }

}