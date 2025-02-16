package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.R
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.launch

data class horizontalPagerIcons(val icono: Int, val titulo: String)

@Composable
fun HorizontalPagerBottomBarTecnico()
{
    val context = LocalContext.current
    val corroutineScope = rememberCoroutineScope()

    val numPantalla = rememberSaveable {
        mutableStateOf(4)
    }

    val horizontalPagerScreen = remember {
        mutableStateListOf(
            horizontalPagerIcons(1, "INICIO"),
            horizontalPagerIcons(1, "BUSCAR"),
            horizontalPagerIcons(1, "INFORME"),
            horizontalPagerIcons(1, "CUENTA")
        )
    }

    val state = rememberPagerState(initialPage = 0, pageCount = { numPantalla.value })

    ConstraintLayout(modifier = Modifier.fillMaxSize())
    {
        var (horizontalPager, bottomBar) = createRefs()

        HorizontalPager(state = state, modifier = Modifier.background(Color.Blue).constrainAs(horizontalPager) {
            top.linkTo(parent.top)
            bottom.linkTo(bottomBar.top)

            width = Dimension.matchParent
            height = Dimension.fillToConstraints
        })
        { page ->

            when(page)
            {
                0 -> InicioAdministrador()
                else -> Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
                        {
                            //Text(text = horizontalPagerScreen[page])
                        }
            }

        }
        BottomAppBar(modifier = Modifier.constrainAs(bottomBar) {

            bottom.linkTo(parent.bottom)
        })
        {
            Row(modifier = Modifier.background(Color.Red).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

                // Un bucle desde el horizontalPageScreen, para crear las opciones de la bottonBar
                horizontalPagerScreen.forEachIndexed { index, item  ->

                    Column(verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxHeight().clickable {
                            // animatedScrollToPage es una funcion suspendida, por ende se tiene que usar corrutinas
                            corroutineScope.launch {

                                state.animateScrollToPage(index)
                                //Cada vez que se cambia la pantalla, se actualiza los datos de la base de datos
                                Toast.makeText(
                                    context,
                                    "Pagina",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        })
                    {
                        Text(text = item.titulo, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                    }

                }

            }
        }
    }
    LaunchedEffect(state.currentPage)
    {
        Toast.makeText(
            context,
            "Pagina",
            Toast.LENGTH_SHORT
        ).show()
    }
}


