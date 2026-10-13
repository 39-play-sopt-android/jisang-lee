package org.sopt.play.auth.presentation
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sopt.play.auth.presentation.component.EmailAuthTextField
import org.sopt.play.auth.presentation.component.PasswordAuthTextField
import org.sopt.play.ui.component.SoptPrimaryButton
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit,
    onSignUp: () -> Unit,
) {
    val email = rememberTextFieldState()
    val password = rememberTextFieldState()

    val emailText = email.text.toString().trim()
    val passwordText = password.text.toString()

    val isEmailValid = Patterns.EMAIL_ADDRESS
        .matcher(emailText)
        .matches()

    val emailError = when {
        emailText.isEmpty() || isEmailValid -> null
        else -> "올바른 이메일을 입력해주세요."
    }

    val passwordError = when {
        passwordText.isEmpty() || passwordText.length >= 6 -> null
        else -> "비밀번호는 6자 이상 입력해주세요."
    }

    val canLogin = isEmailValid && passwordText.length >= 6

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SoptTheme.colors.background)
            .padding(horizontal = 16.dp)
            .padding(top = 112.dp),
    ) {
        Text(
            text = "이메일로 로그인하기",
            style = SoptTheme.typography.b28,
            color = SoptTheme.colors.textPrimary
        )

        Spacer(Modifier.height(40.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            EmailAuthTextField(
                state = email,
                errorText = emailError
            )

            PasswordAuthTextField(
                state = password,
                errorText = passwordError
            )
        }

        Spacer(Modifier.height(40.dp))

        SoptPrimaryButton(
            text = "로그인",
            enabled = canLogin,
            onClick = {
                onLogin(emailText, passwordText)
            }
        )

        Spacer(Modifier.height(20.dp))

        SignUpPrompt(onClick = onSignUp)
    }
}

@Composable
private fun SignUpPrompt(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = SoptTheme.colors
    val textStyle = SoptTheme.typography.m14

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            12.dp,
            Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "아직 계정이 없으신가요?",
            style = textStyle,
            color = colors.textSecondary
        )

        Text(
            text = "회원가입하기",
            style = textStyle,
            color = colors.textLabel,
            modifier = Modifier.clickable(onClick = onClick),
        )
    }
}