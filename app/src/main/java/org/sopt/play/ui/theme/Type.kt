package org.sopt.play.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.sopt.play.R

private val Pretendard = FontFamily(
    Font(
        resId = R.font.pretendard_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.pretendard_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.pretendard_bold,
        weight = FontWeight.Bold
    )
)

private fun soptTextStyle(
    size: Int,
    weight: FontWeight
): TextStyle = TextStyle(
    fontFamily = Pretendard,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = 1.2.em,
    letterSpacing = (-0.01).em,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
)

@Immutable
data class SoptTypography(
    val b28: TextStyle = soptTextStyle(28, FontWeight.Bold),
    val m18: TextStyle = soptTextStyle(18, FontWeight.Medium),
    val sb16: TextStyle = soptTextStyle(16, FontWeight.SemiBold),
    val m14: TextStyle = soptTextStyle(14, FontWeight.Medium),
    val sb14: TextStyle = soptTextStyle(14, FontWeight.SemiBold)
)

