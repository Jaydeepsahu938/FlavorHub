package com.example.flavorhub.presentation.welcome


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flavorhub.R
import com.example.flavorhub.presentation.welcome.WelcomeViewModel

@Composable
fun WelcomeScreen(modifier: Modifier, onNavigateToLogin: () -> Unit, onNavigateToRegister: () -> Unit) {
    val viewModel : WelcomeViewModel = viewModel()
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {

                WelcomeEffect.NavigateToLogin -> {
                    onNavigateToLogin()
                }

                WelcomeEffect.NavigateToRegister -> {
                    onNavigateToRegister()
                }
            }
        }
    }
    Column(
        modifier = modifier
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f))
        {
            Image(
                painter = painterResource(id = R.drawable.welcome_image),
                contentDescription = "welcome image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = "FlavorHub",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xffEC8450),
                modifier = Modifier.padding(top = 20.dp, start = 20.dp)
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
                .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Discover the best\nflavors near you.",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = TextUnit(40f, TextUnitType.Sp)
            )
            Text(
                text = "Experience lightning-fast delivery from the city's top-rated restaurants and hidden culinary gems.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color(0xff5f5e5e),
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(Modifier.height(24.dp))
            CustomButton(Modifier.fillMaxWidth(),"Register",Color(0xffA43700),Color.White){
               viewModel.onIntent(WelcomeIntent.RegisterClicked)
            }
            Spacer(Modifier.height(16.dp))
            CustomButton(Modifier.fillMaxWidth(),"Login",Color(0xffeeeeee),Color(0xff1a1c1c)){
                viewModel.onIntent(WelcomeIntent.LoginClicked)
            }
            Text(
                text = "Made in India",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color(0xff5f5e5e),
                modifier = Modifier.padding(top = 40.dp)
            )
        }
    }
}

@Composable
fun CustomButton(modifier: Modifier, text: String, backgroundColor: Color, textColor: Color,onClick: () -> Unit) {
    Box(modifier = modifier
        .clickable{
            onClick()
        }
        .height(52.dp)
        .background(color = backgroundColor, shape = RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center){
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = textColor,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomeScreenPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//        WelcomeScreen(modifier = Modifier.padding(innerPadding), backStack = rememberNavBackStack(Welcome))
    }
}