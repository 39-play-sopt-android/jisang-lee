package org.sopt.play.post.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun PostSearchBar(
    state: TextFieldState,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val search = {
        keyboardController?.hide()
        onSearch()
    }

    BasicTextField(
        state = state,
        modifier = modifier.fillMaxWidth(),
        lineLimits = TextFieldLineLimits.SingleLine,
        textStyle = SoptTheme.typography.m18.copy(
            color = SoptTheme.colors.textInput
        ),
        cursorBrush = SolidColor(SoptTheme.colors.textInput),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        onKeyboardAction = { search() },
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = SoptTheme.colors.borderDefault,
                        shape = SoptTheme.shapes.input
                    )
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.text.isEmpty()) {
                        Text(
                            text = "검색어를 입력하세요",
                            color = SoptTheme.colors.placeholder,
                            style = SoptTheme.typography.m18,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    innerTextField()
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "검색",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = search),
                    tint = SoptTheme.colors.textSecondary
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PostSearchBarPreview() {
    SoptTheme {
        PostSearchBar(
            state = rememberTextFieldState(),
            onSearch = {}
        )
    }
}
