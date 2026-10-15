package org.sopt.play.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

object SoptPalette {
    val Black = Color(0xFF121212)
    val Gray1 = Color(0xFFF7F7F7)
    val Gray2 = Color(0xFFD1D5D6)
    val Gray3 = Color(0xFFB2BABD)
    val Gray5 = Color(0xFF505559)
    val Gray6 = Color(0xFF23272A)
    val Red = Color(0xFFFF4D4D)
    val White = Color(0xFFFFFFFF)
}

@Immutable
data class SoptColors(
    val background: Color = SoptPalette.White,
    val textPrimary: Color = SoptPalette.Black,
    val textSecondary: Color = SoptPalette.Gray3,
    val textLabel: Color = SoptPalette.Gray6,
    val textInput: Color = SoptPalette.Gray5,
    val placeholder: Color = SoptPalette.Gray2,
    val borderDefault: Color = SoptPalette.Gray2,
    val borderFocused: Color = SoptPalette.Gray5,
    val error: Color = SoptPalette.Red,
    val buttonBackground: Color = SoptPalette.Black,
    val buttonContent: Color = SoptPalette.Gray1,
    val disabledBackground: Color = SoptPalette.Gray1,
    val disabledContent: Color = SoptPalette.Gray3
)