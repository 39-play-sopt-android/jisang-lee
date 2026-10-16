package org.sopt.play.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object Auth : AppRoute

    @Serializable
    data object Login : AppRoute

    @Serializable
    data object Register : AppRoute

    @Serializable
    data object Main : AppRoute
}