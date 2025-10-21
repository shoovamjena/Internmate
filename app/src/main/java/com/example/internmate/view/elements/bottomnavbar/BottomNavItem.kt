package com.example.internmate.view.elements.bottomnavbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internmate.ui.theme.romalio

@Composable
fun BottomNavItem(
    screen: Screen,
    isSelected: Boolean,
    isAndroid12OrAbove: Boolean,
    isDark: Boolean
) {
    val animationKey = remember(screen.title) { screen.title }

    val animatedHeight by animateDpAsState(
        targetValue = if (isSelected) 40.dp else 20.dp,
        animationSpec = spring(
            stiffness = Spring.StiffnessVeryLow,
            dampingRatio = Spring.DampingRatioHighBouncy,
        ),
        label = "height_$animationKey"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else .7f,
        animationSpec = spring(
            stiffness = Spring.StiffnessVeryLow,
            dampingRatio = Spring.DampingRatioHighBouncy,
        ),
        label = "alpha_$animationKey"
    )
    val animatedIconSize by animateDpAsState(
        targetValue = if (isSelected) 26.dp else 18.dp,
        animationSpec = spring(
            stiffness = Spring.StiffnessVeryLow,
            dampingRatio = Spring.DampingRatioHighBouncy,
        ),
        label = "iconSize_$animationKey",
    )

    Box(
        modifier = Modifier.height(animatedHeight)
    ){
        if (isAndroid12OrAbove){

            Box(modifier = Modifier
                .matchParentSize()
                .shadow(
                    elevation = if(isSelected)15.dp else 0.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .blur(if (isSelected) 5.dp else 0.dp)
                .innerShadow(
                    shape = RoundedCornerShape(50),
                    shadow = Shadow(
                        radius = 25.dp,
                        color = if(isSelected) Color.White else Color.Transparent
                    )
                )
                ){}
        } else {
            Box(modifier = Modifier.matchParentSize()
                .clip(RoundedCornerShape(50))
                .shadow(
                    elevation = if(isSelected)15.dp else 0.dp,
                    shape = RoundedCornerShape(50))
                .background(if (isSelected) {if(!isDark)Color.White.copy(0.7f) else Color.White.copy(0.3f)} else Color.Transparent)
            )
        }
        Row(
            modifier = Modifier.padding(end = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            FlipIcon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxHeight()
                    .padding(start = 5.dp)
                    .alpha(animatedAlpha)
                    .size(animatedIconSize),
                isActive = isSelected,
                activeIcon = screen.activeIcon,
                inactiveIcon = screen.inactiveIcon,
                contentDescription = "Bottom Navigation Icon"
            )
            AnimatedVisibility(
                visible = isSelected,
                enter =slideInHorizontally(),
                exit =slideOutHorizontally()
            ) {
                Text(
                    text = screen.title.uppercase(),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                        .basicMarquee(animationMode = MarqueeAnimationMode.Immediately,
                            iterations = Int.MAX_VALUE,// Start animation immediately
                        initialDelayMillis = 1000, // Initial delay before first scroll
                        spacing = MarqueeSpacing.fractionOfContainer(0.6f), // Space between repetitions
                        velocity = 35.dp),// Scroll speed),
                    maxLines = 1,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    fontFamily = romalio
                )
            }
        }
    }
}