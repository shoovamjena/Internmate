package com.example.internmate.view.screens

import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.internmate.data.UserPreferencesRepository
import com.example.internmate.view.elements.bottomnavbar.BottomNavNoAnimation
import com.example.internmate.view.elements.bottomnavbar.Screen
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    context: Context,
    navController: NavController,
    userPreferencesRepository: UserPreferencesRepository
){
    val coroutineScope = rememberCoroutineScope()
    val isDark = isSystemInDarkTheme()
    val isAndroid12OrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 4 } // Number of screens
    )
    val screens = listOf(
        Screen.Home,
        Screen.Career,
        Screen.Jobs,
        Screen.Ai
    )
    Scaffold (
        Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .heightIn(min = 30.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars)

            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .shadow(16.dp, shape = RoundedCornerShape(50))

                ) {
                    BottomNavNoAnimation(
                        screens = screens,
                        isDark = isDark,
                        isAndroid12OrAbove = isAndroid12OrAbove,
                        color = Color(0xFFA802F8),
                        selectedIndex = pagerState.settledPage,
                        onTabSelected = { index ->
                            coroutineScope.launch {
                                pagerState.scrollToPage(index) // or scrollToPage for instant switch
                            }
                        }
                    )
                }
            }
        }
    ){paddiing->

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            beyondViewportPageCount = 0
        ) { page ->
            when(page){
                0-> HomeScreen(
                    navController,
                    userPreferencesRepository
                )
                1-> CareerScreen()
                2-> JobScreen()
                3-> ChatScreen()
            }
        }
    }
}