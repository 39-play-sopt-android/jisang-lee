package org.sopt.play.main.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            selectedMenu = savedStateHandle["selectedMenu"] ?: "홈",
            selectedCategory = savedStateHandle["selectedCategory"] ?: "전체"
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onMenuSelected(menu: String) {
        savedStateHandle["selectedMenu"] = menu
        _uiState.update { state ->
            state.copy(selectedMenu = menu)
        }
    }

    fun onCategorySelected(category: String) {
        savedStateHandle["selectedCategory"] = category
        _uiState.update { state ->
            state.copy(selectedCategory = category)
        }
    }

    fun onFavoriteChanged(productId: String, isFavorite: Boolean) {
        _uiState.update { state ->
            state.copy(
                products = state.products.map { product ->
                    if (product.id == productId) {
                        product.copy(isFavorite = isFavorite)
                    } else {
                        product
                    }
                }
            )
        }
    }
}
