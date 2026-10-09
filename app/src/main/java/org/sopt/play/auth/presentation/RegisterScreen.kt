package org.sopt.play.auth.presentation

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.auth.presentation.component.EmailAuthTextField
import org.sopt.play.auth.presentation.component.NameAuthTextField
import org.sopt.play.auth.presentation.component.PasswordAuthTextField
import org.sopt.play.ui.component.PlayPrimaryButton

@Composable
fun RegisterScreen(
    onRegister: (name: String, email: String, password: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val name = rememberTextFieldState()
    val email = rememberTextFieldState()
    val password = remember { TextFieldState() }
    val passwordConfirm = remember { TextFieldState() }

    val nameText = name.text.toString().trim()
    val emailText = email.text.toString().trim()
    val passwordText = password.text.toString()
    val confirmText = passwordConfirm.text.toString()

    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailText).matches()
    val isEmailError = emailText.isNotEmpty() && !isEmailValid
    val isPasswordError = passwordText.isNotEmpty() && passwordText.length < 6
    val isConfirmError = confirmText.isNotEmpty() && confirmText != passwordText

    val canRegister =
        nameText.isNotEmpty() &&
                isEmailValid &&
                passwordText.length >= 6 &&
                confirmText == passwordText

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = 112.dp, bottom = 24.dp),
    ) {
        Text(
            text = "이메일로 회원가입",
            style = TextStyle(
                fontSize = 28.sp,
                lineHeight = 33.6.sp,
                letterSpacing = (-0.28).sp,
                fontFamily = FontFamily(
                    Font(R.font.pretendard_bold, FontWeight.Bold)
                ),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF121212),
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
        )

        Spacer(Modifier.height(40.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            NameAuthTextField(state = name)

            EmailAuthTextField(
                state = email,
                isError = isEmailError,
            )

            PasswordAuthTextField(
                state = password,
                isError = isPasswordError,
            )

            PasswordAuthTextField(
                state = passwordConfirm,
                title = "비밀번호 확인",
                isError = isConfirmError,
                errorText = if (isConfirmError) {
                    "비밀번호가 일치하지 않아요."
                } else {
                    null
                },
            )
        }

        Spacer(Modifier.height(40.dp))

        PlayPrimaryButton(
            text = "회원가입",
            enabled = canRegister,
            onClick = {
                onRegister(nameText, emailText, passwordText)
            },
        )
    }
}