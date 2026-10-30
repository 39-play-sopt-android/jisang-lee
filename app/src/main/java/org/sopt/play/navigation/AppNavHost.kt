package org.sopt.play.navigation

import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.play.auth.navigation.authNavGraph
import org.sopt.play.main.presentation.MainScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    var registeredEmail by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var registeredPassword by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var registeredName by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    NavHost(
        navController = navController,
        startDestination = AppRoute.Auth,
        modifier = modifier.fillMaxSize(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        predictivePopEnterTransition = { EnterTransition.None },
        predictivePopExitTransition = { ExitTransition.None }
    ) {
        authNavGraph(
            navController = navController,
            onLogin = { email, password ->
                val isMatched = email.trim() == registeredEmail &&
                        password == registeredPassword

                if (isMatched) {
                    navController.navigate(AppRoute.Main) {
                        popUpTo<AppRoute.Auth> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }

                    registeredEmail = null
                    registeredPassword = null
                } else {
                    Toast.makeText(
                        context,
                        "이메일 또는 비밀번호가 올바르지 않아요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            // 이름은 그냥 안써버리기 해놨는데 바로 메인에서 써버리네요;;
            onRegister = { name, email, password ->
                registeredEmail = email.trim()
                registeredPassword = password
                registeredName = name

                navController.popBackStack<AppRoute.Login>(
                    inclusive = false
                )
            }
        )

        composable<AppRoute.Main> {
            MainScreen(name = registeredName?:"")
        }
    }
}