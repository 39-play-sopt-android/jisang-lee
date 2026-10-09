package org.sopt.play.auth

import android.R.attr.onClick
import android.R.attr.text
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import org.sopt.play.MainActivity
import org.sopt.play.R
import org.sopt.play.auth.presentation.LoginScreen
import org.sopt.play.ui.component.PlayPrimaryButton
import org.sopt.play.ui.theme.PlaySoptTheme
import kotlin.jvm.java

class LoginActivity : ComponentActivity() {

    private var registeredEmail: String? = null
    private var registeredPassword: String? = null

    private val registerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            registeredEmail = result.data
                ?.getStringExtra(RegisterActivity.EXTRA_EMAIL)
            registeredPassword = result.data
                ?.getStringExtra(RegisterActivity.EXTRA_PASSWORD)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PlaySoptTheme {
                LoginScreen(
                    onLogin = ::tryLogin,
                    onSignUp = {
                        registerLauncher.launch(
                            Intent(this, RegisterActivity::class.java)
                        )
                    },
                )
            }
        }
    }

    private fun tryLogin(email: String, password: String) {
        val isMatched =
            email == registeredEmail?.trim() &&
                    password == registeredPassword

        if (isMatched) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(
                this,
                "이메일 또는 비밀번호가 올바르지 않아요.",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}