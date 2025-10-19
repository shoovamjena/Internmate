package com.example.internmate.view.screens

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.internmate.R
import com.example.internmate.ui.theme.inspDoc
import com.example.internmate.ui.theme.romalio
import com.example.internmate.view.elements.AnimatedBackground
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun WelcomeScreen(
    navController: NavController
) {

    var isTapped by remember { mutableStateOf(false) }
    val haptic = LocalView.current

    var isDragging by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableFloatStateOf(0f) }
    var isDragged by remember { mutableStateOf(false) }

    val animationPaused = isDragging || isTapped
    var loginWidth = 0.dp
    isSystemInDarkTheme()
    // Animated drag offset with bounce-back
    val animatedDragOffset by animateFloatAsState(
        targetValue = dragOffset,
        animationSpec = if (!isDragging && !isDragged) {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        } else {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        },
        label = "dragAnimation"
    )
    val textAlpha by animateFloatAsState(
        targetValue = run {
            val containerWidthPx = with(LocalDensity.current) { loginWidth.toPx() }
            val buttonSizePx = with(LocalDensity.current) { 50.dp.toPx() }
            val textCenterPx = containerWidthPx / 2f
            val iconCenterPx = animatedDragOffset + (buttonSizePx / 2f)

            // Calculate distance from icon center to text center
            val distanceToText = kotlin.math.abs(iconCenterPx - textCenterPx)
            val fadeThreshold = buttonSizePx * 1.5f // Start fading when icon is 1.5x button size away from text

            when {
                // If icon hasn't reached the fade threshold, keep text fully visible
                distanceToText > fadeThreshold -> 0.3f
                // If icon is approaching or crossing text, fade based on proximity
                iconCenterPx < textCenterPx -> {
                    // Icon approaching from left - fade out as it gets closer
                    (distanceToText / fadeThreshold).coerceIn(0.3f, 1f)
                }
                else -> {
                    // Icon has crossed to the right - keep text invisible
                    1f
                }
            }
        },
        animationSpec = tween(durationMillis = 150),
        label = "textFadeAnimation"
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)

    ) {
        Box(modifier = Modifier.fillMaxSize().blur(25.dp)){
            AnimatedBackground(animationPaused = animationPaused)
        }
        Text(
            text = "InternMate",
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(
                maxFontSize = 86.sp
            ),
            style = TextStyle(
                fontFamily = inspDoc,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.align(Alignment.Center)
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 100.dp)
            ,
            contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(R.drawable.quote),
                contentDescription = "quote",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(160.dp)
                    .padding(end = 130.dp, bottom = 100.dp)
            )
            Text(
                text = "Upload.\nChoose.\nIntern.",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 36.sp
                ),
                fontFamily = romalio,
            )
            Icon(
                painter = painterResource(R.drawable.quote),
                contentDescription = "quote",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(150.dp)
                    .padding(start = 120.dp, top = 90.dp)
                    .rotate(180f)
            )
        }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 10.dp)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .align(
                    Alignment.BottomCenter
                )
        ) {
            val containerWidth = maxWidth
            loginWidth = containerWidth
            val buttonSize = animateDpAsState(
                targetValue = if(isDragging || isTapped)70.dp else 50.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
            val maxDragDistance = containerWidth - buttonSize.value - 10.dp // Account for padding

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .shadow(6.dp, shape = RoundedCornerShape(50))
                    .clip(RoundedCornerShape(50))
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
            ) {
                // Draggable button
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .offset { IntOffset(animatedDragOffset.roundToInt(), 0) }
                        .shadow(if (!isDragged) 15.dp else 0.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer
                        )
                        .size(buttonSize.value)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isTapped = true
                                    haptic.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                                    tryAwaitRelease()
                                    isTapped = false
                                }
                            )
                        }
                        .pointerInput(Unit) {
                            val maxWidthPx = maxDragDistance.toPx()
                            detectHorizontalDragGestures(
                                onDragStart = {
                                    isDragging = true
                                    isTapped = false
                                },
                                onDragEnd = {
                                    isDragging = false
                                    // Check if dragged far enough to trigger the "done" state
                                    if (dragOffset > maxWidthPx * 0.8f) {
                                        isDragged = true
                                        navController.navigate("onboarding")
                                        dragOffset = maxWidthPx // Snap to end
                                    } else {
                                        // Trigger bounce-back animation by setting dragOffset to 0
                                        dragOffset = 0f
                                    }
                                    haptic.performHapticFeedback(HapticFeedbackConstants.GESTURE_END)
                                },
                                onDragCancel = {
                                    isDragging = false
                                    dragOffset = 0f
                                }
                            ) { _, dragDelta ->
                                val newOffset = dragOffset + dragDelta
                                dragOffset = newOffset.coerceIn(0f, maxWidthPx)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        painter = painterResource(R.drawable.next),
                        contentDescription = "Arrow Icon",
                        modifier = Modifier.size(25.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                }
                Text(
                    "Your Internship Awaits",
                    fontFamily = romalio,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primaryContainer.copy(textAlpha),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}