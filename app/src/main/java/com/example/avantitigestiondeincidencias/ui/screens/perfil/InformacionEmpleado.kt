package com.example.avantitigestiondeincidencias.ui.screens.perfil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.ui.screens.ticket.fuenteLetraTicketDesplegado
import io.ktor.util.toUpperCasePreservingASCIIRules

@Composable
fun informacionPersonalEmpleado(empleado: Empleado)
{
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(text = "CÉDULA: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = "${empleado.cedula}",
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "PRIMER NOMBRE: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = empleado.primerNombre.toUpperCasePreservingASCIIRules(),
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado,
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "SEGUNDO NOMBRE: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = empleado.segundoNombre.toUpperCasePreservingASCIIRules(),
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "PRIMER APELLIDO: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = empleado.primerApellido.toUpperCasePreservingASCIIRules(),
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "SEGUNDO APELLIDO: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = empleado.segundoApellido.toUpperCasePreservingASCIIRules(),
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "CORREO ELECTRÓNICO: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = empleado.correoElectronico,
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "TELÉFONO: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = "${empleado.telefonoEmpleado.codigoOperadoraTelefono.operadora}-${empleado.telefonoEmpleado.extension}" ,
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

    }
}

@Composable
fun InformacionLaboralEmpleado(empleado: Empleado)
{
    Column(modifier = Modifier.fillMaxWidth())
    {

        Text(text = "CARGO: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = empleado.cargoEmpleado.tipoCargo.toUpperCasePreservingASCIIRules(),
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "DEPARTAMENTO: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = "${empleado.departamento.piso} - ${empleado.departamento.nombre.toUpperCasePreservingASCIIRules()}",
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "SEDE: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = empleado.departamento.sede.nombre,
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "USUARIO: ", fontSize = fuenteLetraTicketDesplegado)
        Text(
            text = empleado.usuario.nombre,
            fontWeight = FontWeight.Bold,
            fontSize = fuenteLetraTicketDesplegado
        )
        Spacer(modifier = Modifier.padding(5.dp))

    }
}