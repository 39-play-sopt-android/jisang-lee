package org.sopt.play.main.presentation

sealed interface HomeIntent {
    data class MenuSelected(val menu: String) : HomeIntent
    data class CategorySelected(val category: String) : HomeIntent
    data class FavoriteChanged(val productId: String, val isFavorite: Boolean) : HomeIntent
}
