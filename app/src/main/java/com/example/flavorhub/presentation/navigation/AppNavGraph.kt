package com.example.flavorhub.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.flavorhub.presentation.home.HiddenRestaurantScreen
import com.example.flavorhub.presentation.home.HomeScreen
import com.example.flavorhub.presentation.login.LoginScreen
import com.example.flavorhub.presentation.registration.RegistrationScreen
import com.example.flavorhub.presentation.restaurant_details.RestaurantDetailsScreen
import com.example.flavorhub.presentation.welcome.WelcomeScreen

@Composable
fun AppNavGraph() {
    val backStack = rememberNavBackStack(Welcome)

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Welcome> {
                WelcomeScreen(
                    Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    onNavigateToLogin = {
                        backStack.add(Login)
                                        },
                    onNavigateToRegister = {
                        backStack.add(Registration)
                    }
                )
            }
            entry<Registration> {
                RegistrationScreen(
                    Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    onNavigateToLogin = {backStack.add(Login)},
                    onNavigateToHome = {backStack.add(Home)}
                )
            }
            entry<Login> {
                LoginScreen(
                    Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    onRegisterClicked = {
                        backStack.add(Registration)
                    },
                    onLoginSuccess = {
                        backStack.add(Home)
                    },
                )
            }
            entry<Home> {
                HomeScreen(Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()){
                    backStack.add(RestaurantDetail(
                        id = it
                    ))
                }
            }
            entry<HiddenRestaurant> {
                HiddenRestaurantScreen(Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),backStack)
            }
            entry<RestaurantDetail> {
                RestaurantDetailsScreen(Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),it.id)
            }
        }
    )

}