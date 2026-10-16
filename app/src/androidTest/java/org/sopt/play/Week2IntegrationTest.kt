package org.sopt.play

import android.graphics.Bitmap
import android.os.SystemClock
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextReplacement
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Runs through the real activity and navigation graph, including account registration.
 * Pass `-e captureEvidence true` to save PNGs in the app's external files/pr-evidence
 * directory and briefly hold each stage for an independently started screen recording.
 */
@RunWith(AndroidJUnit4::class)
class Week2IntegrationTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun registerLoginAndBrowseHome() {
        composeRule.onNodeWithText("로그인").assertIsNotEnabled()
        capture("01-login-empty")

        enterCredentials(email = "invalid", password = "123")
        composeRule.onNodeWithText("올바른 이메일을 입력해주세요.").assertExists()
        composeRule.onNodeWithText("비밀번호는 6자 이상 입력해주세요.").assertExists()
        composeRule.onNodeWithText("로그인").assertIsNotEnabled()
        capture("02-login-validation")

        openRegistrationAndFillFields()
        composeRule.onNodeWithText("회원가입")
            .performScrollTo()
            .assertIsEnabled()
        capture("03-register-ready")
        composeRule.onNodeWithText("회원가입").performClick()

        enterCredentials(password = "wrong-password")
        composeRule.onNodeWithText("로그인").performScrollTo().performClick()
        composeRule.onNodeWithText("이메일로 로그인하기").assertExists()
        composeRule.onNodeWithText("PLAY SOPT").assertDoesNotExist()
        capture("04-login-rejected")

        enterCredentials()
        composeRule.onNodeWithText("로그인").performScrollTo().performClick()
        assertHome()
        composeRule.onNodeWithText("홈").assertIsSelected()
        composeRule.onNodeWithText("전체").assertIsSelected()
        capture("05-home")

        composeRule.onNodeWithText("브랜드").performClick().assertIsSelected()
        composeRule.onNodeWithText("홈").assertIsNotSelected()
        composeRule.onNodeWithText("팬츠").performClick().assertIsSelected()
        composeRule.onNodeWithText("전체").assertIsNotSelected()
        capture("06-menu-category-selected")

        bannerRow().performScrollToIndex(2)
        composeRule.onNodeWithContentDescription("프로모션 배너 3").assertIsDisplayed()
        capture("07-banner-third")

        productGrid().performScrollToIndex(1)
        composeRule.onNodeWithText("트랜디어패럴").assertIsDisplayed()
        composeRule.onNodeWithText("28,800").assertIsDisplayed()
        composeRule.onNodeWithText("26,820").assertIsDisplayed()
        // These are sample-data indicators; the current product cards do not toggle favorites.
        composeRule.onAllNodesWithContentDescription("이 상품은 별로..")[0]
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("이 상품이 좋아요").assertIsDisplayed()
        capture("08-products-first-row")

        productGrid().performScrollToIndex(3)
        composeRule.onNodeWithText("보울7부티셔츠").assertIsDisplayed()
        composeRule.onNodeWithText("17,100").assertIsDisplayed()
        composeRule.onNodeWithText("35,200").assertIsDisplayed()
        composeRule.onNodeWithText("PLAY SOPT").assertIsDisplayed()
        capture("09-products-last-row")
    }

    @Test
    fun homeNameAndSelectionsSurviveActivityRecreation() {
        openRegistrationAndFillFields()
        composeRule.onNodeWithText("회원가입").performScrollTo().performClick()
        enterCredentials()
        composeRule.onNodeWithText("로그인").performScrollTo().performClick()
        assertHome()

        composeRule.onNodeWithText("브랜드").performClick().assertIsSelected()
        composeRule.onNodeWithText("팬츠").performClick().assertIsSelected()

        composeRule.activityRule.scenario.recreate()
        composeRule.waitForIdle()

        assertHome()
        composeRule.onNodeWithText("브랜드").assertIsSelected()
        composeRule.onNodeWithText("팬츠").assertIsSelected()
        composeRule.onNodeWithText("홈").assertIsNotSelected()
        composeRule.onNodeWithText("전체").assertIsNotSelected()
        composeRule.onNodeWithText("이메일로 로그인하기").assertDoesNotExist()
        capture("10-home-after-recreation")
    }

    private fun openRegistrationAndFillFields() {
        composeRule.onNodeWithText("회원가입하기").performScrollTo().performClick()
        setField("이름", USER_NAME)
        setField("이메일 주소", EMAIL)
        setField("비밀번호", PASSWORD)
        setField("비밀번호 확인", PASSWORD)
        closeSoftKeyboard()
    }

    private fun enterCredentials(email: String = EMAIL, password: String = PASSWORD) {
        setField("이메일 주소", email)
        setField("비밀번호", password)
        closeSoftKeyboard()
    }

    private fun setField(label: String, value: String) {
        composeRule.onNodeWithContentDescription(label)
            .performScrollTo()
            .performTextReplacement(value)
    }

    private fun assertHome() {
        composeRule.onNodeWithText("PLAY SOPT").assertIsDisplayed()
        composeRule.onNodeWithText("${USER_NAME}님을 위한 추천 아이템").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("검색").assertIsDisplayed()
    }

    private fun bannerRow() = composeRule.onNode(
        hasScrollToIndexAction() and
            SemanticsMatcher.keyIsDefined(SemanticsProperties.HorizontalScrollAxisRange)
    )

    private fun productGrid() = composeRule.onNode(
        hasScrollToIndexAction() and
            SemanticsMatcher.keyIsDefined(SemanticsProperties.VerticalScrollAxisRange)
    )

    private fun capture(name: String) {
        if (InstrumentationRegistry.getArguments().getString("captureEvidence") != "true") {
            return
        }

        composeRule.waitForIdle()
        SystemClock.sleep(500)

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val directory = File(
            requireNotNull(instrumentation.targetContext.getExternalFilesDir(null)),
            "pr-evidence"
        )
        assertTrue("Could not create evidence directory", directory.exists() || directory.mkdirs())
        val bitmap = requireNotNull(instrumentation.uiAutomation.takeScreenshot())
        try {
            File(directory, "$name.png").outputStream().use { output ->
                assertTrue("Could not save $name.png", bitmap.compress(Bitmap.CompressFormat.PNG, 100, output))
            }
        } finally {
            bitmap.recycle()
        }
        SystemClock.sleep(1_300)
    }

    private companion object {
        const val USER_NAME = "이지상"
        const val EMAIL = "week2@example.com"
        const val PASSWORD = "sopt2026"
    }
}
