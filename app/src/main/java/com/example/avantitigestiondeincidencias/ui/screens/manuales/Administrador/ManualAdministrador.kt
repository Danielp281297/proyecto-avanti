package com.example.avantitigestiondeincidencias.ui.screens.manuales.Administrador

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun ManualAdministrador(
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
) {

    var scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 21 })

    ScaffoldSimplePersonalizado(
        tituloPantalla = "Manual de Avanti - Administrador",
        containerColor = containerColor
    ) {

        VerticalPager(
            modifier = Modifier,
            state = pagerState
        ) { page ->

            // depende del resultado del los lambdas, se redirige a una pagina en especifico
            when (page) {
                0 -> Presentacion()
                1 -> IndiceAdministrador{
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
                5 -> DescripcionPantallaInicioAdministrador{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                6 -> DescripcionPantallaInformacionTicketAdministrador{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                7 -> DescripcionPantallaBusquedaTicket{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                8 -> DescripcionPantallaIndicadores{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                9 -> DescripcionPantallaUsuarios{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                10 -> DescripcionPantallaInformacionUsuario{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                11 -> DescripcionPantallaEditarPerfilUsuario{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                12 -> DescripcionPantallaCrearUsuario{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }// Crear usuario
                13 -> DescripcionPantallaMenuLateral{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                14 -> DescripcionPantallaPerfil(R.drawable.perfil_cliente_interno){
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                15 -> DescripcionPantallaEditarPerfil{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                16 -> DescripcionPantallaCambiarContrasena{
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
                17 -> OperatividadPantalla()

                18 -> OperatividadAdministrador{

                    scope.launch {
                        pagerState.scrollToPage(it)
                    }

                }
                19 -> Informe()
                20 -> InformeIndicadores{

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
fun IndiceAdministrador(numPagina: (Int) -> Unit) {
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
        TextoManual("\t Pantalla de Inicio de Administrador", modifier = Modifier.clickable { numPagina(5) })
        TextoManual("\t Pantalla de Información del ticket", modifier = Modifier.clickable { numPagina(6) })
        TextoManual("\t Pantalla de Buscar ticket.", modifier = Modifier.clickable { numPagina(7) })
        TextoManual("\t Pantalla de Indicadores.", modifier = Modifier.clickable { numPagina(8) })
        TextoManual("\t Pantalla de Usuarios.", modifier = Modifier.clickable { numPagina(9) })
        TextoManual("\t Pantalla de Información del usuario", modifier = Modifier.clickable { numPagina(10) })
        TextoManual("\t Pantalla de Editar perfil del usuario", modifier = Modifier.clickable { numPagina(11) })
        TextoManual("\t Pantalla de Crear usuario", modifier = Modifier.clickable { numPagina(12) })
        TextoManual("\t Pantalla de Menú Lateral", modifier = Modifier.clickable { numPagina(13) })
        TextoManual("\t Pantalla de perfil", modifier = Modifier.clickable { numPagina(14) })
        TextoManual("\t Pantalla de editar perfil", modifier = Modifier.clickable { numPagina(15) })
        TextoManual("\t Pantalla de editar contraseña", modifier = Modifier.clickable { numPagina(16) })
        TextoManual("OPERATIVIDAD DE GESTION DE INCIDENCIAS", modifier = Modifier.clickable { numPagina(17) })
        TextoManual("\t Operatividad", modifier = Modifier.clickable { numPagina(18) })
        TextoManual("INFORME", modifier = Modifier.clickable { numPagina(19) })
        TextoManual("\tInforme de indicadores", modifier = Modifier.clickable { numPagina(20) })
        Spacer(modifier = Modifier.padding(30.dp))
    }
}

@Composable
fun InformeIndicadores(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Informe de indicadores")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Este informe es generado al presionar el botón “generar informe” en la pantalla de indicadores:\n")
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .border(1.dp, if(!isSystemInDarkTheme()) Color.Black else Color.Transparent, RectangleShape),
            painter = painterResource(R.drawable.formato_informe_administrador),
            contentDescription = "Pantalla Login"
        )
        TextoManual("Este es un informe que se guarda en la carpeta de documentos del dispositivos.\n" +
                "Este informe es un archivo Excel formato .xlsx, que muestra los siguientes elementos:\n" +
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
                "\t•  Un gráfico de torta mostrando la cantidad de tickets resueltos por cada técnico\n" +
                "\t• Un gráfico de torta mostrando la cantidad de tickets en relación con los diferentes tipos.\n" +
                "Este formato siguiendo el formato de informe generados por el coordinador de infraestructura.\n")

    }
}


@Composable
fun Informe() {
    Column(modifier = Modifier.padding(15.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        TituloManual("INFORME")
    }
}

@Composable
fun OperatividadAdministrador(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Operatividad ")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("La gestión comienza cuando el cliente interno presenta una situación de interrupción o una solicitud. \n" +
                "Cuando el cliente interno abre un ticket, este aparece en la lista de la pantalla de inicio del usuario. El administrado puede acceder a la información del ticket al pulsar el mismo. Esto abre la pantalla de información del ticket.\n" +
                "En esta misma pantalla el administrador tiene dos opciones: Asignar un ticket o cancelarlo si lo considera pertinente. \n" +
                "Al momento de cancelar el ticket, este desaparece de la lista de la pantalla de inicio tanto del administrador como del cliente interno.\n" +
                "Al momento de asignar el ticket este desaparece de la pantalla de inicio, aunque puede ser encontrado en la pantalla de “buscar ticket” para poder reasignar el ticket en caso que se considere necesario.\n" +
                "Al asignar el ticket, el técnico será responsable del diagnóstico y la resolución del incidente.\n")
    }
}

@Composable
fun DescripcionPantallaCrearUsuario(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Crear usuario")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Al pulsar el botón flotante de la pantalla “Usuarios”, aparece la siguiente:\n" )
        ImagenManual(R.drawable.crear_usuario)
        TextoManual("La pantalla se compone de los siguientes elementos:\n" +
                "\t• El tipo de usurario: Campo obligatorio de lista desplegable\n" +
                "\t• Número de cédula: Campo obligatorio con un rango no mayor a 31 millones.\n" +
                "\t• Nombre completo: Campos obligatorios del nombre completo del usuario. Una entrada de texto alfabético en un rango de 3 y 20 caracteres.\n" +
                "\t• Correo electrónico: Campo obligatorio en un rango entre 3 y 255 caracteres\n" +
                "\t• Número de teléfono: Conformado por una lista desplegable con la extensión de las diferentes operadoras en Caracas, y el campo obligatorio del número de teléfono.\n" +
                "\t• Departamento: Si es un usuario cliente interno, es campo obligatorio que muestra una lista desplegable de los diferentes departamentos de la empresa, y un campo de texto “Sede” que muestra el nombre de la sede donde se encuentra ubicado el mismo.\n" +
                "\t• Cargo: Campo obligatorio de una lista desplegable que muestran los diferentes cargos de la empresa\n" +
                "\t• Grupo de atención: Si es un técnico, este es un campo obligatorio de lista desplegable que muestra los diferentes grupos de atención o áreas de especialidad del técnico.\n" +
                "\t• Nombre de usuario: Campo obligatorio del nombre de usuario con un rango de 6 a 20 caracteres.\n" +
                "\t• Contraseña y confirmar contraseña: Campos obligatorios para validar la contraseña de usuario. \n")

    }
}


@Composable
fun DescripcionPantallaEditarPerfilUsuario(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Editar perfil de usuario")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        ImagenManual(R.drawable.editar_perfil_usuario)
        TextoManual("\n" +
                "En esta pantalla se muestran los datos de usuario para poder editar los mismos. \n" +
                "Entre los campos del formulario, se encuentran:\n" +
                "\t• El tipo de usurario: Campo obligatorio de lista desplegable\n" +
                "\t• Numero de cedula: Campo obligatorio con un rango no mayor a 31 millones.\n" +
                "\t• Nombre completo: Campos obligatorios del nombre completo del usuario. Una entrada de texto alfabético en un rango de 3 y 20 caracteres.\n" +
                "\t• Correo electrónico: Campo obligatorio en un rango entre 3 y 255 caracteres\n" +
                "\t• Número de teléfono: Conformado por una lista desplegable con la extensión de las diferentes operadoras en Caracas, y el campo obligatorio del número de teléfono.\n" +
                "\t• Departamento: Si es un usuario cliente interno, es campo obligatorio que muestra una lista desplegable de los diferentes departamentos de la empresa, y un campo de texto “Sede” que muestra el nombre de la sede donde se encuentra ubicado el mismo.\n" +
                "\t• Cargo: Campo obligatorio de una lista desplegable que muestran los diferentes cargos de la empresa\n" +
                "\t• Grupo de atención: Si es un técnico, este es un campo obligatorio de lista desplegable que muestra los diferentes grupos de atención o áreas de especialidad del técnico.\n" +
                "\t• Nombre de usuario: Campo obligatorio del nombre de usuario con un rango de 6 a 20 caracteres.\n" +
                "\t• Un botón para validar y modificar los campos en la base de datos \n")

    }
}

@Composable
fun DescripcionPantallaInformacionUsuario(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Información del usuarios")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("Al pulsar en un usuario, se muestra la siguiente pantalla:\n" )
        ImagenManual(R.drawable.informacion_usuario)
        TextoManual("En esta pantalla se muestra los siguientes elementos:\n" +
                "\t• Datos personales: Cedula y nombre completo del usuario.\n" +
                "\t• Datos de contacto: Número de teléfono y correo electrónico del usuario.\n" +
                "\t• Datos laborales: Piso, departamento y sede donde trabaja el usuario.\n" +
                "\t• El nombre del usuario.\n" +
                "\t• Botón de editar perfil: Botón para cambiar a la pantalla de editar perfil.\n" +
                "\t• Botón de cambiar contraseña: Botón para cambiar a la pantalla de cambiar contraseña. \n" +
                "\t• Si el usuario es técnico, se muestra su grupo de atención\n" +
                "\t• El botón para cambiar la pantalla para editar los datos del usuario\n" +
                "\t• El botón para cambiar la contraseña de usuario\n" +
                "\t• El botón para inhabilitar el usuario\n")
    }
}

@Composable
fun DescripcionPantallaUsuarios(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Usuarios")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("La cuarta pantalla del menú deslizable:\n")
        ImagenManual(R.drawable.usuarios)
        TextoManual("Esta pantalla tiene los siguientes elementos\n" +
                "\t•Una entrada de texto para buscar a los usuarios\n" +
                "\t•Un botón “Filtros de búsqueda”, que muestra una lista desplegable de diferentes filtros de búsqueda de clientes\n" +
                "\t•Un botón “Técnico” que muestra la lista de los técnicos \n" +
                "\t•Un botón “Cliente” que muestra la lista de los clientes\n" +
                "\t•Un botón flotante que permite crear un nuevo usuario\n" +
                "En esta pantalla, el administrador puede buscar información acerca de los usuarios de la aplicación. Es obligatorio pulsar uno de estos dos botones para mostrar la lista de usuarios respectivos, y habilitar la entrada de texto.\n" +
                "El filtro desplegable tiene diferentes opciones para la búsqueda, entre los cuales están:\n" +
                "\t•Nombre de usuario: Buscar perfiles por el nombre de usuario\n" +
                "\t•Nombre del empleado: Buscar perfiles por el primer nombre del usuario\n" +
                "\t•Numero de cedula: Buscar perfiles por el número de cedula.\n" +
                "Estos son filtros para la búsqueda en la entrada de texto.\n" +
                "Al mostrar la lista, estas presentan los siguientes elementos:\n" +
                "\t•En caso de los clientes, se muestran\n" +
                "\t\toLa sede, piso y departamento\n" +
                "\t\toEl nombre completo del cliente\n" +
                "\t\toEl nombre de usuario \n" +
                "\t•En caso de los técnicos. Se muestra\n" +
                "\t\toEl grupo de atención\n" +
                "\t\toNombre completo del técnico\n" +
                "\t\toEl nombre de usuario \n")

    }
}


@Composable
fun DescripcionPantallaIndicadores(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Indicadores")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("La tercera pantalla del menú deslizable:\n")
        ImagenManual(R.drawable.indicadores)
        TextoManual("Esta pantalla se compone de los siguientes elementos:\n" +
                "\t• Una entrada de fecha inicial\n" +
                "\t• Una entrada de fecha final\n" +
                "\t• Botón “GENERAR INFORME”\n" +
                "En esta pantalla, el usuario puede conocer los datos relacionados con los tickets en base a un rango de fechas. \n" +
                " Al hacer esto, se habilita el botón “GENERAR INFORME” que, al habilitarse y pulsarse, se muestran los siguientes elementos:\n" +
                "\t• El total de tickets\n" +
                "\t• Un gráfico de tortas de los tickets según su estado.\n" +
                "\t• Un gráfico de tortas de los diferentes tipos de tickets.\n" +
                "\t• El promedio de tiempo de respuesta en formato HH:mm:ss\n" +
                "\t• El promedio de calificación de la gestión de la incidencia de los tickets\n" +
                "\t• El botón pata generar el informe y guardarse en el almacenamiento del dispositivo.\n" +
                "Es obligatorio que por lo menos, una de las dos entradas sea llenas para poder generar los datos.\n")

    }
}

@Composable
fun DescripcionPantallaBusquedaTicket(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Buscar ticket")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("La segunda pantalla del conjunto es la búsqueda de tickets:\n" )
        ImagenManual(R.drawable.buscar_ticket)
        TextoManual("Esta pantalla muestra un pequeño motor de búsqueda con tres filtros:\n" +
                "\t• Una entrada de texto para buscar un conjunto de tickets por su descripción.\n" +
                "\t• Una entrada para indicar la fecha inicial para buscar los tickets\n" +
                "\t• Una entrada para indicar la fecha límite para buscar los tickets\n" +
                "Si bien las entradas son necesarias para buscar un ticket en específico, no es obligatorio completarlos para buscar ticket:\n" +
                "\t• Si la entrada de texto está vacía, se busca muestran los últimos 50 tickets en orden descendente.\n" +
                "\t• Si la entrada de la fecha inicial y final están vacías, se muestran todos los tickets que coincidan con la descripción.\n" +
                "En esta pantalla, se puede observar los datos de los tickets, con el agregado que muestra la prioridad de los mismos. \n")

    }
}

@Composable
fun DescripcionPantallaInformacionTicketAdministrador(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Información del ticket")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("La pantalla de la información del ticket se muestra de la siguiente forma:\n" )
        ImagenManual(R.drawable.informacion_ticket_administrador)
        TextoManual("Esta pantalla se compone de los siguientes elementos\n" +
                "\t• El número, la fecha y hora de abertura del ticket, y el nivel de prioridad del ticket.\n" +
                "\t• El nombre completo, el número de cedula y el número de teléfono del cliente interno\n" +
                "\t• El nombre de la sede, el departamento, y el número de piso de lugar de trabajo donde se encuentra el cliente interno\n" +
                "Si el ticket no fue asignado, se muestran los siguientes elementos: \n" +
                "\t• Una lista desplegable que muestra las diferentes opciones para priorizar el ticket.\n" +
                "\t• Una lista desplegable que muestras los diferentes analistas técnicos registrados en la aplicación.\n" +
                "\t• Un botón para asignar el ticket a un técnico ya indicado en la lista anterior\n" +
                "\t• Un botón para cancelar el ticket\n" +
                "Es obligatorio priorizar e indicar un técnico para asignar un ticket.\n")

    }
}

@Composable
fun DescripcionPantallaInicioAdministrador(numPantalla: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(enabled = true, state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Spacer(modifier = Modifier.padding(50.dp))
        TituloManual("Pantalla de Inicio de Administrador")
        TextoManual("volver al Índice", modifier = Modifier.align(Alignment.End).clickable { numPantalla(1) })
        TextoManual("La primera pantalla que ve el administrador al acceder a su usuario:\n")
        ImagenManual(R.drawable.inicio_administrador)
        TextoManual("En esta pantalla se visualizan últimos tickets abiertos en orden descendente. En el momento en que un ticket se asigna a un técnico, se actualiza la lista, y ocultando el ticket.\n" +
                "Esta pantalla forma parte de un conjunto horizontal de pantallas que pueden acceder en las opciones en la parte inferior de la pantalla, o deslizando la misma. \n" +
                "En estas cuatro pantallas, se puede acceder al botón superior derecho de la misma, que al presionarse aparece el menú lateral del usuario. \n" +
                "Los tickets tienen el formato que muestran los siguientes elementos\n" +
                "\t• El tipo de ticket\n" +
                "\t• La descripción del ticket\n" +
                "\t• La fecha y la hora de la abertura del ticket\n" +
                "\n")

    }
}

