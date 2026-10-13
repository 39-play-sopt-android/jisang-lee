package org.sopt.play.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun SoptSecureTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    label: String,
    placeholder: String,
    errorText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done,
        autoCorrectEnabled = false
    ),
    onKeyboardAction: KeyboardActionHandler? = null,
    contentType: ContentType? = ContentType.Password
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val inputColor = soptTextFieldInputColor(errorText)

    SoptFieldLayout(
        label = label,
        errorText = errorText,
        modifier = modifier
    ) {
        BasicSecureTextField(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .soptTextFieldSemantics(
                    label = label,
                    errorText = errorText,
                    autofillContentType = contentType
                ),
            textStyle = SoptTheme.typography.m18.copy(
                color = inputColor
            ),
            cursorBrush = SolidColor(inputColor),
            textObfuscationMode = TextObfuscationMode.Hidden,
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