package org.sopt.play.auth.presentation

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.auth.presentation.component.EmailAuthTextField
import org.sopt.play.auth.presentation.component.NameAuthTextField
import org.sopt.play.auth.presentation.component.PasswordAuthTextField
import org.sopt.play.ui.component.SoptPrimaryButton
import org.sopt.play.ui.theme.SoptTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegister: (name: String, email: String, password: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val name = rememberTextFieldState()
    val email = rememberTextFieldState()
    val password = rememberTextFieldState()
    val passwordConfirm = rememberTextFieldState()

    val nameText = name.text.toString().trim()
    val emailText = email.text.toString().trim()
    val passwordText = password.text.toString()
    val confirmText = passwordConfirm.text.toString()

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

    val confirmError = when {
        confirmText.isEmpty() || confirmText == passwordText -> null
        else -> "비밀번호가 일치하지 않아요."
    }

    val canRegister =
        nameText.isNotEmpty() &&
                isEmailValid &&
                passwordText.length >= 6 &&
                confirmText == passwordText

    Scaffold(
        modifier = modifier.fillMaxSize(),
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
                    .padding(top = 60.dp),
            ) {
                Text(
                    text = "이메일로 회원가입",
                    style = SoptTheme.typography.b28,
                    color = SoptTheme.colors.textPrimary
                )

                Spacer(Modifier.height(40.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                ) {
                    NameAuthTextField(
                        state = name
                    )

                    EmailAuthTextField(
                        state = email,
                        errorText = emailError
                    )

                    PasswordAuthTextField(
                        state = password,
                        errorText = passwordError,
                        imeAction = ImeAction.Next
                    )

                    PasswordAuthTextField(
                        state = passwordConfirm,
                        title = "비밀번호 확인",
                        errorText = confirmError,
                        imeAction = ImeAction.Done
                    )
                }

                Spacer(Modifier.height(40.dp))

                SoptPrimaryButton(
                    text = "회원가입",
                    enabled = canRegister,
                    onClick = {
                        onRegister(nameText, emailText, passwordText)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    SoptTheme {
        RegisterScreen(onRegister = { _, _, _ -> })
    }
}