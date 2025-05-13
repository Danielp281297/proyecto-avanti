package com.example.avantitigestiondeincidencias.Supabase

import android.util.Log
import androidx.compose.foundation.layout.Column
import com.example.avantitigestiondeincidencias.AVANTI.CargoEmpleado
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Departamento
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.GrupoAtencion
import com.example.avantitigestiondeincidencias.AVANTI.Sede
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.ui.screens.componentes.SHA512
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.Login
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EmpleadoRequest(): SupabaseClient() {

    private val columnaUsuario = Columns.raw("""
        
        id_usuario,
        nombre_usuario,
        id_tipo_usuario
            
    """.trimIndent())

    val columnasTecnico = Columns.raw("""
        id_técnico,
        grupo_atención(
            id_grupo_atención,
            nombre_grupo_atención
        ),
        empleado!inner (
            id_empleado,
            cédula_empleado,
            primer_nombre_empleado,
            segundo_nombre_empleado,
            primer_apellido_empleado,
            segundo_apellido_empleado,
            correo_electrónico_empleado,
            id_usuario,
            id_teléfono_empleado,
            id_departamento,
            id_cargo_empleado,
            teléfono_empleado(
                id_teléfono_empleado,
                extensión_teléfono,
                id_código_operadora_teléfono,
                código_operadora_teléfono(
                    operadora_teléfono
                )
            ),
            departamento(
                nombre_departamento,
                piso_departamento,
                sede(
                    nombre_sede
                )
            ),
            cargo_empleado(
                tipo_cargo_empleado
            )
        )
            
        """.trimIndent())

    val columnasClienteInterno = Columns.raw("""
        id_cliente_interno,
        empleado!inner (
            id_empleado,
            cédula_empleado,
            primer_nombre_empleado,
            segundo_nombre_empleado,
            primer_apellido_empleado,
            segundo_apellido_empleado,
            correo_electrónico_empleado,
            id_usuario,
            id_teléfono_empleado,
            id_departamento,
            id_cargo_empleado,
            teléfono_empleado(
                extensión_teléfono,
                id_código_operadora_teléfono,
                código_operadora_teléfono(
                    operadora_teléfono
                )
            ),
            departamento(
                nombre_departamento,
                piso_departamento,
                sede(
                    nombre_sede
                )
            ),
            cargo_empleado(
                tipo_cargo_empleado
            )
        )
            
        """.trimIndent())

    val columnasDepartamento = Columns.raw("""
        nombre_departamento, 
        piso_departamento,
        sede(
            nombre_sede
        )
    """.trimIndent())

    suspend fun seleccionarTecnicobyUsuarioId(idUsuario: Int): Tecnico {

        var resultado = getSupabaseClient().from("técnico").select(columns = columnasTecnico){
            filter {
                eq("empleado.id_usuario", idUsuario)
            }
        }.decodeSingle<Tecnico>()

        val usuario = getSupabaseClient().from("usuario").select(columnaUsuario) {

            filter {
                eq("id_usuario", resultado.empleado.idUsuario)
            }

        }.decodeSingle<Usuario>()

        resultado.empleado.usuario = usuario

        return resultado
    }

    suspend fun seleccionarClienteInternobyUsuarioId(idUsuario: Int): ClienteInterno {

        val resultado = getSupabaseClient().from("cliente_interno").select(columns = columnasClienteInterno){
            filter {
                eq("empleado.id_usuario", idUsuario)
            }
        }.decodeSingle<ClienteInterno>()

        val usuario = getSupabaseClient().from("usuario").select(columnaUsuario) {

            filter {
                eq("id_usuario", resultado.empleado.idUsuario)
            }

        }.decodeSingle<Usuario>()

        resultado.empleado.usuario = usuario

        return resultado
    }

    suspend fun seleccionarEmpleadoTecnicoById(id: Int): Tecnico {

        var resultado = getSupabaseClient().from("técnico").select(columns = columnasTecnico){

            filter {
                eq("id_técnico", id)
            }

        }.decodeSingle<Tecnico>()

        // Se buscan los datos del usuario
        var usuario = getSupabaseClient().from("usuario").select(columnaUsuario) {

            filter {
                eq("id_usuario", resultado.empleado.idUsuario)
            }

        }.decodeSingle<Usuario>()

        resultado.empleado.usuario = usuario

        return resultado

    }

    suspend fun seleccionarEmpleadoClienteById(id: Int): ClienteInterno {

        var resultado = getSupabaseClient().from("cliente_interno").select(columns = columnasClienteInterno){

            filter {
                eq("id_cliente_interno", id)
            }

        }.decodeSingle<ClienteInterno>()

        var usuario = getSupabaseClient().from("usuario").select(columnaUsuario) {

            filter {
                eq("id_usuario", resultado.empleado.idUsuario)
            }

        }.decodeSingle<Usuario>()

        resultado.empleado.usuario = usuario

        return resultado

    }

    suspend fun seleccionarCargosEmpleado(): List<CargoEmpleado> {
        return getSupabaseClient().from("cargo_empleado").select().decodeList<CargoEmpleado>()
    }

    suspend fun seleccionarGrupoAtencion(): List<GrupoAtencion> {
        return getSupabaseClient().from("grupo_atención").select().decodeList<GrupoAtencion>()
    }

    suspend fun seleccionarDepartamentos(): List<Departamento> {

        return getSupabaseClient().from("departamento")
            .select(columns = columnasDepartamento)
            .decodeList<Departamento>()
    }

    suspend fun obtenerDatosUsuario(usuario: String, pass: String): Usuario? {

        // Se convierte la contrasena en un hash2 de 256 bits
        val contrasena = SHA512(pass)

        var empleado: Usuario? = getSupabaseClient().from("usuario").
            select(columns = Columns.raw("""
                    id_usuario,
                    id_tipo_usuario
            """.trimIndent())){
                filter {
                    eq("nombre_usuario", usuario)
                    eq("contraseña_usuario", contrasena)
                }

            }.decodeSingleOrNull()

        return empleado

    }

    // Metodo para devolver los datos de los tecnicos de forma descendente
    suspend fun seleccionarUsuariosTecnico(function: (List<Tecnico>) -> Unit) {

        val dataset = getSupabaseClient().from("técnico").select(columns = columnasTecnico){

            order("id_técnico", Order.DESCENDING)
            filter {
                gt("id_técnico", 1)
            }

        }.decodeList<Tecnico>()

        function(dataset)

    }

    // Metodo para devolver los datos de los clientes tecnicos de forma descendente
    suspend fun seleccionarUsuariosClienteInterno(function: (List<ClienteInterno>) -> Unit) {

        val dataset = getSupabaseClient().from("cliente_interno").
        select(columns = columnasClienteInterno){
            order("id_cliente_interno", Order.DESCENDING)
            filter {
                gt("id_cliente_interno", 1)
            }
        }.decodeList<ClienteInterno>()

        function(dataset)

    }

    suspend fun seleccionarClienteInternoById(id:Int): ClienteInterno {
        return getSupabaseClient().from("cliente_interno").select(columns = columnasClienteInterno){
            filter {
                eq("id_cliente_interno", id)
            }
        }.decodeSingle<ClienteInterno>()
    }

    suspend fun seleccionarTecnicoById(id:Int): Tecnico {
        return getSupabaseClient().from("técnico").select(columns = columnasTecnico){
            filter {
                eq("id_técnico", id)
            }
        }.decodeSingle<Tecnico>()
    }

    suspend fun seleccionarClienteById(id: Int): ClienteInterno {

        return getSupabaseClient().from("cliente_interno").select(columns = columnasClienteInterno){

            filter {
                eq("id_empleado", id)
            }

        }.decodeSingle<ClienteInterno>()

    }

    suspend fun buscarCorreoElectronico(correo: String, salida: (Empleado?) -> Unit)
    {
        val resultado = getSupabaseClient().from("empleado").
        select(columns = Columns.raw("correo_electrónico_empleado")){
            filter {
                eq("correo_electrónico_empleado", correo)
            }
        }.decodeSingleOrNull<Empleado>()

        salida(resultado)

    }

    suspend fun buscarCedula(cedula: String, salida: (Empleado?) -> Unit)
    {
        val resultado = getSupabaseClient().from("empleado").
        select(Columns.raw("cédula_empleado")){
            filter {
                eq("cédula_empleado", cedula)
            }
        }.decodeSingleOrNull<Empleado>()

        salida(resultado)

    }

    suspend fun buscarTecnicoByNombreUsuario(nombre: String, tecnicos: (List<Tecnico>) -> Unit)
    {

        var resultados = getSupabaseClient().from("usuario").select(columnasTecnico){
            filter {
                like("nombre_usuario", "%${nombre}%")
            }
        }.decodeList<Tecnico>()

        tecnicos(resultados)

    }

    suspend fun buscarClienteByNombreUsuario(nombre: String, clientes: (List<ClienteInterno>) -> Unit)
    {

        val resultados = getSupabaseClient().from("cliente_interno").select(columnasClienteInterno){
            filter {
                like("empleado.usuario.nombre_usuario", "%${nombre}%")
            }
        }.decodeList<ClienteInterno>()

        clientes(resultados)

    }

    suspend fun seleccionarUsuariosTecnicosByNombre(nombre: String, tecnicos: (List<Tecnico>) -> Unit)
    {
        val resultados = getSupabaseClient().from("usuario").select(columnaUsuario){
            filter{
                //and {
                like("nombre_usuario", "%${nombre}%")
                eq("id_tipo_usuario", 1)
                //}
            }
        }.decodeList<Usuario>()

        var tecnicoLista = mutableListOf<Tecnico>()

        resultados.forEach{
            //Log.d("USUARIO", it.toString())
            var tecnico = getSupabaseClient().from("técnico").select(columnasTecnico){
                filter{
                    eq("empleado.id_usuario", it.id)
                }
            }.decodeSingle<Tecnico>()
            tecnico.empleado.usuario = it
            tecnicoLista.add(tecnico)
            //Log.d("TECNICO", tecnico.toString())
        }

        tecnicos(tecnicoLista as List<Tecnico>)

    }

    suspend fun seleccionarUsuariosClientesInternoByNombre(nombre: String, tecnicos: (List<ClienteInterno>) -> Unit)
    {
        val resultados = getSupabaseClient().from("usuario").select(columnaUsuario){
            filter{
                //and {
                like("nombre_usuario", "%${nombre}%")
                eq("id_tipo_usuario", 2)
                //}
            }
        }.decodeList<Usuario>()

        var clienteLista = mutableListOf<ClienteInterno>()

        resultados.forEach{
            var cliente = getSupabaseClient().from("cliente_interno").select(columnasClienteInterno){
                filter{
                    eq("empleado.id_usuario", it.id)
                }
            }.decodeSingle<ClienteInterno>()

            cliente.empleado.usuario = it
            clienteLista.add(cliente)
        }

        tecnicos(clienteLista as List<ClienteInterno>)

    }

    suspend fun actualizarDatosClienteInterno(anterior: ClienteInterno, nuevo: ClienteInterno)
    {

        actualizarDatosEmpleado(anterior.empleado, nuevo.empleado)
        getSupabaseClient().from("empleado").update ({
            if(anterior.empleado.idDepartamento != nuevo.empleado.idDepartamento)
            {
                set("id_departamento", nuevo.empleado.idDepartamento)
            }
        }){
            filter{
                eq("id_empleado", anterior.idEmpleado)
            }
        }

    }

    suspend fun actualizarDatosTecnico(empleado: Empleado, nuevo: Tecnico)
    {

        var anterior = getSupabaseClient().from("técnico").select(columnasTecnico){
            filter{
                eq("id_empleado", empleado.id)
            }
        }.decodeSingle<Tecnico>()

        val usuarioAnterior = getSupabaseClient().from("usuario").select(){
            filter{
                eq("id_usuario", empleado.idUsuario)
            }
        }.decodeSingle<Usuario>()

        anterior.empleado.usuario = usuarioAnterior

        getSupabaseClient().from("técnico").update({
            if (anterior.idGrupoAtencion != nuevo.idGrupoAtencion)
            {
                set("id_grupo_atención", nuevo.idGrupoAtencion)
            }
        }){
            filter {
                eq("id_técnico", anterior.id)
            }
        }

        actualizarDatosEmpleado(anterior.empleado, nuevo.empleado)

    }


    suspend fun actualizarDatosEmpleado(anterior: Empleado, nuevo: Empleado)
    {

        Log.d("ANTERIOR", anterior.toString())
        Log.d("NUEVO", nuevo.toString())

        getSupabaseClient().from("empleado").update(update = {

            if (anterior.cedula != nuevo.cedula)
                set("cédula_empleado", nuevo.cedula)


            if (anterior.primerNombre != nuevo.primerNombre)
                set("primer_nombre_empleado", nuevo.primerNombre)

            if (anterior.segundoNombre != nuevo.segundoNombre)
                set("segundo_nombre_empleado", nuevo.segundoNombre)


            if (anterior.primerApellido != nuevo.primerApellido)
                set("primer_apellido_empleado", nuevo.primerApellido)

            if (anterior.segundoApellido != nuevo.segundoApellido)
                set("segundo_apellido_empleado", nuevo.segundoApellido)


            if (anterior.correoElectronico != nuevo.correoElectronico)
                set("correo_electrónico_empleado", nuevo.correoElectronico)

            // Si el usuario es cliente interno, se actualiza el departamento
            if (anterior.usuario.idTipoUsuario == 2 && (anterior.idDepartamento != nuevo.idDepartamento))
                set("id_departamento", nuevo.idDepartamento)

            if (anterior.idCargoEmpleado != nuevo.idCargoEmpleado)
                   set("id_cargo_empleado", nuevo.idCargoEmpleado)


        }){
            filter {
                eq("id_empleado", nuevo.id)
            }
        }

        getSupabaseClient().from("usuario").update({

            if(anterior.usuario.nombre != nuevo.usuario.nombre)
            {
                set("nombre_usuario", nuevo.usuario.nombre)
            }

        }){filter {
            eq("id_usuario", anterior.idUsuario)
        }}


        getSupabaseClient().from("teléfono_empleado").update({
            if (anterior.telefonoEmpleado.codigoOperadoraTelefono != nuevo.telefonoEmpleado.codigoOperadoraTelefono)
                set("id_código_operadora_teléfono", nuevo.telefonoEmpleado.idCodigoOperadoraTelefono)


            if (anterior.telefonoEmpleado.extension != nuevo.telefonoEmpleado.extension)
                set("extensión_teléfono", nuevo.telefonoEmpleado.extension)


        }){
            filter {
                eq("id_teléfono_empleado", anterior.idTeléfonoEmpleado)
            }
        }

    }

    suspend fun buscarEmpleadoByCedula(cedula: Int, empleado: (Empleado?) -> Unit)
    {
        // Se obtiene los datos del empleado con respecto a su cedula de identidad
        val resultado = getSupabaseClient().
        from("empleado").select(){
            filter {
                eq("cédula_empleado", cedula)
            }
        }
            .decodeSingleOrNull<Empleado>()

        empleado(resultado)

    }

    suspend fun buscarEmpleadoByCorreoElectronico(correo: String, empleado: (Empleado?) -> Unit){
        val resultado = getSupabaseClient().
        from("empleado").select(){
            filter {
                eq("correo_electrónico_empleado", correo)
            }
        }
            .decodeSingleOrNull<Empleado>()

        empleado(resultado)
    }

    suspend fun seleccionarIdGrupoAtencionByEmpleadoId(id: Int, idDepartamento: (Int) -> Unit)
    {
        val resultado = getSupabaseClient().from("técnico").select(){
            filter{
                eq("id_empleado", id)
            }
        }.decodeSingle<Tecnico>()

        idDepartamento(resultado.idGrupoAtencion)

    }

    suspend fun seleccionarTecnicoPorNombreEmpleado(entrada: String, tecnicos: (List<Tecnico>) -> Unit)
    {
        var resultados = getSupabaseClient().from("técnico").select(columnasTecnico) {
            filter {
                // El operador ilike permite buscar patrones de cadenas de caracteres sin importar si esta en mayusculas
                gt("id_técnico", 1)
                ilike("empleado.primer_nombre_empleado", "%$entrada%")
            }
        }.decodeList<Tecnico>()
        resultados.forEach { tecnico ->
            UsuarioRequest().seleccionarUsuarioById(tecnico.empleado.idUsuario){
                tecnico.empleado.usuario = it
            }
        }

        tecnicos(resultados)

    }

    suspend fun seleccionarClienteInternoPorNombreEmpleado(entrada: String, tecnicos: (List<ClienteInterno>) -> Unit)
    {
        var resultados = getSupabaseClient().from("cliente_interno").select(columnasClienteInterno) {
            filter {
                // El operador ilike permite buscar patrones de cadenas de caracteres sin importar si esta en mayusculas
                gt("id_cliente_interno", 1)
                ilike("empleado.primer_nombre_empleado", "%$entrada%")
            }
        }.decodeList<ClienteInterno>()
        resultados.forEach { cliente ->
            UsuarioRequest().seleccionarUsuarioById(cliente.empleado.idUsuario){
                cliente.empleado.usuario = it
            }
        }

        tecnicos(resultados)

    }

    suspend fun seleccionarTecnicoPorCedula(cedula: String, tecnicos: (List<Tecnico>) -> Unit)
    {
        var resultados = getSupabaseClient().from("técnico").select(columnasTecnico) {
            filter {
                // El operador ilike permite buscar patrones de cadenas de caracteres sin importar si esta en mayusculas
                gt("id_técnico", 1)
                eq("empleado.cédula_empleado", cedula)
            }
        }.decodeList<Tecnico>()
        resultados.forEach { tecnico ->
            UsuarioRequest().seleccionarUsuarioById(tecnico.empleado.idUsuario){
                tecnico.empleado.usuario = it
            }
        }

        tecnicos(resultados)

    }

    suspend fun seleccionarClienteInternoPorCedula(cedula: String, tecnicos: (List<ClienteInterno>) -> Unit)
    {
        var resultados = getSupabaseClient().from("cliente_interno").select(columnasClienteInterno) {
            filter {
                // El operador ilike permite buscar patrones de cadenas de caracteres sin importar si esta en mayusculas
                gt("id_cliente_interno", 1)
                eq("empleado.cédula_empleado", cedula)
            }
        }.decodeList<ClienteInterno>()
        resultados.forEach { cliente ->
            UsuarioRequest().seleccionarUsuarioById(cliente.empleado.idUsuario){
                cliente.empleado.usuario = it
            }
        }

        tecnicos(resultados)

    }

}