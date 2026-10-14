package org.sopt.play.auth.presentation
import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.auth.presentation.component.EmailAuthTextField
import org.sopt.play.auth.presentation.component.PasswordAuthTextField
import org.sopt.play.ui.component.SoptPrimaryButton
import org.sopt.play.ui.theme.SoptTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier,
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

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = SoptTheme.colors.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 60.dp)
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
    }
}

@Composable
private fun SignUpPrompt(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
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
            modifier = Modifier.clickable(onClick = onClick),
            style = textStyle,
            color = colors.textLabel,
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    SoptTheme {
        LoginScreen(
            onLogin = { _, _ -> },
            onSignUp = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPromptPreview() {
    SoptTheme {
        SignUpPrompt(onClick = { })
    }
}