package com.example.internmate.view.elements

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.lerp

private data class Circles(
    val color: Color,
    val radius: Float,
    val positionX: Float, // The X position as a fraction of screen width
    val positionY: Float  // The Y position as a fraction of screen height
)
@Composable
fun StaticBackground(
    pagerState: PagerState,
    colors: List<Color> = listOf(
        Color(0xFF9849F1), // Dark purple (from text/icon)
        Color(0xFF87A1FF), // Mid-dark purple
        Color(0xFFCD43E8), // Mid-light purple
        Color(0xFFBEA4FC)  // Light purple (from icon arrow)
    )
){
    val pageStates = listOf(
        // State for Page 0
        listOf(
            Circles(colors[0], 600f, 0.2f, 0f),
            Circles(colors[1], 400f, 1f, 0.4f),
            Circles(colors[2], 800f, 1f, 1f),
            Circles(colors[3], 300f, 0f, 0.8f)
        ),
        // State for Page 1
        listOf(
            Circles(colors[0], 450f, 1f, 0.1f),    // Moves top-right, shrinks
            Circles(colors[1], 500f, 0f, 0.2f),    // Moves top-left, grows
            Circles(colors[2], 600f, 0.2f, 1f),    // Moves bottom-left, shrinks
            Circles(colors[3], 700f, 0.8f, 0.9f)   // Moves to the right, grows
        ),
        // State for Page 2
        listOf(
            Circles(colors[0], 300f, 0.5f, 0.7f),  // Moves near center
            Circles(colors[1], 450f, 0.2f, 0.4f),  // Moves near center
            Circles(colors[2], 350f, 0.8f, 0.3f),  // Moves near center
            Circles(colors[3], 500f, 0.5f, 0.5f)   // Moves to center
        )
    )

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val width = size.width
        val height = size.height

        val currentPage = pagerState.currentPage
        val offsetFraction = pagerState.currentPageOffsetFraction

        val easedFraction = FastOutSlowInEasing.transform(offsetFraction)

        // Determine the "from" and "to" states for the animation.
        val fromState = pageStates[currentPage]
        // If we are at the last page, it transitions to itself to prevent a crash.
        val toState = pageStates.getOrElse(currentPage + 1) { fromState }

        // Animate each circle individually.
        for (i in fromState.indices) {
            val fromCircle = fromState[i]
            val toCircle = toState[i]

            // ✅ Use lerp to interpolate each property based on the scroll offset.
            val currentRadius = lerp(fromCircle.radius, toCircle.radius, easedFraction)
            val currentX = lerp(fromCircle.positionX, toCircle.positionX, easedFraction) * width
            val currentY = lerp(fromCircle.positionY, toCircle.positionY, easedFraction) * height

            drawCircle(
                color = fromCircle.color,
                radius = currentRadius,
                center = Offset(currentX, currentY)
            )
        }
    }
}