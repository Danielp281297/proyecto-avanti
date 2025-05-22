package com.example.avantitigestiondeincidencias.ui.screens.manuales.ClienteInterno

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.manuales.DescripcionPantallaLogin
import com.example.avantitigestiondeincidencias.ui.screens.manuales.ImagenManual
import com.example.avantitigestiondeincidencias.ui.screens.manuales.PantallaIntroduccion
import com.example.avantitigestiondeincidencias.ui.screens.manuales.Presentacion
import com.example.avantitigestiondeincidencias.ui.screens.manuales.TextoManual
import com.example.avantitigestiondeincidencias.ui.screens.manuales.TituloManual
import kotlinx.coroutines.launch

@Composable
fun ManualClienteInterno(
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
) {

    var scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 15 })

    ScaffoldSimplePersonalizado(
        tituloPantalla = "Manual de Avanti - Cliente",
        containerColor = containerColor
    ) {

        VerticalPager(
            modifier = Modifier,
            state = pagerState
        ) { page ->

            // depende del resultado del los lambdas, se redirige a una pagina en especifico
            when (page) {
                0 -> Presentacion()
                1 -> IndiceClienteInterno{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }

                }
                2 -> PantallaIntroduccion()
                3 -> DescripcionPantalla()
                4 -> DescripcionPantallaLogin {
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                5 -> DescripcionPantallaInicioClienteInterno{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                6 -> DescripcionPantallaNuevoTicket{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                7 -> DescripcionPantallaInformacionTicket{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                8 -> DescripcionPantallaCalificacionCliente{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                9 -> DescripcionPantallaMenuLateral{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                10 -> DescripcionPantallaPerfil(R.drawable.perfil_cliente_interno){
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                11 -> DescripcionPantallaEditarPerfil{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                12 -> DescripcionPantallaCambiarContrasena{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                13 -> OperatividadPantalla()
                14 -> OperatividadClienteInterno{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                else -> {}
            }

        }
    }
}

@Composable
fun OperatividadClienteInterno(numPantalla: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Operatividad")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual(
            "Abrir un ticket \n" +
                    "Al momento de presentar una incidencia, el usuario puede abrir un ticket en la pantalla nuevo ticket. En esta pantalla, el usuario detalla en la entrada “Descripción”, la situación presentada.\n" +
                    "\n" +
                    "Al momento de crear el ticket, este aparecerá en la pantalla de inicio. \n" +
                    "\n" +
                    "Seguimiento del ticket\n" +
                    "El usuario podrá tener información acerca del técnico asignado para resolver la situación establecida en el ticket. Una vez asignado, el llegara una notificación acerca del ticket, y el nombre del técnico asignado\n"
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Box(modifier = Modifier.weight(1F)) {
                ImagenManual(R.drawable.notificacion_ticket_asignado)
            }
            Box(modifier = Modifier.weight(1F)) {
                ImagenManual(R.drawable.barra_notificacion_ticket_asignado)
            }
       }
        TextoManual(
            "\n" +
                    "Cierre del ticket\n" +
                    "Una vez el técnico haya diagnosticado y resuelto la situación, este registrara la acción y las observaciones del ticket que fueron usadas para resolver el mismo. Al hacer esto, al usuario le llegara una notificación mostrando que la misma ha sido resuelta, y se le pide que cierre el ticket.\n"
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Box(modifier = Modifier.weight(1F)) {
                ImagenManual(R.drawable.notificacion_ticket_resuelto)
            }
            Box(modifier = Modifier.weight(1F)) {
                ImagenManual(R.drawable.barra_notificaciones_ticket_resuelto)
            }
        }
        TextoManual(
            "A partir de aquí, el usuario debe cerrar el ticket. Esto se consigue en la pantalla información del ticket, en que se encuentra el botón “CERRAR TICKET”. Una vez presionado, se le pide al usuario que ingrese la calificación por la gestión de la incidencia por parte del técnico asignado.\n" +
                    " Una vez hecho esto, se cierra el ticket.\n"
        )

    }
}

@Composable
fun DescripcionPantallaCambiarContrasena(numPantalla: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de cambiar contraseña")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("La pantalla para editar la contraseña es la siguiente:\n")
        ImagenManual(R.drawable.cambiar_contrasena)
        TextoManual(
            "Esta pantalla tiene los siguientes elementos:\n" +
                    "\t• Una entrada de texto solo para lectura mostrando el nombre de usuario.\n" +
                    "\t• Una entrada de texto para ingresar la contraseña.\n" +
                    "\t• Una entrada de texto para ingresar la confirmación de la contraseña.\n" +
                    "\n" +
                    "Es obligatorio que ambas contraseñas coincidan y que cumplan con los requisitos de seguridad para poder realizar el cambio.\n"
        )

    }
}

@Composable
fun DescripcionPantallaEditarPerfil(numPantalla: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de editar perfil")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Para poder editar los datos personales, de contacto, y el nombre del usuario se ingresa a la siguiente pantalla:\n")
        ImagenManual(R.drawable.editar_perfil)
        TextoManual(
            "En esta pantalla se muestran los datos del usuario, y este puede cambiarlos cuando lo considere pertinente, En esta pantalla, el usuario puede editar sus:\n" +
                    "\n" +
                    "\t• Datos personales\n" +
                    "\t• Datos de contacto\n" +
                    "\t• El nombre del usuario\n" +
                    "\\n" +
                    "Cada entrada, tanto de texto como numérico debe de tener sus propias restricciones para evitar datos inválidos al poder editar el perfil del usuario\n"
        )

    }
}

@Composable
fun DescripcionPantallaPerfil(Pantalla: Int, numPantalla: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de perfil")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Al ingresar a los datos del perfil, se mostrará la siguiente pantalla:\n")
        ImagenManual(Pantalla)
        TextoManual(
            "En esta pantalla se visualiza los siguientes elementos:\n" +
                    "\t• Datos personales: Cedula y nombre completo del usuario.\n" +
                    "\t• Datos de contacto: Número de teléfono y correo electrónico del usuario.\n" +
                    "\t• Datos laborales: Piso, departamento y sede donde trabaja el usuario.\n" +
                    "\t• El nombre del usuario.\n" +
                    "\t• Botón de editar perfil: Botón para cambiar a la pantalla de editar perfil.\n" +
                    "\t• Botón de cambiar contraseña: Botón para cambiar a la pantalla de cambiar contraseña. \n" +
                    "\n"
        )

    }
}

@Composable
fun DescripcionPantallaMenuLateral(numPantalla: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Menú Lateral")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("En este menú, se visualizan las opciones relacionadas al usuario:\n")
        ImagenManual(R.drawable.menu_lateral)
        TextoManual(
            "Esta pantalla está compuesta por los siguientes elementos: \n" +
                    "\n" +
                    "\t• Encabezado: Se muestran el nombre completo del usuario.\n" +
                    "\t• Botón de perfil: Se muestra la pantalla de perfil.\n" +
                    "\t• Botón de manual de usuario: Se muestra el manual de usuario.\n" +
                    "\t• Botón de cerra sesión: Se pregunta al usuario si desea cerrar la sesión. De ser afirmativo, se cierra la sesión del usuario, y se regresa a la pantalla de ingreso de sesión.\n"
        )


    }
}

