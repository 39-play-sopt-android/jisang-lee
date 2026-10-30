package org.sopt.play.ui.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "기본", widthDp = 360, showBackground = true)
@Preview(name = "글꼴 200%", widthDp = 360, fontScale = 2f, showBackground = true)
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class ComponentPreviews

@Preview(
    name = "기본",
    apiLevel = 36,
    device = "spec:width=360dp,height=800dp,dpi=440",
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "글꼴 150%",
    apiLevel = 36,
    device = "spec:width=360dp,height=800dp,dpi=440",
    fontScale = 1.5f,
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "글꼴 200%",
    apiLevel = 36,
    device = "spec:width=360dp,height=800dp,dpi=440",
    fontScale = 2f,
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "작은 화면, 글꼴 200%",
    apiLevel = 36,
    device = "spec:width=320dp,height=640dp,dpi=440",
    fontScale = 2f,
    showBackground = true,
    showSystemUi = true
)
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class ScreenPreviews
