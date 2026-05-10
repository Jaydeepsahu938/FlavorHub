package com.example.flavorhub.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flavorhub.R
import com.example.flavorhub.core.AppDatabase
import com.example.flavorhub.data.AuthRepoImpl
import com.example.flavorhub.presentation.registration.RegistrationViewModelFactory
import com.example.flavorhub.presentation.registration.CustomEditText
import com.example.flavorhub.presentation.registration.RegistrationEffect
import com.example.flavorhub.presentation.registration.RegistrationIntent
import com.example.flavorhub.presentation.registration.RegistrationViewModel
import com.example.flavorhub.presentation.welcome.CustomButton

@Composable
fun LoginScreen(modifier: Modifier,onRegisterClicked: () -> Unit, onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    val repo = AuthRepoImpl(AppDatabase.getInstance(context).userDao())
    val viewModel: RegistrationViewModel = viewModel(
        factory = RegistrationViewModelFactory(repo)
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when(it) {
                RegistrationEffect.NavigateToHome -> onLoginSuccess()
                RegistrationEffect.NavigateToLogin -> onRegisterClicked()
                is RegistrationEffect.showError -> {

                }
            }
        }
    }

    Box(
    modifier = modifier.imePadding()
        ,
    )
    {
        Box(Modifier.align(Alignment.TopEnd).offset(x = (200).dp, y = (-250).dp)
            .size(500.dp).background(color = Color(0xffFEF2F2), shape = CircleShape))
        Box(Modifier.align(Alignment.BottomStart).offset(x = (-200).dp, y = (250.dp))
            .size(500.dp).background(color = Color(0xffFEF2F2), shape = CircleShape))
        Text(
            text = "FlavorHub",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xffA43700),
            modifier = Modifier.padding(top = 30.dp, start = 20.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
                .background(
                    color = Color.Transparent,
                )
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Welcome Back",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = "Login to continue your journey",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color(0xff5f5e5e),
                modifier = Modifier.padding(top = 2.dp, start = 4.dp, bottom = 20.dp)
            )
            CustomEditText(
                Modifier.fillMaxWidth(),
                "Email Address",
                "Enter your email",
                icon = R.drawable.email__icon,
                onTextChange = {}
            )

            Spacer(Modifier.height(18.dp))
            CustomEditText(
                Modifier.fillMaxWidth(),
                "Password",
                "Enter your password",
                icon = R.drawable.passkey_icon,
                onTextChange = {}
            )
            Spacer(Modifier.height(30.dp))
            CustomButton(Modifier.fillMaxWidth(), "Login", Color(0xffA43700), Color.White) {
                viewModel.onIntent(RegistrationIntent.OnLoginClicked)
            }
            Text(
                text = buildAnnotatedString {
                    append("Do'nt have an acount?")
                    withStyle(style = SpanStyle(color = Color(0xffA43700))) {
                        append(" Register")
                    }
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color(0xff5f5e5e),
                modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
//    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//        LoginScreen(modifier = Modifier.padding(innerPadding), backStack = rememberNavBackStack(Welcome))
//    }
}