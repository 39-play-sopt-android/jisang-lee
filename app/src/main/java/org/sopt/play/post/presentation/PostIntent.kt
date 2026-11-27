package org.sopt.play.post.presentation

sealed interface PostIntent {
    data class InputChanged(val title: String, val content: String) : PostIntent
    data object FloatingButtonClicked : PostIntent
    data object SheetDismissed : PostIntent
    data object CreateClicked : PostIntent
    data object ErrorMessageShown : PostIntent
    data object PostCreatedHandled : PostIntent
}