@Composable
fun DescripcionPantallaCalificacionCliente(numPantalla: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de calificación de ticket.")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Al presionar el botón “CERRAR TICKET”, se muestra una pantalla para calificar la gestión del técnico:\n")
        ImagenManual(R.drawable.nuevo_ticket)
        TextoManual(
            "Esta pantalla presenta una barra horizontal delimitada para valorar de forma numérica la gestión de la incidencia y así, poder cerrar el ticket.\n" +
                    "\n" +
                    "Este es un requisito obligatorio y la misma se cierra al oprimir el botón “aceptar”.\n"
        )

    }
}

@Composable
fun DescripcionPantallaInformacionTicket(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Información del ticket")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Al presionar sobre un ticket, el usuario puede ver la siguiente pantalla:\n")
        ImagenManual(R.drawable.informacion_ticket)
        TextoManual(
            "Esta pantalla tiene los siguientes elementos:\n" +
                    "\n" +
                    "\t• Los datos referentes al ticket: El número, la fecha y la hora; el tipo y el estado del ticket.\n" +
                    "\t• Los datos del cliente interno: Su nombre completo, número de teléfono, correo electrónico, piso, departamento y sede donde se trabaja. \n" +
                    "\t• Si la situación del ticket fue resuelta, se visualiza la fecha y la hora de la asignación; el nombre del técnico encargado, la acción ejecutada y las observaciones del mismo. \n" +
                    "\t• Si el ticket no ha sido asignado, se muestra un botón para cancelar el ticket.\n" +
                    "\t• Si el ticket fue resuelto, se muestra un botón para cerrar el ticket.\n" +
                    "\n" +
                    "El botón “cancelar ticket” es una opción habilitada para el cliente en el momento en que no considere necesario la gestión de la incidencia.\n" +
                    "\n" +
                    "Es obligatorio que el cliente cierre el ticket para terminar con la finalizar la gestión por parte del equipo técnico de soporte.\n"
        )

    }
}

