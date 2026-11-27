package org.sopt.play.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.play.auth.data.AuthRepository
import org.sopt.play.auth.navigation.authNavGraph
import org.sopt.play.main.presentation.HomeIntent
import org.sopt.play.main.presentation.HomeViewModel
import org.sopt.play.main.presentation.MainScreen
import org.sopt.play.post.data.PostRepository
import org.sopt.play.post.presentation.PostRoute

@Composable
fun AppNavHost(
    authRepository: AuthRepository,
    postRepository: PostRepository,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

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
            repository = authRepository,
            onLogin = {
                navController.navigate(AppRoute.Main) {
                    popUpTo<AppRoute.Auth> {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )

        composable<AppRoute.Main> {
            val homeViewModel: HomeViewModel = viewModel()
            val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
            val user by authRepository.currentUser.collectAsStateWithLifecycle()

            LaunchedEffect(user) {
                if (user == null) {
                    navController.navigate(AppRoute.Auth) {
                        popUpTo<AppRoute.Main> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }

            user?.let { currentUser ->
                MainScreen(
                    name = currentUser.name,
                    homeUiState = homeUiState,
                    onSearchClick = {
                        navController.navigate(AppRoute.Post) {
                            launchSingleTop = true
                        }
                    },
                    onMenuSelected = { menu ->
                        homeViewModel.onIntent(HomeIntent.MenuSelected(menu))
                    },
                    onCategorySelected = { category ->
                        homeViewModel.onIntent(HomeIntent.CategorySelected(category))
                    },
                    onFavoriteChanged = { productId, isFavorite ->
                        homeViewModel.onIntent(HomeIntent.FavoriteChanged(productId, isFavorite))
                    }
                )
            }
        }

        composable<AppRoute.Post> {
            PostRoute(
                postRepository = postRepository,
                authRepository = authRepository
            )
        }
    }
}
