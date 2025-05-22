package com.example.avantitigestiondeincidencias.ui.screens.manuales.Tecnico

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.manuales.Administrador.DescripcionPantallaBusquedaTicket
import com.example.avantitigestiondeincidencias.ui.screens.manuales.Administrador.Informe
import com.example.avantitigestiondeincidencias.ui.screens.manuales.ClienteInterno.DescripcionPantalla
import com.example.avantitigestiondeincidencias.ui.screens.manuales.ClienteInterno.DescripcionPantallaCambiarContrasena
import com.example.avantitigestiondeincidencias.ui.screens.manuales.ClienteInterno.DescripcionPantallaEditarPerfil
import com.example.avantitigestiondeincidencias.ui.screens.manuales.ClienteInterno.DescripcionPantallaMenuLateral
import com.example.avantitigestiondeincidencias.ui.screens.manuales.ClienteInterno.DescripcionPantallaPerfil
import com.example.avantitigestiondeincidencias.ui.screens.manuales.ClienteInterno.OperatividadPantalla
import com.example.avantitigestiondeincidencias.ui.screens.manuales.DescripcionPantallaLogin
import com.example.avantitigestiondeincidencias.ui.screens.manuales.ImagenManual
import com.example.avantitigestiondeincidencias.ui.screens.manuales.PantallaIntroduccion
import com.example.avantitigestiondeincidencias.ui.screens.manuales.Presentacion
import com.example.avantitigestiondeincidencias.ui.screens.manuales.TextoManual
import com.example.avantitigestiondeincidencias.ui.screens.manuales.TituloManual
import kotlinx.coroutines.launch

@Composable
fun ManualTecnico(
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
) {

    var scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 17 })

    ScaffoldSimplePersonalizado(
        tituloPantalla = "Manual de Avanti - Técnico",
        containerColor = containerColor
    ) {

        VerticalPager(
            modifier = Modifier,
            state = pagerState
        ) { page ->

            // depende del resultado del los lambdas, se redirige a una pagina en especifico
            when (page) {
                0 -> Presentacion()
                1 -> IndiceTecnico{
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
                5 -> DescripcionPantallaTecnico{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                6 -> DescripcionPantallaInformacionTicketTecnico { scope.launch {
                    pagerState.scrollToPage(it)
                } }
                7 -> DescripcionPantallaBusquedaTicket{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                8 -> DescripcionPantallaInforme{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                9 -> DescripcionPantallaMenuLateral{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                10 -> DescripcionPantallaPerfil(R.drawable.perfil_tecnico){
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
                14 -> OperatividadTecnico{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }//Operatividad tecnico
                15 -> Informe()
                16 -> InformeAccionesTecnicoIncidenciasResueltas {}// Informe del tecnico
                else -> {}
            }

        }
    }
}

@Composable
fun IndiceTecnico(numPagina: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Índice")

        TextoManual("Presentación", modifier = Modifier.clickable { numPagina(0) })
        TextoManual("Índice", modifier = Modifier.clickable { numPagina(1) })
        TextoManual("Introducción", modifier = Modifier.clickable { numPagina(2) })
        TextoManual("DESCRIPCIÓN DE PANTALLAS", modifier = Modifier.clickable { numPagina(3) })
        TextoManual("\t Pantalla Login", modifier = Modifier.clickable { numPagina(4) })
        TextoManual("\t Pantalla de Inicio de técnico", modifier = Modifier.clickable { numPagina(5) })
        TextoManual("\t Pantalla de Información del ticket", modifier = Modifier.clickable { numPagina(6) })
        TextoManual("\t Pantalla de Buscar ticket.", modifier = Modifier.clickable { numPagina(7) })
        TextoManual("\t Pantalla de Informe.", modifier = Modifier.clickable { numPagina(8) })
        TextoManual("\t Pantalla de Menú Lateral", modifier = Modifier.clickable { numPagina(9) })
        TextoManual("\t Pantalla de perfil", modifier = Modifier.clickable { numPagina(10) })
        TextoManual("\t Pantalla de editar perfil", modifier = Modifier.clickable { numPagina(11) })
        TextoManual("\t Pantalla de editar contraseña", modifier = Modifier.clickable { numPagina(12) })
        TextoManual("OPERATIVIDAD DE GESTION DE INCIDENCIAS", modifier = Modifier.clickable { numPagina(13) })
        TextoManual("\t Operatividad", modifier = Modifier.clickable { numPagina(14) })
        TextoManual("INFORME", modifier = Modifier.clickable { numPagina(15) })
        TextoManual("\tInforme de incidencias resueltas", modifier = Modifier.clickable { numPagina(16) })
        Spacer(modifier = Modifier.padding(30.dp))

    }
}

@Composable
fun InformeAccionesTecnicoIncidenciasResueltas(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Informe de incidencias resueltas")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Este informe es generado al presionar el botón “generar informe” en la pantalla de informe:\n")
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .border(1.dp, if(!isSystemInDarkTheme()) Color.Black else Color.Transparent, RectangleShape),
            painter = painterResource(R.drawable.formato_informe_tecnico),
            contentDescription = "Pantalla Login"
        )
        TextoManual("Este informe es un archivo Excel formato .xlsx, que muestra los siguientes elementos:\n" +
                "\t• Una tabla que muestras los tickets resueltos y cerrados con las siguientes columnas:\n" +
                "\t\to El número de ticket\n" +
                "\t\to El nombre completo del cliente interno\n" +
                "\t\to El nombre de la sede\n" +
                "\t\to El piso\n" +
                "\t\to El departamento\n" +
                "\t\to El tipo de ticket\n" +
                "\t\to La descripción del ticket\n" +
                "\t\to La fecha de abertura\n" +
                "\t\to La hora de abertura\n" +
                "\t\to La acción ejecutada \n" +
                "\t\to La fecha de la acción\n" +
                "\t\to La hora de la acción\n" +
                "\t\to El estado del ticket\n" +
                "\t\to El grupo de atención del técnico\n" +
                "\t\to El nombre completo del técnico\n" +
                "\t\to Las observaciones del ticket\n" +
                "Este formato siguiendo el formato de informe generados por el coordinador de infraestructura.\n")

    }
}

@Composable
fun OperatividadTecnico(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Operatividad")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("La gestión de incidencias empieza cuando el administrador asigna un ticket a un técnico, y este lo ve reflejado en la pantalla de inicio.\n" +
                "Al pulsar en un ticket, aparece la pantalla de información del ticket, en donde el usuario podrá indicar la acción ejecutada para resolver la incidencia, y las observaciones con respecto al ticket, o con respecto a la incidencia.\n" +
                "Al resolver la incidencia, esto actualiza los datos del ticket, en que el cliente interno se le indica que paso final de cierre de ticket para terminar con la gestión de incidencia. \n")

    }
}

