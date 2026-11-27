package org.sopt.play.post.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.post.model.Post
import org.sopt.play.ui.theme.SoptPalette
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun PostCard(
    post: Post,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(20.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SoptPalette.Gray1,
                shape = cardShape
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = post.author,
                modifier = Modifier.weight(1f, fill = false),
                color = SoptTheme.colors.textSecondary,
                style = SoptTheme.typography.m12,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            VerticalDivider(
                modifier = Modifier.height(12.dp),
                thickness = 1.dp,
                color = SoptTheme.colors.textSecondary
            )

            Text(
                text = post.createdDate,
                color = SoptTheme.colors.textSecondary,
                style = SoptTheme.typography.m12,
                maxLines = 1
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = post.title,
                color = SoptPalette.Gray5,
                style = SoptTheme.typography.m16
            )

            Text(
                text = post.content,
                color = SoptPalette.Gray5,
                style = SoptTheme.typography.m14
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostCardPreview() {
    val posts = listOf(
        Post(
            id = 1,
            title = "본문이 길어지면",
            content = "본문은 천 자까지 쓸 수 있대서 카드가 어떻게 늘어나는지 보려고 길게 써봤어요. 생각보다 길게 쓰는 게 어렵네요. 아무 말이나 더 써보겠습니다. 오늘 점심은 뭐 먹지",
            author = "안드로이드",
            createdDate = "2026.11.25"
        ),

        Post(
            id = 2,
            title = "첫 글",
            content = "첫 글은 제가 가져갑니다",
            author = "닉네임은스무자까지된다길래꽉채워봤어요",
            createdDate = "2026.11.19"
        )
    )

    SoptTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            posts.forEach { post ->
                PostCard(post = post)
            }
        }
    }
}
