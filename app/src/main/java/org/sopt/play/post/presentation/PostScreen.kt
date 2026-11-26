package org.sopt.play.post.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.sopt.play.ui.preview.ScreenPreviews
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun PostScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = SoptTheme.colors.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "게시글",
                color = SoptTheme.colors.textPrimary,
                style = SoptTheme.typography.m18
            )
        }
    }
}

@ScreenPreviews
@Composable
private fun PostScreenPreview() {
    SoptTheme {
        PostScreen()
    }
}
