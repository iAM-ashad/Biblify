package com.example.biblify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.biblify.navigation.BiblifyNavigation
import com.example.biblify.ui.theme.BiblifyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BiblifyTheme {
                BiblifyApp()
            }
        }
    }
}

@Composable
fun BiblifyApp() {
    Surface (
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
    ) {
        BiblifyNavigation()
    }
}
