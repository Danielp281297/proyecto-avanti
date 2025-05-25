package com.example.avantitigestiondeincidencias.ui.screens.administrador

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldConMenuLateral
import com.example.avantitigestiondeincidencias.ui.screens.ticket.BusquedaTicket
import com.example.avantitigestiondeincidencias.ui.screens.perfil.MenuLateralContenido
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


data class NavItem(val label: String, val icon: ImageVector)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HorizontalPagerBottomBarAdministrador(
    navController: NavController,
    context: Context,
    administrador: Tecnico,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919),
    )
{

    val corroutineScope = rememberCoroutineScope()

    val navItemList = listOf(
        NavItem("Inicio", Icons.Default.List),
        NavItem("Buscar\nticket", Icons.Default.Search),
        NavItem("Indicadores", ImageVector.Companion.vectorResource(R.drawable.indicadores_icon)),
        NavItem("Usuarios", Icons.Default.Person)

    )

    val numPantalla = rememberSaveable {
        mutableStateOf(navItemList.count())
    }

    val state = rememberPagerState(initialPage = 3, pageCount = { numPantalla.value })

    Network.networkCallback(navController, context)

    ScaffoldConMenuLateral(
        titulo = "",
        containerColor =  containerColor,
        contenidoMenu = {
            MenuLateralContenido(navController, context, administrador.empleado, perfil = {

                // Se covierte el objeto en json para enviarlo a la pantalla
                val json = Json { ignoreUnknownKeys = true }.encodeToString(administrador)

                navController.navigate("informacionPerfilTecnico" + "/${json}")

            }, manualUsuarioEvento = {
                navController.navigate("ManualAdministrador")
            })
    })
    {
        Scaffold(
            bottomBar = {

                NavigationBar(containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)) {

                    navItemList.forEachIndexed { index, item ->

                        NavigationBarItem(
                            selected = true,
                            onClick = {

                                corroutineScope.launch {
                                    state.animateScrollToPage(index)
                                }

                            },
                            colors = NavigationBarItemColors(
                                selectedIconColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                selectedTextColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                selectedIndicatorColor = Color.Transparent,
                                unselectedIconColor = Color.White,
                                unselectedTextColor = Color.White,
                                disabledIconColor = Color.White,
                                disabledTextColor = Color.White
                            ),
                            icon = { Icon(imageVector = item.icon, contentDescription = item.label, modifier = Modifier.size(20.dp)) },
                            label = { Text(text = item.label, textAlign = TextAlign.Center) }
                        )

                    }

                }
            },
        )
        {

            HorizontalPager(state = state, modifier = Modifier.background(Color.Blue))
            { page ->

                    when (page) {
                        0 -> InicioAdministrador(navController, containerColor)
                        1 -> BusquedaTicket(navController, context, administrador, clickAccion = { json ->

                            navController.navigate("ticketDesplegadoAdministrador" + "/${json}")

                        }, containerColor)
                        2 -> IndicadoresAdministrador(navController, context, administrador, containerColor)
                        3 -> BusquedaUsuarios(navController, context, containerColor)
                    }
            }


        }
    }

}

@Preview(showBackground = true)
@Composable
fun HorizontalPagerBottomBarPreview() {

    val navController = rememberNavController()
    val context = LocalContext.current

    AVANTITIGestionDeIncidenciasTheme {

        HorizontalPagerBottomBarAdministrador(navController, context, Tecnico())

    }
}
