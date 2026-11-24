package org.sopt.play.main.presentation.model

import androidx.annotation.DrawableRes

data class HomeProduct(
    val id: String,
    @param:DrawableRes
    val imageRes: Int,
    val storeName: String,
    val productName: String,
    val discountPercent: Int,
    val price: Int,
    val isFavorite: Boolean = false
)
