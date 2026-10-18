package org.sopt.play.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.theme.SoptTheme
import org.sopt.play.ui.theme.SoptTheme.colors

@Composable
fun SoptPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        Button(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 49.dp),
            enabled = enabled,
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
}

@Preview(showBackground = true)
@Composable
private fun SoptPrimaryButtonPreview() {
    SoptTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SoptPrimaryButton(
                text = "이것은 버튼이다",
                onClick = {}
            )

            SoptPrimaryButton(
                text = "누를 수 없는 버튼은 버튼인가?",
                onClick = {},
                enabled = false
            )
        }
    }
}