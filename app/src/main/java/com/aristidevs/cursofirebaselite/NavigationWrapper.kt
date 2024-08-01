package com.aristidevs.cursofirebaselite

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aristidevs.cursofirebaselite.presentation.initial.InitialScreen
import com.aristidevs.cursofirebaselite.presentation.login.LoginScreen
import com.aristidevs.cursofirebaselite.presentation.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth) {

    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignUp = { navHostController.navigate("signUp") })
        }
        composable("logIn") {
            LoginScreen(auth)
        }
        composable("signUp") {
            SignUpScreen(auth)
        }
    }
}