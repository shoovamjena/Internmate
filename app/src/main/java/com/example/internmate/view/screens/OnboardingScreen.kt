package com.example.internmate.view.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.credentials.GetCredentialException
import android.os.Build
import android.view.HapticFeedbackConstants
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.internmate.R
import com.example.internmate.data.UserPreferencesRepository
import com.example.internmate.ui.theme.inspDoc
import com.example.internmate.ui.theme.romalio
import com.example.internmate.view.elements.AnimatedBackground
import com.example.internmate.view.elements.StaticBackground
import com.example.internmate.view.elements.authcomponents.LoginContent
import com.example.internmate.view.elements.authcomponents.SignUpContent
import com.example.internmate.viewmodel.AuthViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

// Data class to represent the content of each onboarding page
data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String,
    val description2: String = ""
)

enum class SelectedScreen{
    Login,
    SignUp
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun OnboardingScreens(
    navController: NavController,
    userPreferencesRepository: UserPreferencesRepository
) {
    val activity = LocalContext.current as Activity
    val windowSize = calculateWindowSizeClass(activity)
    val widthSize = windowSize.widthSizeClass
    val heightSize = windowSize.heightSizeClass
    var visible by remember { mutableStateOf(false) }
    val pages = listOf(
        OnboardingPage(
            imageRes = R.drawable.onboarding1,
            title = "WELCOME TO INTERNMATE",
            description = "Find Internships specially tailored for you",
        ),
        OnboardingPage(
            imageRes = R.drawable.onboarding2, // Replace with your second drawable
            title = "Internship powerhouse at Your Fingertips.".uppercase(),
            description = "Upload resume and get in as Intern",
            description2 = "\nAnytime, anywhere."
        ),
        OnboardingPage(
            imageRes = R.drawable.onboarding3, // Replace with your third drawable
            title = "Get personalized internships by our AI".uppercase(),
            description = "Getting internships has never been easier",
        )
    )

    // 2. Create the PagerState to control the pager
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }
    val haptics = LocalView.current

    var loginEmail by rememberSaveable { mutableStateOf("") }
    var loginPassword by rememberSaveable { mutableStateOf("") }
    var userName by rememberSaveable { mutableStateOf("") }
    var signUpEmail by rememberSaveable { mutableStateOf("") }
    var signUpPassword by rememberSaveable { mutableStateOf("") }

    var selectedScreen by rememberSaveable { mutableStateOf(SelectedScreen.Login) }

    val authViewModel :AuthViewModel= viewModel()
    val authState by authViewModel.uiState.collectAsState()


    BackHandler(enabled = visible) {
        visible = false
    }

    // --- START NEW CREDENTIAL MANAGER LOGIC ---
    val context = LocalContext.current
    // 1. Create the CredentialManager instance
    val credentialManager = remember { CredentialManager.create(context) }

