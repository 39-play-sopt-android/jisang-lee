package org.sopt.play.post.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import org.sopt.play.ui.component.SoptPrimaryButton
import org.sopt.play.ui.theme.SoptPalette
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun PostDeleteDialog(
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val dialogWindow = (LocalView.current.parent as? DialogWindowProvider)?.window
        SideEffect {
            dialogWindow?.setDimAmount(0.4f)
        }

        Column(
            modifier = modifier
                .fillMaxWidth(0.8f)
                .background(
                    color = SoptTheme.colors.background,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "게시글을 삭제할까요?",
                modifier = Modifier.fillMaxWidth(),
                color = SoptTheme.colors.textLabel,
                style = SoptTheme.typography.m18,
                textAlign = TextAlign.Center
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 49.dp),
                    shape = SoptTheme.shapes.button,
                    contentPadding = PaddingValues(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoptPalette.Gray1,
                        contentColor = SoptPalette.Gray5
                    )
                ) {
                    Text(
                        text = "취소",
                        style = SoptTheme.typography.sb14
                    )
                }

                SoptPrimaryButton(
                    text = "삭제",
                    onClick = onDeleteClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
