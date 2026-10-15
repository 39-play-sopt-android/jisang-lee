package org.sopt.play.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
data class SoptShapes(
    val input: CornerBasedShape = RoundedCornerShape(12.dp),
    val button: CornerBasedShape = RoundedCornerShape(100.dp)
)