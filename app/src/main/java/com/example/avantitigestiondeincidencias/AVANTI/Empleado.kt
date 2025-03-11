package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Empleado(
    @SerialName("id_empleado")
    val id: Int = 0,
    @SerialName("cédula_empleado")
    val cedula: Int = 0,
    @SerialName("primer_nombre_empleado")
    val primerNombre: String = " ",
    @SerialName("segundo_nombre_empleado")
    val segundoNombre: String = " ",
    @SerialName("primer_apellido_empleado")
    val primerApellido: String = " ",
    @SerialName("segundo_apellido_empleado")
    val segundoApellido: String = " ",
    @SerialName("correo_electrónico_empleado")
    val correoElectronico: String = " ",
    @SerialName("teléfono_empleado")
    val telefonoPersona: TelefonoPersona = TelefonoPersona(),
    @SerialName("departamento")
    val departamento: Departamento = Departamento(),
    @SerialName("cargo_empleado")
    val cargoEmpleado: CargoEmpleado = CargoEmpleado(),
    @SerialName("usuario")
    val usuario: Usuario = Usuario()
)
