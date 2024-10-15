package com.example.biblify.screens.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.biblify.R
import com.example.biblify.navigation.BiblifyScreens
import com.example.biblify.screens.register.UsersForm
import com.example.biblify.utils.customFonts

@Composable
fun LoginScreen(navController: NavController) {

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 80.sp,
            fontFamily = customFonts(GoogleFont("Great Vibes")),
            color = Color(240,205,156),
            modifier = Modifier
                .padding(5.dp)
        )
        UsersForm("Login") {email, password ->
            Log.d("TAG", "$password, $email")
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(
                text = "New User?"
            )
            Text(
                text = " Sign Up",
                color =  Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        navController.navigate(BiblifyScreens.CreateAccountScreen.name)
                    }
            )
        }
    }
}