@Composable
fun DescripcionPantallaInforme(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Informe")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("La tercera pantalla del conjunto es el informe:\n")
        ImagenManual(R.drawable.informe_tecnico)
        TextoManual("Pantalla conformada por los siguientes elementos:\n" +
                "\t• Entrada de fecha inicial de la búsqueda\n" +
                "\t• Entrada de fecha final de la búsqueda\n" +
                "\t• Botón “CARGAR DATOS” que busca las incidencias resueltas asociados al analista técnico\n" +
                "\t• Caja de texto que muestra la información de los tickets \n" +
                "\t• Botón “GUARDAR INFORME” que guarda los datos de la incidencia en los documentos del dispositivo\n" +
                "La entrada inicial y final son obligatorias para generar el informe\n" +
                "El contenido de la caja de texto está compuesto por líneas conformadas por los siguientes campos:\n" +
                "\t\to El número de ticket\n" +
                "\t\to El nombre completo del cliente interno\n" +
                "\t\to El nombre de la sede\n" +
                "\t\to El piso\n" +
                "\t\to El departamento\n" +
                "\t\to El tipo de ticket\n" +
                "\t\to La descripción del ticket\n" +
                "\t\to La fecha de abertura\n" +
                "\t\to La hora de abertura\n" +
                "\t\to La acción ejecutada \n" +
                "\t\to La fecha de la acción\n" +
                "\t\to La hora de la acción\n" +
                "\t\to El estado del ticket\n" +
                "\t\to El grupo de atención del técnico\n" +
                "\t\to El nombre completo del técnico\n" +
                "\t\to Las observaciones del ticket\n")

    }
}

@Composable
fun DescripcionPantallaInformacionTicketTecnico(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Información del ticket")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Al presionar sobre un ticket, el usuario puede ver la siguiente pantalla:\n")
        ImagenManual(R.drawable.informacion_ticket_tecnico)
        TextoManual("Al pulsar en un ticket, este pasa a la pantalla que muestra la información de referente al ticket, lo cual se compone de los siguientes elementos\n" +
                "\t• El número, la fecha y hora de abertura del ticket, y el nivel de prioridad del ticket.\n" +
                "\t• El nombre completo, el número de cedula y el número de teléfono del cliente interno\n" +
                "\t• El nombre de la sede, el departamento, y el número de piso de lugar de trabajo donde se encuentra el cliente interno\n" +
                "\t• Entrada de texto para indicar la acción ejecutada ante la incidencia\n" +
                "\t• Entrada de texto para las observaciones acerca del incidente, o de la misma acción\n" +
                "\t• Botón “Incidencia Resuelta”, que actualiza la información del ticket, mostrando que la incidencia fue resuelta.\n" +
                "Es obligatorio que se indique la acción para poder resolver la incidencia.\n")
    }
}

@Composable
fun DescripcionPantallaTecnico(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Inicio de técnico")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("La pantalla en que aparece los tickets asignados por el administrador.\n" )
        ImagenManual(R.drawable.inicio_tecnico)
        TextoManual("Esta pantalla contiene una lista de los tickets asignados por orden descendentes. Cuando el administrador asigna un ticket, este aparece en esta pantalla.\n" +
                "Esta pantalla forma parte de un conjunto horizontal de 3 pantallas que pueden acceder en las opciones en la parte inferior de la pantalla, o deslizando la misma. A estas pantallas se puede acceder al botón superior derecho de la misma, que al presionarse aparece el menú lateral del usuario. \n" +
                "Los tickets tienen el formato que muestran los siguientes elementos\n" +
                "\t• El tipo de ticket\n" +
                "\t• La descripción del ticket\n" +
                "\t• La fecha y la hora de la abertura del ticket\n")

    }
}

/*

(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("")

    }
}


*/