package org.sopt.play.post.presentation

import org.sopt.play.post.model.Post

sealed interface PostIntent {
    data object SearchClicked : PostIntent
    data class InputChanged(val title: String, val content: String) : PostIntent
    data object FloatingButtonClicked : PostIntent
    data object SheetDismissed : PostIntent
    data object CreateClicked : PostIntent
    data class PostClicked(val post: Post) : PostIntent
    data class EditInputChanged(val title: String, val content: String) : PostIntent
    data object EditSheetDismissed : PostIntent
    data object EditClicked : PostIntent
    data class PostLongClicked(val postId: Long) : PostIntent
    data object DeleteDialogDismissed : PostIntent
    data object DeleteClicked : PostIntent
    data object ErrorMessageShown : PostIntent
    data object ScrollToTopHandled : PostIntent
}
