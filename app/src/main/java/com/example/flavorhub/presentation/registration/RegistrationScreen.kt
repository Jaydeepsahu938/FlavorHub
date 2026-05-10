package com.example.flavorhub.presentation.registration

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.flavorhub.presentation.welcome.CustomButton

@Composable
fun RegistrationScreen(modifier: Modifier, onNavigateToLogin: () -> Unit, onNavigateToHome: () -> Unit) {
    val context = LocalContext.current
    val repo = AuthRepoImpl(AppDatabase.getInstance(context).userDao())
    val viewModel: RegistrationViewModel = viewModel(
        factory = RegistrationViewModelFactory(repo)
    )
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
               when(effect) {
                   RegistrationEffect.NavigateToHome ->onNavigateToHome()
                   RegistrationEffect.NavigateToLogin -> onNavigateToLogin()
                   is RegistrationEffect.showError -> {
                       Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                   }
               }
        }
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
            .imePadding(),
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(0.4f),
            contentAlignment = Alignment.BottomStart
        )
        {
            Image(
                painter = painterResource(id = R.drawable.registation_screen_image),
                contentDescription = "registration image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = "FlavorHub",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xffA43700),
                modifier = Modifier.padding(bottom = 20.dp, start = 20.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(y = (-20).dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp
                    )
                )
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Create Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp, bottom = 20.dp)
            )
           CustomEditText(
                Modifier.fillMaxWidth(), "Full Name", "Enter your name", icon = R.drawable.outline_person_24, onTextChange = {
                    viewModel.onIntent(RegistrationIntent.NameChanged(it))
               }
            )
            Spacer(Modifier.height(12.dp))
            CustomEditText(
                Modifier.fillMaxWidth(), "Email Address", "Enter your email", icon = R.drawable.email__icon,
                onTextChange = {
                    viewModel.onIntent(RegistrationIntent.EmailChanged(it))
                }
            )
            Spacer(Modifier.height(12.dp))
            CustomEditText(
                Modifier.fillMaxWidth(), "Phone Number", "Enter your number", icon = R.drawable.phone_icon,
                onTextChange = {
                    viewModel.onIntent(RegistrationIntent.PhoneChanged(it))
                }
            )
            Spacer(Modifier.height(12.dp))
            CustomEditText(
                Modifier.fillMaxWidth(), "Password", "Enter your password", icon = R.drawable.passkey_icon,
                onTextChange = {
                    viewModel.onIntent(RegistrationIntent.PasswordChanged(it))
                }
            )
            Spacer(Modifier.height(20.dp))
            CustomButton(Modifier.fillMaxWidth(), "Register", Color(0xffA43700), Color.White) {
                 viewModel.onIntent(RegistrationIntent.OnRegisterClicked)
            }
            Text(
                text =  buildAnnotatedString {
                    append("Already have an account?")
                    withStyle(style = SpanStyle(color = Color(0xffA43700))) {
                        append(" Login")
                    }
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color(0xff5f5e5e),
                modifier = Modifier.fillMaxWidth().clickable{
                    viewModel.onIntent(RegistrationIntent.OnLoginClicked)
                }.padding(top = 20.dp),
            )
        }
    }
}

@Composable
fun CustomEditText(modifier: Modifier, heading: String,label: String,icon: Int,onTextChange: (String)-> Unit) {
    val text = remember { mutableStateOf("") }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = heading,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black,
        )
        Spacer(Modifier.height(4.dp))
        TextField(
            value =  text.value,
            onValueChange = {
                text.value = it
                onTextChange(text.value)
            },
            modifier = Modifier.fillMaxWidth().background(color = Color(0xfff3f3f3), shape = RoundedCornerShape(8.dp)),
            leadingIcon = {
                Image(
                    painter = painterResource(icon),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp),
                    contentScale = ContentScale.Fit,
                )
            },
            placeholder = {
                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xffA5A4A3),
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xfff3f3f3),
                unfocusedContainerColor = Color(0xfff3f3f3),
            ),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RegistrationScreenPreview() {
}
