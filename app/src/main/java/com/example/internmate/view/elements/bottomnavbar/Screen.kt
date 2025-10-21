package com.example.internmate.view.elements.bottomnavbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.internmate.R

sealed class Screen(
    val title: String,
    val activeIcon: @Composable () -> ImageVector,
    val inactiveIcon: @Composable () -> ImageVector,
    val route: String
) {
    data object Home: Screen("Home", { ImageVector.vectorResource(R.drawable.round_home_24) }, { ImageVector.vectorResource(R.drawable.rounded_home_24) },"home")
    data object Career: Screen("Career",
        { ImageVector.vectorResource(R.drawable.growth_filled) },
        { ImageVector.vectorResource(R.drawable.growth) },"career")
    data object Jobs: Screen("Jobs", { ImageVector.vectorResource(R.drawable.bag_filled) },
        { ImageVector.vectorResource(R.drawable.bag) },"job")
    data object Ai: Screen("AI", { ImageVector.vectorResource(R.drawable.ai_chipset) },
        { ImageVector.vectorResource(R.drawable.chip) },"ai")
}