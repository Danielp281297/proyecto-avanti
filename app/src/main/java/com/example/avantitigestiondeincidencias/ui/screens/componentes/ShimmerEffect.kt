package com.example.avantitigestiondeincidencias.ui.screens.componentes

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

fun Modifier.shimmerEffect(): Modifier = composed {

    var size = remember{
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX = transition.animateFloat(
        initialValue = -2 * size.value.width.toFloat(),
        targetValue = 2 * size.value.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0x70EFEFEF),
                Color(0x70B0B0B0),
                Color(0x70EFEFEF)
            ),
            start = Offset(startOffsetX.value, 0f),
            end = Offset((startOffsetX.value + size.value.width.toFloat()), size.value.height.toFloat())
        )
    )
        .onGloballyPositioned{
            size.value = it.size
        }

}


@Composable
fun TicketLoading()
{
    Column(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)){
            Box(modifier = Modifier.height(15.dp).weight(2F).shimmerEffect()) {}
            Box(modifier = Modifier.height(15.dp).weight(1F).shimmerEffect()) {}
        }
        Box(modifier = Modifier.fillMaxWidth().height(15.dp).shimmerEffect()) {}
        Box(modifier = Modifier.fillMaxWidth().height(15.dp).shimmerEffect()) {}
        HorizontalDivider()
    }
}

@Composable
fun LoadingShimmerEffectScreen(
    isLoading: Boolean,
    contenidoMientrasCarga: @Composable () -> Unit,
    contenidoDespuesdeCargar: @Composable () -> Unit,
    modifier: Modifier = Modifier
)
{

    if(isLoading){

        contenidoMientrasCarga()

    }
    else
    {
        contenidoDespuesdeCargar()
    }

}