@Composable
fun DescripcionPantallaNuevoTicket(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de formulario de nuevo ticket")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Para poder crear un nuevo ticket, el usuario tiene la siguiente pantalla:\n")
        ImagenManual(R.drawable.nuevo_ticket)
        TextoManual(
            "Esta pantalla presenta los siguientes elementos\n" +
                    "\t• Una lista desplegable para indicar el tipo de ticket, en el que la primera opción es “incidencia”.\n" +
                    "\t• Una entrada de texto que permite la descripción por parte del cliente para el ticket.\n" +
                    "\t• El botón para abrir el ticket.\n" +
                    "\n" +
                    "Es obligatorio que el usuario ingrese la descripción de la situación. \n" +
                    "\n" +
                    "Entre los diferentes estados de ticket están:\n" +
                    "\n" +
                    "\n" +
                    "\t• Incidencia: una interrupción no planificada de un servicio de TI o a una reducción en la calidad del mismo. Ejemplos incluyen la imposibilidad de un usuario para acceder a un sistema o la caída de una aplicación crítica.\n" +
                    "\t• Solicitud: No implica una interrupción del servicio, sino una petición formal realizada por un usuario para obtener información, asesoramiento, acceso a un servicio.\n" +
                    "\t• Mantenimiento: Tareas preventivas o correctivas para asegurar el buen funcionamiento de los sistemas y evitar incidentes futuros.\n" +
                    "\t• Control de cambio: Incluye la evaluación, aprobación, planificación y ejecución de cambios o mudanzas de equipos de manera controlada.  \n" +
                    "\n" +
                    "En la descripción del ticket, se detalla la situación o la solicitud que se necesita realizar.\n"
        )

    }
}

@Composable
fun DescripcionPantallaInicioClienteInterno(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Inicio de Cliente Interno")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Al iniciar sesión, se presenta la siguiente pantalla. \n")
        ImagenManual(R.drawable.inicio_cliente)
        TextoManual(
            "Esta pantalla presenta los siguientes elementos:\n" +
                    "\n" +
                    "\t• Lista de últimos tickets: Se muestra una lista ordenada de forma descendente de los tickets emitidos por el usuario.\n" +
                    "\t• Botón del nuevo ticket: El botón flotante ubicado en la parte inferior derecha de la pantalla, que muestra un formulario para abrir un nuevo ticket. \n" +
                    "\t• Botón del menú lateral: El botón ubicado en la esquina superior izquierda de la pantalla, que muestra la vista del menú lateral del usuario.\n" +
                    "\n" +
                    "En esta pantalla se mostrarán los tickets que han creado el usuario de forma descendente." +
                    " \n" +
                    "En la lista de tickets, se muestra los siguientes datos:\n" +
                    "\t• El tipo de ticket\n" +
                    "\t• La descripción del mismo\n" +
                    "\t• Si es un ticket asignado, se muestra el nombre completo del técnico.\n" +
                    "\t• La fecha de abertura del ticket.\n" +
                    "Cuando el ticket es asignado por el administrado, el estado se actualiza “Pendiente” en tiempo real.\n" +
                    " Cuando la situación es resuelta por el técnico, el estado del ticket se actualiza en “Resuelto” en tiempo real.\n"
        )


    }

}

@Composable
fun DescripcionPantalla() {
    Column(modifier = Modifier.padding(15.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        TituloManual("DESCRIPCIÓN DE PANTALLAS")
    }
}

@Composable
fun OperatividadPantalla() {
    Column(modifier = Modifier.padding(15.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        TituloManual("OPERATIVIDAD DE GESTIÓN DE INCIDENCIAS")
    }
}

@Composable
fun IndiceClienteInterno(numPagina: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Índice")

        TextoManual("Presentación", modifier = Modifier.clickable { numPagina(0) })
        TextoManual("Índice", modifier = Modifier.clickable { numPagina(1) })
        TextoManual("Introducción", modifier = Modifier.clickable { numPagina(2) })
        TextoManual("DESCRIPCIÓN DE PANTALLAS", modifier = Modifier.clickable { numPagina(3) })
        TextoManual("\t Pantalla Login", modifier = Modifier.clickable { numPagina(4) })
        TextoManual("\t Pantalla de Inicio de Cliente Interno", modifier = Modifier.clickable { numPagina(5) })
        TextoManual("\t Pantalla de Nuevo Ticket", modifier = Modifier.clickable { numPagina(6) })
        TextoManual("\t Pantalla de información del ticket", modifier = Modifier.clickable { numPagina(7) })
        TextoManual("\t Pantalla de calificación de ticket.", modifier = Modifier.clickable { numPagina(8) })
        TextoManual("\t Pantalla de Menú Lateral", modifier = Modifier.clickable { numPagina(9) })
        TextoManual("\t Pantalla de perfil", modifier = Modifier.clickable { numPagina(10) })
        TextoManual("\t Pantalla de editar perfil", modifier = Modifier.clickable { numPagina(11) })
        TextoManual("\t Pantalla de editar contraseña", modifier = Modifier.clickable { numPagina(12) })
        TextoManual("OPERATIVIDAD DE GESTION DE INCIDENCIAS", modifier = Modifier.clickable { numPagina(13) })

    }
}