    // 2. Define the Google Sign-In helper function
    fun launchGoogleSignIn(flow: SelectedScreen) {
        scope.launch {
            // Get the Web Client ID from your google-services.json
            // You can find it under project_info -> clients -> oauth_client -> client_id
            // (Make sure the client_type is 3)
            // Or, just paste the one from the Firebase Console
            val serverClientId = "1064352574919-82tjs7cdq3rr6btlq64pv8ijjvg8jj27.apps.googleusercontent.com" // <-- PASTE YOUR ID

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(flow == SelectedScreen.Login) // Only show saved accounts if on Login screen
                .setServerClientId(serverClientId)
                .setAutoSelectEnabled(false) // Show the One Tap UI
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            try {
                // 3. Show the One Tap UI
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                // 4. Handle the result
                val credential = result.credential
                if (credential is GoogleIdTokenCredential) {
                    val idToken = credential.idToken

                    // Check which flow started this
                    val usernameToPass = if (flow == SelectedScreen.SignUp) userName.trim() else null

                    // 5. Call the ViewModel (this function is already perfect)
                    authViewModel.signInWithGoogle(idToken, usernameToPass, userPreferencesRepository)

                } else {
                    Toast.makeText(context, "Sign-in failed: Unexpected credential type", Toast.LENGTH_SHORT).show()
                }

            } catch (e: GetCredentialException) {
                // Handle errors, e.g., user cancelled the dialog
                Toast.makeText(context, "Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            catch (e: GetCredentialCancellationException){
                Toast.makeText(context, "Sign-in cancelled", Toast.LENGTH_SHORT).show()
            }
            catch(e: NoCredentialException){
                Toast.makeText(context,"No Credentials Found. Go to SignUp", Toast.LENGTH_LONG).show()
            }
        }
    }
    // --- END NEW CREDENTIAL MANAGER LOGIC ---

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
        // Blurred Background
        Box(modifier = Modifier.matchParentSize().blur(25.dp)) {
            if(!visible) StaticBackground(pagerState) else AnimatedBackground(animationPaused = false)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            // App Title
            Text(
                text = "InternMate",
                style = TextStyle(fontFamily = inspDoc, color = MaterialTheme.colorScheme.primary),
                fontSize = 56.sp,
                maxLines = 1,
                modifier = Modifier.padding(top = 50.dp)
            )

            // 3. HorizontalPager to swipe between pages
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { pageIndex ->
                OnboardingPageItem(page = pages[pageIndex])
            }

            // 4. Page Indicators
            PageIndicator(
                pageCount = pages.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            TextButton(
                onClick = {
                    visible=true
                }
            ) {
                Text(
                    "skip",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                        fontFamily = romalio,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }

            // 5. "NEXT" / "GET STARTED" Button
            OnboardingButton(
                pagerState = pagerState,
                pageCount = pages.size,
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        visible = true
                    }
                }
            )
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = visible,
            enter = slideInVertically(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                ),
                initialOffsetY = { fullHeight -> fullHeight }
            ) + fadeIn(),
            exit = slideOutVertically(
                animationSpec = tween(500, easing = LinearOutSlowInEasing),
                targetOffsetY = { fullHeight -> fullHeight }
            ) + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .graphicsLayer {
                        this.clip = true
                        this.shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    }
                    .shadow(10.dp, shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)
            ){
                val openIcon by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(R.raw.open_icon_light)
                )
                val openIconAnimation by animateLottieCompositionAsState(
                    composition = openIcon,
                    isPlaying = visible,
                    iterations = 1,
                    speed = 0.85f
                )
                // Encapsulate toggle and content in a Column
                LazyColumn (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        LottieAnimation(
                            composition = openIcon,
                            progress = {openIconAnimation},
                            modifier = Modifier
                                .size(36.dp)
                                .alpha(0.7f)
                                .clickable(
                                    interactionSource, null,
                                    onClick = {
                                        visible = false
                                        haptics.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                    }
                                )
                                .rotate(180f),
                        )
                    }
                    item {
                        LoginSignUpToggle(
                            selectedScreen = selectedScreen,
                            onScreenSelected = { newScreen -> selectedScreen = newScreen
                                haptics.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)}
                        )
                    }
                    item {
                        when (selectedScreen) {
                            SelectedScreen.Login -> LoginContent(
                                authViewModel = authViewModel,
                                authState = authState,
                                email = loginEmail,
                                onEmailChanged = { loginEmail = it},
                                password = loginPassword,
                                onPasswordChanged = { loginPassword = it},
                                heightSize = heightSize,
                                widthSize = widthSize,
                                navController = navController,
                                userPreferencesRepository = userPreferencesRepository,
                                onGoogleSignInClick = {
                                    launchGoogleSignIn(SelectedScreen.Login)
                                },
                            )
                            SelectedScreen.SignUp -> SignUpContent(
                                onLogin = {selectedScreen = SelectedScreen.Login},
                                email = signUpEmail,
                                onEmailChanged = { signUpEmail = it},
                                password = signUpPassword,
                                onPasswordChanged = { signUpPassword = it},
                                userName = userName,
                                onUsernameChanged = { userName = it},
                                heightSize = heightSize,
                                widthSize = widthSize,
                                authState = authState,
                                authViewModel = authViewModel,
                                navController = navController,
                                userPreferencesRepository = userPreferencesRepository,
                                onGoogleSignInClick = {
                                    launchGoogleSignIn(SelectedScreen.SignUp)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingPageItem(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = page.title,
            maxLines = 2,
            autoSize = TextAutoSize.StepBased(
                maxFontSize = 36.sp
            ),
            style = TextStyle(
                fontFamily = romalio,
                color =MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            ),
        )

        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = page.title,
            modifier = Modifier.size(250.dp)
        )


        Text(
            text = page.description,
            maxLines = 5,
            fontSize = 22.sp,
            style = TextStyle(
                fontFamily = romalio,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            ),
        )
        if (page.description2.isNotEmpty()) {
            Text(
                text = page.description2,
                maxLines = 5,
                fontSize = 22.sp,
                style = TextStyle(
                    fontFamily = romalio,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            val color by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                label = "Indicator Color"
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (isSelected) 12.dp else 8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
fun OnboardingButton(pagerState: PagerState, pageCount: Int, onClick: () -> Unit) {
    val buttonText = if (pagerState.currentPage == pageCount - 1) "GET STARTED" else "NEXT"

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(bottom = 10.dp)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .height(60.dp)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            fontFamily = romalio,
            fontSize = 34.sp,
            color = MaterialTheme.colorScheme.primaryContainer,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3ExpressiveApi::class)
@SuppressLint("UseOfNonLambdaOffsetOverload", "ContextCastToActivity")
@Composable
fun LoginSignUpToggle(
    selectedScreen: SelectedScreen,
    onScreenSelected: (SelectedScreen) -> Unit
) {
    val activity = LocalContext.current as Activity
    val windowSize = calculateWindowSizeClass(activity)
    val widthSize = windowSize.widthSizeClass
    val heightSize = windowSize.heightSizeClass
    val haptics = LocalView.current

    // SDK / Theme checks
    val isAndroid12OrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val interactionSource = remember { MutableInteractionSource() }
    val largeScreen = (widthSize == WindowWidthSizeClass.Expanded || widthSize == WindowWidthSizeClass.Medium) && (heightSize == WindowHeightSizeClass.Expanded || heightSize == WindowHeightSizeClass.Medium)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .height(if (largeScreen) 80.dp else 50.dp)
            .shadow(5.dp, shape = RoundedCornerShape(50))
            .clip(RoundedCornerShape(50))
            .background(
                MaterialTheme.colorScheme.primaryContainer.copy(0.7f)
            )
    ) {

        val maxWidth = this.maxWidth
        val xOffset by animateDpAsState(
            targetValue = if (selectedScreen == SelectedScreen.Login) 0.dp else maxWidth / 2,
            animationSpec = MaterialTheme.motionScheme.fastSpatialSpec(),
            label = "indicatorOffset"
        )

        // Animated text sizes
        val textSizeLogin by animateIntAsState(
            targetValue = if (selectedScreen == SelectedScreen.Login) {
                if (largeScreen) 30 else 20
            } else {
                if (largeScreen) 24 else 14
            },
            animationSpec = MaterialTheme.motionScheme.fastSpatialSpec(),
            label = "loginTextSize"
        )
        val textSizeSign by animateIntAsState(
            targetValue = if (selectedScreen == SelectedScreen.SignUp) {
                if (largeScreen) 30 else 20
            } else {
                if (largeScreen) 24 else 14
            },
            animationSpec = MaterialTheme.motionScheme.fastSpatialSpec(),
            label = "signupTextSize"
        )

        // The draggable overlay
        Box(
            modifier = Modifier
                .width(maxWidth / 2)
                .fillMaxHeight()
                .padding(5.dp)
                .offset(x = xOffset)
                .clip(RoundedCornerShape(50))
                .then(
                    if (isAndroid12OrAbove) {
                        Modifier
                            .blur(5.dp)
                            .border(
                                width = 5.dp,
                                brush = Brush.radialGradient(
                                    colors = listOf(Color.White, Color.White)
                                ),
                                shape = RoundedCornerShape(50)
                            )
                    } else {
                        Modifier
                            .clip(RoundedCornerShape(50))
                            .shadow(15.dp, shape = RoundedCornerShape(50))
                            .background(
                                Color.White.copy(0.7f)
                            )
                    }
                )

        )

        // Clickable texts
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(interactionSource, null) {
                        onScreenSelected(SelectedScreen.Login)
                        haptics.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "LOGIN",
                    fontSize = textSizeLogin.sp,
                    color =
                        if (selectedScreen == SelectedScreen.Login)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primary.copy(0.5f)
                    ,
                    fontWeight = FontWeight.Bold,
                    fontFamily = romalio
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(interactionSource, null) {
                        onScreenSelected(SelectedScreen.SignUp)
                        haptics.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SIGNUP",
                    fontSize = textSizeSign.sp,
                    color =
                        if (selectedScreen == SelectedScreen.SignUp)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primary.copy(0.5f)
                    ,
                    fontWeight = FontWeight.Bold,
                    fontFamily = romalio
                )
            }
        }
    }
}