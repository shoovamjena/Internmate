package com.example.internmate.view.elements.bottomnavbar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun BottomNavNoAnimation(
    screens: List<Screen>,
    isDark: Boolean,
    isAndroid12OrAbove:Boolean,
    color: Color,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    Box(modifier = Modifier.height(58.dp)){
        if (isAndroid12OrAbove){
            Box(modifier = Modifier
                .matchParentSize()
                .blur(if (isDark)5.dp else 50.dp)
                .border(width = 5.dp,
                    brush = Brush.radialGradient(listOf(Color.White,Color.White.copy(0.5f))),
                    shape = RoundedCornerShape(50)
                )
                .background(color)
            )
        }else{
            Box(modifier = Modifier
                .matchParentSize()
                .background(color)
            )
        }
        Row(modifier = Modifier.padding(vertical = 10.dp)){
            Row(
                modifier = Modifier.padding(end = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                screens.forEachIndexed { index, screen ->
                    val isSelected = index == selectedIndex

                    val animatedWeight by animateFloatAsState(
                        targetValue = if (isSelected) 1.7f else 1.4f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessVeryLow
                        ),
                        label = ""
                    )

                    val interactionSource = remember { MutableInteractionSource() }

                    Box(
                        modifier = Modifier
                            .weight(animatedWeight)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                if (selectedIndex!= index) {
                                    onTabSelected(index)
                                    haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                                }
                            }

                                //Learn Pointer Input and Swipe Gestures
                            .pointerInput(selectedIndex) {
                                var dragAmount = 0f
                                val swipeThreshold = 100f // Minimum drag distance to trigger swipe

                                detectHorizontalDragGestures(
                                    onDragEnd = {
                                        if (abs(dragAmount) > swipeThreshold) {
                                            when {
                                                dragAmount > 0 && selectedIndex <3 -> {
                                                    onTabSelected(selectedIndex + 1)
                                                }
                                                dragAmount < 0 && selectedIndex >0 -> {
                                                    onTabSelected(selectedIndex - 1)
                                                }
                                            }
                                        }
                                        dragAmount = 0f
                                    }
                                ) { _, dragDelta ->
                                    dragAmount += dragDelta
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        BottomNavItem(
                            screen = screen,
                            isSelected = isSelected,
                            isAndroid12OrAbove,
                            isDark = isDark
                        )
                    }
                }

            }
        }
    }
}
