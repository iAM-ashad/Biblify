package com.example.biblify.screens.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.biblify.R
import com.example.biblify.navigation.BiblifyScreens
import com.example.biblify.utils.customFonts

@Composable
fun CreateAccountScreen(navController: NavController) {

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
        UsersForm("Register") {email, password ->
            Log.d("TAG", "$password, $email")
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(
                text = "Already Have an Account?"
            )
            Text(
                text = " Sign In",
                color =  Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        navController.navigate(BiblifyScreens.LoginScreen.name)
                    }
            )
        }
    }
}

@Composable
fun UsersForm (
    buttonText: String,
    onDone: (String, String) -> Unit = {email, password ->}
) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val isVisible = rememberSaveable {
        mutableStateOf(false)
    }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        EmailInputField(
            emailState = email,
            onAction = KeyboardActions{
                passwordFocusRequest.requestFocus()
            },
            modifier = Modifier
                .padding(top = 20.dp, bottom = 10.dp)
        )
        PasswordInputField(
            passwordState = password,
            isVisible = isVisible,
            modifier = Modifier
                .padding(bottom = 15.dp)
        )
        LoginRegisterButton (
            buttonText = buttonText,
            validInputs = valid,
            modifier = Modifier
                .padding(bottom = 20.dp)
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }

}

@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    onAction: KeyboardActions = KeyboardActions.Default,
) {

        OutlinedTextField(
            value = emailState.value,
            onValueChange = {
                emailState.value = it
            },
            label = {
                Text(
                    text = "Email"
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = onAction,
            maxLines = 1,
            shape = RoundedCornerShape(35.dp),
            modifier = modifier
        )
}

@Composable
fun PasswordInputField(
    modifier: Modifier,
    passwordState: MutableState<String>,
    isVisible: MutableState<Boolean>,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = {
            Text(
                text = "Password"
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null
            )
        },
        trailingIcon = {
            when (isVisible.value) {
                true -> Image(
                    painter = painterResource(R.drawable.eyeopen),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            isVisible.value = !isVisible.value
                        }
                )
                else -> Image(
                    painter = painterResource(id = R.drawable.eyeclose),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            isVisible.value = !isVisible.value
                        }
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        visualTransformation = when (isVisible.value) {
            true -> VisualTransformation.None
            else -> PasswordVisualTransformation()
        },
        keyboardActions = onAction,
        maxLines = 1,
        shape = RoundedCornerShape(35.dp),
        modifier = modifier
    )
}
@Composable
fun LoginRegisterButton(
    buttonText: String,
    validInputs: Boolean,
    modifier: Modifier,
    onButtonClicked: () -> Unit,
) {
    Button(
        onClick = onButtonClicked,
        shape = RoundedCornerShape(35.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 15.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(226,180,117,255),
            disabledContainerColor = Color(241,223,200,255)

        ),
        enabled = validInputs,
        modifier = modifier
    ) {
        Text(
            text = buttonText,
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}