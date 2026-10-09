package org.sopt.play.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.sopt.play.auth.presentation.RegisterScreen
import org.sopt.play.auth.ui.theme.PlaySoptTheme

class RegisterActivity : ComponentActivity() {
    companion object {
        const val EXTRA_NAME = "org.sopt.play.EXTRA_NAME"
        const val EXTRA_EMAIL = "org.sopt.play.EXTRA_EMAIL"
        const val EXTRA_PASSWORD = "org.sopt.play.EXTRA_PASSWORD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaySoptTheme {
                RegisterScreen(
                    onRegister = { name, email, password ->
                        val result = Intent().apply {
                            putExtra(EXTRA_NAME, name)
                            putExtra(EXTRA_EMAIL, email)
                            putExtra(EXTRA_PASSWORD, password)
                        }

                        setResult(Activity.RESULT_OK, result)
                        finish()
                    },
                )
            }
        }
    }
    
}