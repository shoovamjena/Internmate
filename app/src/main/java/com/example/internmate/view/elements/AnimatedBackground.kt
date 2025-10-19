package com.example.internmate.view.elements

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

private data class Circle(
    val color: Color,
    val initialRadius: Float,
    val xOffset: Float,
    val yOffset:Float
){
    val positionX = Animatable(xOffset)
    val positionY = Animatable(yOffset)
    val radius = Animatable(initialRadius)
}

@Composable
fun AnimatedBackground(
    colors: List<Color> = listOf(
        Color(0xFF9849F1), // Dark purple (from text/icon)
        Color(0xFF87A1FF), // Mid-dark purple
        Color(0xFFCD43E8), // Mid-light purple
        Color(0xFFBEA4FC)  // Light purple (from icon arrow)
    ),
    animationPaused: Boolean
) {
    rememberCoroutineScope()

    val circles = remember{
        mutableStateListOf(
            Circle(colors[0], 600f, 0.2f, 0f),
            Circle(colors[1], 400f, 1f, 0.4f),
            Circle(colors[2], 800f, 1f, 1f),
            Circle(colors[3], 300f, 0f, 0.8f)
        )
    }
    LaunchedEffect(Unit) {
        while (!animationPaused) {
            // Create shuffled lists of the initial positions.
            // This ensures circles swap places with each other's starting points.
            val targetXOffsets = circles.map { it.xOffset }.shuffled()
            val targetYOffsets = circles.map { it.yOffset }.shuffled()

            // Animate each circle to a new shuffled position and a new random size.
            circles.forEachIndexed { index, circle ->
                // launch starts a new coroutine for each circle's animation
                // allowing them all to animate concurrently.
                launch {
                    circle.positionX.animateTo(
                        targetValue = targetXOffsets[index],
                        animationSpec = tween(durationMillis = 6000)
                    )
                }
                launch {
                    circle.positionY.animateTo(
                        targetValue = targetYOffsets[index],
                        animationSpec = tween(durationMillis = 6000)
                    )
                }
                launch {
                    // Animate to a new random radius between 300f and 900f.
                    val newRadius = Random.nextFloat() *600f +200f
                    circle.radius.animateTo(
                        targetValue = newRadius,
                        animationSpec = tween(durationMillis = 6000)
                    )
                }
            }

            // Wait for the animations to finish before starting the next loop.
            delay(6000)
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        circles.forEachIndexed{ _, state ->
            val centerX = state.positionX.value * width
            val centerY = state.positionY.value * height
            drawCircle(
                color = state.color,
                radius = state.radius.value,
                center = Offset(centerX, centerY)
            )
        }
    }
}