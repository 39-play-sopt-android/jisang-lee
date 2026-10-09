package org.sopt.play.auth.presentation

import android.R.attr.contentDescription
import android.R.id.input
import android.util.Patterns
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.play.R
import org.sopt.play.auth.presentation.component.EmailAuthTextField
import org.sopt.play.auth.presentation.component.PasswordAuthTextField
import org.sopt.play.ui.component.PlayPrimaryButton
import org.sopt.play.ui.theme.PlaySoptTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
) {
    val email = rememberTextFieldState()
    val password = remember { TextFieldState() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .padding(top = 112.dp),
    ) {
        LoginText()

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            val emailText = email.text.toString().trim()
            val isEmailError = emailText.isNotEmpty() &&
                    !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()
            EmailAuthTextField(
                state = email,
                isError = isEmailError
            )
            val isPasswordError = password.text.isNotEmpty() && password.text.length < 6
            PasswordAuthTextField(
                state = password,
                isError = isPasswordError
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        val emailText = email.text.toString().trim()
        val passwordText = password.text.toString()

        val canLogin =
            Patterns.EMAIL_ADDRESS.matcher(emailText).matches() &&
                    passwordText.length >= 6

        PlayPrimaryButton(
            text = "로그인",
            enabled = canLogin,
//            onClick = { onLogin(emailText, passwordText) },
            onClick = {}
        )

        Spacer(modifier = Modifier.height(20.dp))

        SignUpPrompt(onClick = {

        })
    }
}

@Composable
private fun LoginText() {
    Text(
        text = "이메일로 로그인하기",

        // b28
        style = TextStyle(
            fontSize = 28.sp,
            lineHeight = 33.6.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            fontWeight = FontWeight(700),
            color = Color(0xFF121212),
        )
    )
}

@Composable
private fun SignUpPrompt(
    onClick: () -> Unit
) {
    val textStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
        letterSpacing = (-0.14).sp,
        fontFamily = FontFamily(
            Font(R.font.pretendard_medium)
        ),
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            12.dp,
            Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "아직 계정이 없으신가요?",
            style = textStyle,
            color = Color(0xFFB2BABD),
        )

        Text(
            text = "회원가입하기",
            style = textStyle,
            color = Color(0xFF23272A),
            modifier = Modifier.clickable(onClick = onClick),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    PlaySoptTheme {
        LoginScreen(Modifier.fillMaxSize())
    }
}