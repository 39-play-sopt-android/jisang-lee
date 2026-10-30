package org.sopt.play.navigation

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable
import org.sopt.play.R

sealed interface MainRoute {
    @Serializable
    data object Home : MainRoute

    @Serializable
    data object Favorite : MainRoute

    @Serializable
    data object MyPage : MainRoute
}

enum class MainTab(
    val route: MainRoute,
    val label: String,
    @DrawableRes val iconRes: Int,
    @DrawableRes val selectedIconRes: Int
) {
    HOME(
        route = MainRoute.Home,
        label = "홈",
        iconRes = R.drawable.ic_home_outline,
        selectedIconRes = R.drawable.ic_home_filled
    ),
    FAVORITE(
        route = MainRoute.Favorite,
        label = "찜",
        iconRes = R.drawable.ic_favorite_outline,
        selectedIconRes = R.drawable.ic_favorite_filled
    ),
    MY_PAGE(
        route = MainRoute.MyPage,
        label = "마이페이지",
        iconRes = R.drawable.ic_user_outline,
        selectedIconRes = R.drawable.ic_user_fill
    )
}
