package org.sopt.play.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun SoptTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    label: String,
    placeholder: String,
    errorText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    contentType: ContentType? = null
) {
    val interactionSource = remember {MutableInteractionSource()}
    val isFocused by interactionSource.collectIsFocusedAsState()
    val inputColor = soptTextFieldInputColor(errorText)

    SoptFieldLayout(
        label = label,
        errorText = errorText,
        modifier = modifier
    ) {
        BasicTextField(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .soptTextFieldSemantics(
                    label = label,
                    errorText = errorText,
                    autofillContentType = contentType
                ),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = SoptTheme.typography.m18.copy(
                color = inputColor
            ),
            cursorBrush = SolidColor(inputColor),
            keyboardOptions = keyboardOptions,
            onKeyboardAction = onKeyboardAction,
            interactionSource = interactionSource,
            decorator = { innerTextField ->
                SoptFieldDecoration(
                    placeholder = placeholder,
                    isEmpty = state.text.isEmpty(),
                    isFocused = isFocused,
                    errorText = errorText,
                    innerTextField = innerTextField
                )
            }
        )
    }
}

@Composable
internal fun SoptFieldLayout(
    modifier: Modifier = Modifier,
    label: String,
    errorText: String?,
    content: @Composable () -> Unit
) {
    val colors = SoptTheme.colors
    val typography = SoptTheme.typography

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            style = typography.sb16,
            color = colors.textLabel,
            modifier = Modifier.padding(start = 8.dp)
        )

        content()

        if (errorText != null) {
            Text(
                text = errorText,
                style = typography.m14,
                color = colors.error,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
internal fun SoptFieldDecoration(
    modifier: Modifier = Modifier,
    placeholder: String,
    isEmpty: Boolean,
    isFocused: Boolean,
    errorText: String?,
    innerTextField: @Composable () -> Unit
) {
    val colors = SoptTheme.colors
    val shape = SoptTheme.shapes.input

    val borderColor = when {
        errorText != null -> colors.error
        isFocused -> colors.borderFocused
        else -> colors.borderDefault
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp)
            .background(colors.background, shape)
            .border(2.dp, borderColor, shape)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (isEmpty) {
            Text(
                text = placeholder,
                style = SoptTheme.typography.m18,
                color = colors.placeholder,
                maxLines = 1
            )
        }

        innerTextField()
    }
}

@Composable
internal fun soptTextFieldInputColor(
    errorText: String?
): Color {
    val colors = SoptTheme.colors

    return if (errorText != null) {
        colors.textLabel
    } else {
        colors.textInput
    }
}

internal fun Modifier.soptTextFieldSemantics(
    label: String,
    errorText: String?,
    autofillContentType: ContentType?
): Modifier = semantics {
    contentDescription = label

    autofillContentType?.let {
        contentType = it
    }

    errorText?.let {
        error(it)
    }
}