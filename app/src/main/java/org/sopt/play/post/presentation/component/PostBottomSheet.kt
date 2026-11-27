package org.sopt.play.post.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.component.SoptPrimaryButton
import org.sopt.play.ui.component.SoptTextField
import org.sopt.play.ui.theme.SoptPalette
import org.sopt.play.ui.theme.SoptTheme

private const val MAX_TITLE_LENGTH = 20
private const val MAX_CONTENT_LENGTH = 1000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostBottomSheet(
    title: TextFieldState,
    content: TextFieldState,
    isCreateEnabled: Boolean,
    onDismissRequest: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = SoptTheme.colors.background,
        scrimColor = Color.Black.copy(alpha = 0.4f),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 20.dp)
                    .size(width = 36.dp, height = 4.dp)
                    .background(
                        color = SoptPalette.Gray3,
                        shape = CircleShape
                    )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SoptTextField(
                    state = title,
                    label = "제목",
                    placeholder = "제목을 입력하세요.",
                    inputTransformation = InputTransformation.maxLength(MAX_TITLE_LENGTH),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                SoptTextField(
                    state = content,
                    label = "본문",
                    placeholder = "본문을 입력하세요.",
                    inputTransformation = InputTransformation.maxLength(MAX_CONTENT_LENGTH),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }

            SoptPrimaryButton(
                text = "완료",
                onClick = onCreateClick,
                enabled = isCreateEnabled
            )
        }
    }
}
