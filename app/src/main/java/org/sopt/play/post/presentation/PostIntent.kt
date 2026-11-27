package org.sopt.play.post.presentation

sealed interface PostIntent {
    data class InputChanged(val title: String, val content: String) : PostIntent
    data object FloatingButtonClicked : PostIntent
    data object SheetDismissed : PostIntent
    data object CreateClicked : PostIntent
    data class PostLongClicked(val postId: Long) : PostIntent
    data object DeleteDialogDismissed : PostIntent
    data object DeleteClicked : PostIntent
    data object ErrorMessageShown : PostIntent
    data object PostCreatedHandled : PostIntent
}
