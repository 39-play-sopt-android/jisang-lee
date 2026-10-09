package org.sopt.play.auth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R

private val textColor = Color(0xFF23272A)
private val borderColor = Color(0xFFD1D5D6)
private val errorColor = Color(0xFFFF4D4D)
private val fieldShape = RoundedCornerShape(12.dp)

private val labelStyle = TextStyle(
    fontSize = 16.sp,
    lineHeight = 19.2.sp,
    letterSpacing = (-0.16).sp,
    fontFamily = FontFamily(
        Font(R.font.pretendard_semibold)
    ),
    color = textColor,
    platformStyle = PlatformTextStyle(includeFontPadding = false)
)

private val inputStyle = TextStyle(
    fontSize = 18.sp,
    lineHeight = 21.6.sp,
    letterSpacing = (-0.18).sp,
    fontFamily = FontFamily(
        Font(R.font.pretendard_medium)
    ),
    color = textColor,
    platformStyle = PlatformTextStyle(includeFontPadding = false)
)

@Composable
fun EmailAuthTextField(
    state: TextFieldState,
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    AuthFieldLayout(
        title = "이메일 주소",
        modifier = modifier,
        errorText = if (isError) "올바른 이메일을 입력해주세요." else null
    ) {
        BasicTextField(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "이메일 주소"
                    contentType = ContentType.EmailAddress
                },
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = inputStyle,
            cursorBrush = SolidColor(textColor),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                autoCorrectEnabled = false,
            ),
            decorator = { innerTextField ->
                InputBox(
                    placeholder = "abc@email.com",
                    isEmpty = state.text.isEmpty(),
                    isError = isError,
                    innerTextField = innerTextField,
                )
            },
        )
    }
}

@Composable
fun PasswordAuthTextField(
    state: TextFieldState,
    isError: Boolean = false,
    modifier: Modifier = Modifier,
    title: String = "비밀번호",
    errorText: String? = if (isError) {
        "비밀번호는 6자 이상 입력해주세요."
    } else {
        null
    },
) {
    AuthFieldLayout(
        title = title,
        modifier = modifier,
        errorText = errorText
    ) {
        BasicSecureTextField(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "비밀번호"
                    contentType = ContentType.Password
                },
            textStyle = inputStyle,
            cursorBrush = SolidColor(textColor),
            textObfuscationMode = TextObfuscationMode.Hidden,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            decorator = { innerTextField ->
                InputBox(
                    placeholder = "6자 이상의 비밀번호",
                    isEmpty = state.text.isEmpty(),
                    isError = isError,
                    innerTextField = innerTextField,
                )
            },
        )
    }
}

@Composable
fun NameAuthTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
) {
    AuthFieldLayout(
        title = "이름",
        modifier = modifier,
    ) {
        BasicTextField(
            state = state,
            modifier = Modifier.fillMaxWidth(),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = inputStyle,
            cursorBrush = SolidColor(textColor),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            decorator = { innerTextField ->
                InputBox(
                    placeholder = "홍길동",
                    isEmpty = state.text.isEmpty(),
                    innerTextField = innerTextField,
                )
            },
        )
    }
}



@Composable
private fun AuthFieldLayout(
    title: String,
    modifier: Modifier = Modifier,
    errorText: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = title,
            style = labelStyle,
            modifier = Modifier.padding(start = 8.dp),
        )
        content()
        if (errorText != null) {
            Text(
                text = errorText,
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    letterSpacing = (-0.14).sp,
                    fontFamily = FontFamily(
                        Font(R.font.pretendard_medium)
                    ),
                    color = errorColor,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false,
                    ),
                ),
            )
        }
    }
}

@Composable
private fun InputBox(
    placeholder: String,
    isEmpty: Boolean,
    isError: Boolean = false,
    innerTextField: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp)
            .background(Color.White, fieldShape)
            .border(2.dp, if (isError) errorColor else borderColor, fieldShape)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if(isEmpty) {
            Text(
                text = placeholder,
                style = inputStyle,
                color = borderColor,
                maxLines = 1
            )
        }

        innerTextField()
    }
}