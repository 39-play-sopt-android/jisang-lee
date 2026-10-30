package org.sopt.play.auth.presentation

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
    uiState: LoginUiState,
    email: TextFieldState,
    password: TextFieldState,
    onLogin: () -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                    .verticalScroll(rememberScrollState())
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
                        errorText = uiState.emailError
                    )

                    PasswordAuthTextField(
                        state = password,
                        errorText = uiState.passwordError
                    )
                }

                Spacer(Modifier.height(40.dp))

                SoptPrimaryButton(
                    text = "로그인",
                    enabled = uiState.canLogin,
                    onClick = onLogin
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
            uiState = LoginUiState(),
            email = rememberTextFieldState(),
            password = rememberTextFieldState(),
            onLogin = {},
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
