package org.sopt.play.main.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import androidx.navigationevent.compose.rememberNavigationEventDispatcherOwner
import org.sopt.play.main.presentation.component.MainBottomBar
import org.sopt.play.navigation.MainRoute
import org.sopt.play.navigation.MainTab
import org.sopt.play.ui.preview.ScreenPreviews
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun MainScreen(
    name: String,
    homeUiState: HomeUiState,
    onMenuSelected: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onFavoriteChanged: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: MainRoute = MainRoute.Home
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val selectedTab = MainTab.entries.firstOrNull { tab ->
        currentDestination?.hierarchy?.any { destination ->
            destination.hasRoute(tab.route::class)
        } == true
    } ?: MainTab.HOME

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SoptTheme.colors.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            MainBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    if (tab != selectedTab) {
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            predictivePopEnterTransition = { EnterTransition.None },
            predictivePopExitTransition = { ExitTransition.None }
        ) {
            composable<MainRoute.Home> {
                HomeScreen(
                    name = name,
                    uiState = homeUiState,
                    onMenuSelected = onMenuSelected,
                    onCategorySelected = onCategorySelected,
                    onFavoriteChanged = onFavoriteChanged
                )
            }

            composable<MainRoute.Favorite> {
                PlaceholderTabScreen(title = "찜")
            }

            composable<MainRoute.MyPage> {
                PlaceholderTabScreen(title = "마이페이지")
            }
        }
    }
}

@Composable
private fun PlaceholderTabScreen(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Top + WindowInsetsSides.Horizontal,
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = title,
            color = SoptTheme.colors.textPrimary,
            style = SoptTheme.typography.m18,
        )
    }
}

class MainTabPreviewParameterProvider : PreviewParameterProvider<MainTab> {
    override val values = MainTab.entries.asSequence()

    override fun getDisplayName(index: Int): String = MainTab.entries[index].label
}

@ScreenPreviews
@Composable
private fun MainScreenPreview(
    @PreviewParameter(MainTabPreviewParameterProvider::class) tab: MainTab
) {
    val dispatcherOwner = rememberNavigationEventDispatcherOwner(parent = null)

    SoptTheme {
        CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides dispatcherOwner) {
            MainScreen(
                name = HomeSampleData.userName,
                homeUiState = HomeUiState(),
                onMenuSelected = {},
                onCategorySelected = {},
                onFavoriteChanged = { _, _ -> },
                startDestination = tab.route
            )
        }
    }
}
