package org.sopt.play.post.presentation

sealed interface PostIntent {
    data object ErrorMessageShown : PostIntent
}
