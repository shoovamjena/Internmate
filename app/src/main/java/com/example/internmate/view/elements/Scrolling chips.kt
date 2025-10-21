package com.example.internmate.view.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.internmate.ui.theme.romalio
import kotlinx.coroutines.isActive

/**
 * A composable that displays a horizontal, infinitely scrolling list of chips
 * at a constant linear speed (marquee effect).
 *
 * @param chipItems The list of strings to display in the chips.
 * @param scrollSpeed The speed of the scroll, in Dp per second.
 * @param slantAngle The angle in degrees to slant (rotate) the entire row.
 * @param edgeToEdgeScale The amount to scale the row when slanted to fill screen edges.
 * 1.1f means 110% scale.
 */
@Composable
fun InfiniteScrollingChips(
    modifier: Modifier = Modifier,
    chipItems: List<String>,
    scrollSpeed: Dp = 100.dp,
    slantAngle: Float = -5f,
    edgeToEdgeScale: Float = 1.25f,
    textColor: Color,
    surfaceColor:Color
) {
    val lazyListState = rememberLazyListState()
    val density = LocalDensity.current

    val scrollSpeedPxPerSecond = with(density) { scrollSpeed.toPx() }
    val items = chipItems.takeIf { it.isNotEmpty() } ?: return

    LaunchedEffect(key1 = items, key2 = scrollSpeed) {
        var lastFrameTime = withFrameNanos { it }
        while (isActive) {
            val currentFrameTime = withFrameNanos { it }
            val deltaTimeMillis = (currentFrameTime - lastFrameTime) / 1_000_000L
            val pixelsToScroll = (scrollSpeedPxPerSecond * (deltaTimeMillis / 800f))

            lazyListState.scrollBy(pixelsToScroll)
            lastFrameTime = currentFrameTime
        }
    }

    LazyRow(
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        userScrollEnabled = false,
        modifier = modifier
            .graphicsLayer {
                rotationZ = slantAngle

                // --- THIS IS THE FIX ---
                // Apply scaling *only* when slanted to fill the gaps
                // A 0f angle doesn't need scaling.
                if (slantAngle != 0f) {
                    scaleX = edgeToEdgeScale
                    scaleY = edgeToEdgeScale
                }
                // -----------------------
            }
    ) {
        items(
            count = Int.MAX_VALUE,
            key = { index -> index }
        ) { index ->
            MyChip(text = items[index % items.size],textColor,surfaceColor)
        }
    }
}

/**
 * A simple composable for styling the chip.
 */
@Composable
fun MyChip(
    text: String,
    textColor: Color,
    surfaceColor:Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.shadow(15.dp, CircleShape),
        shape = CircleShape,
        color = surfaceColor,
        border = BorderStroke(1.dp, Color.Black.copy(0.3f))
    ) {
        Text(
            text = text.uppercase(),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            fontFamily = romalio,
        )
    }
}

