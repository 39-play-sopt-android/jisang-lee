package org.sopt.play.main.presentation

import org.sopt.play.main.presentation.model.HomeProduct

data class HomeUiState(
    val products: List<HomeProduct> = HomeSampleData.products,
    val selectedMenu: String = "홈",
    val selectedCategory: String = "전체"
)
