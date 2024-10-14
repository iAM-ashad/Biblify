package com.example.biblify.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.biblify.R
import com.example.biblify.navigation.BiblifyScreens
import com.example.biblify.utils.CustomFonts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val offsetX = (maxWidth * 1.1f)
            val offsetY = (maxHeight * -0.03f)

            val textOffsetX = (maxWidth * 0.11f)
            val textOffsetY = (maxHeight * 0.51f)

            val scope = rememberCoroutineScope()
            val colorAnimation = remember {
                androidx.compose.animation.core.Animatable(0f)
            }

            val initialStyle = TextStyle(
                brush = Brush.linearGradient(
                    listOf(
                        Color(229,164,76),
                        Color(247,230,210,255)
                    )
                )
            )
            val finalStyle = TextStyle(
                brush = Brush.linearGradient(
                    listOf(
                        Color(255,158,94,255),
                        Color(86,160,160),
                        Color(255,120,147,255)
                    )
                )
            )

            val textStyle by remember(colorAnimation.value) {
                derivedStateOf {
                    lerp(initialStyle,finalStyle, colorAnimation.value)
                }
            }

            Image(
                painter = painterResource(id = R.drawable.splashscreenbg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column (
                modifier = Modifier
            ) {
                Image(
                    painter = painterResource(id = R.drawable.appicon),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .rotate(73f)
                        .size(130.dp)
                        .offset(x = offsetX, y = offsetY)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontFamily = CustomFonts(GoogleFont("Satisfy")),
                    style = textStyle,
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .rotate(-17f)
                        .offset(x = textOffsetX, y = textOffsetY)
                )
                LaunchedEffect(key1 = true) {
                    delay(1000L)
                    scope.launch {
                       for (i in 1..10) {
                           colorAnimation.snapTo(i/10f)
                           delay(200L)
                       }
                    }
                    delay(2000L)
                    navController.navigate(BiblifyScreens.LoginScreen.name)
                }
            }
        }
    }
}