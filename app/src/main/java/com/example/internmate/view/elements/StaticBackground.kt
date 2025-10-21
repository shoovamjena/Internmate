package com.example.internmate.view.elements
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

 data class StaticCircles(
    val color: Color,
    val radius: Float,
    val positionX: Float,
    val positionY: Float
)
val BgCircles = listOf(
    StaticCircles(Color(0xFF9849F1), 500f, 0.1f, 0f),
    StaticCircles(Color(0xFF87A1FF), 400f, 1f, 0.4f),
    StaticCircles(Color(0xFFCD43E8), 800f, 1f, 1f),
    StaticCircles(Color(0xFFBEA4FC), 300f, 0f, 0.8f)
)
@Composable
fun StaticBackground(
    circles: List<StaticCircles> = BgCircles
){


    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val width = size.width
        val height = size.height
        circles.forEachIndexed{ _, state ->
            val centerX = state.positionX * width
            val centerY = state.positionY * height
            drawCircle(
                color = state.color,
                radius = state.radius,
                center = Offset(centerX, centerY)
            )
        }
    }
}