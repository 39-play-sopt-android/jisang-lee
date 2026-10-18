package org.sopt.play.auth.presentation.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import org.sopt.play.ui.component.SoptSecureTextField
import org.sopt.play.ui.component.SoptTextField

@Composable
fun EmailAuthTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    errorText: String? = null,
    onKeyboardAction: KeyboardActionHandler? = null
) {
    SoptTextField(
        state = state,
        label = "이메일 주소",
        placeholder = "abc@email.com",
        modifier = modifier,
        errorText = errorText,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            autoCorrectEnabled = false
        ),
        onKeyboardAction = onKeyboardAction,
        contentType = ContentType.EmailAddress
    )
}

@Composable
fun PasswordAuthTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    title: String = "비밀번호",
    errorText: String? = null,
    imeAction: ImeAction = ImeAction.Done,
    onKeyboardAction: KeyboardActionHandler? = null
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SoptSecureTextField(
        state = state,
        label = title,
        placeholder = "6자 이상의 비밀번호",
        modifier = modifier,
        errorText = errorText,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
            autoCorrectEnabled = false
        ),
        onKeyboardAction = onKeyboardAction ?: KeyboardActionHandler { performDefaultAction ->
            if (imeAction == ImeAction.Done) {
                focusManager.clearFocus()
                keyboardController?.hide()
            } else {
                performDefaultAction()
            }
        },
        contentType = ContentType.Password
    )
}

@Composable
fun NameAuthTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    errorText: String? = null,
    onKeyboardAction: KeyboardActionHandler? = null
) {
    SoptTextField(
        state = state,
        label = "이름",
        placeholder = "홍길동",
        modifier = modifier,
        errorText = errorText,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onKeyboardAction = onKeyboardAction
    )
}