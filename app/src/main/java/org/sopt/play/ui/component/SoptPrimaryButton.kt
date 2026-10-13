package org.sopt.play.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.theme.SoptTheme
import org.sopt.play.ui.theme.SoptTheme.colors

@Composable
fun SoptPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 49.dp),
        shape = SoptTheme.shapes.button,
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.buttonBackground,
            contentColor = colors.buttonContent,
            disabledContainerColor = colors.disabledBackground,
            disabledContentColor = colors.disabledContent
        )
    ) {
        Text(
            text = text,
            style = SoptTheme.typography.sb14
        )
    }
}