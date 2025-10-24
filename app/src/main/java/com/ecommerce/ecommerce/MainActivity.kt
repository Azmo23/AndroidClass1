package com.ecommerce.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecommerce.ecommerce.models.AuthResponse
import com.ecommerce.ecommerce.ui.screens.LoginScreen
import com.ecommerce.ecommerce.ui.screens.RegisterScreen
import com.ecommerce.ecommerce.ui.screens.WelcomeScreen
import com.ecommerce.ecommerce.ui.theme.EcommerceAppTheme
import com.ecommerce.ecommerce.utils.SessionManager
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {


    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        sessionManager = SessionManager(this)


        setContent {
            EcommerceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EcommerceApp(
                        sessionManager = sessionManager,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun EcommerceApp(
    sessionManager: SessionManager,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val isLoggedIn by sessionManager.isLoggedIn.collectAsState(initial = false)


    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "welcome" else "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { authResponse ->
                    // Guardar sesión con los datos del AuthResponse
                    val context = navController.context
                    if (context is ComponentActivity) {
                        context.lifecycleScope.launch {
                            sessionManager.saveUserSession(
                                token = authResponse.token!!,
                                userName = authResponse.user!!.UserName,
                                userEmail = authResponse.user!!.Email,
                                isAdmin = authResponse.isAdmin(),
                                roles = authResponse.getRoleNames()
                            )


                            // Navegar a welcome después de guardar la sesión
                            navController.navigate("welcome") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                registeredEmail = navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<String>("registered_email")
            )
        }


        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { email ->
                    // Guardar el email para pre-llenarlo en login
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("registered_email", email)


                    // Navegar al login
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }


        composable("welcome") {
            WelcomeScreen(
                sessionManager = sessionManager,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }
    }
}
