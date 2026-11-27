package org.sopt.play.post.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.post.model.Post
import org.sopt.play.post.presentation.component.PostBottomSheet
import org.sopt.play.post.presentation.component.PostCard
import org.sopt.play.post.presentation.component.PostDeleteDialog
import org.sopt.play.post.presentation.component.PostSearchBar
import org.sopt.play.ui.theme.SoptPalette
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun PostScreen(
    uiState: PostUiState,
    listState: LazyListState,
    keyword: TextFieldState,
    title: TextFieldState,
    content: TextFieldState,
    onSearch: () -> Unit,
    onFloatingButtonClick: () -> Unit,
    onSheetDismiss: () -> Unit,
    onCreateClick: () -> Unit,
    onPostLongClick: (Long) -> Unit,
    onDeleteDialogDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = SoptTheme.colors.background,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SoptTheme.colors.background)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Top +
                                    WindowInsetsSides.Horizontal
                        )
                    )
            ) {
                PostSearchBar(
                    state = keyword,
                    onSearch = onSearch,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = uiState.posts,
                    key = { post -> post.id }
                ) { post ->
                    PostCard(
                        post = post,
                        onLongClick = { onPostLongClick(post.id) }
                    )
                }
            }

            if (uiState.posts.isEmpty() && uiState.searchedKeyword.isNotBlank()) {
                Text(
                    text = "검색 결과가 없습니다.",
                    modifier = Modifier.align(Alignment.Center),
                    color = SoptTheme.colors.textSecondary,
                    style = SoptTheme.typography.m18
                )
            }

            PostFloatingButton(
                onClick = onFloatingButtonClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 20.dp)
            )
        }
    }

    if (uiState.isSheetVisible) {
        PostBottomSheet(
            title = title,
            content = content,
            isCreateEnabled = uiState.isCreateEnabled,
            onDismissRequest = onSheetDismiss,
            onCreateClick = onCreateClick
        )
    }

    if (uiState.deletePostId != null) {
        PostDeleteDialog(
            onDismissRequest = onDeleteDialogDismiss,
            onDeleteClick = onDeleteClick
        )
    }
}

@Composable
private fun PostFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .dropShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 6.dp,
                    color = Color.Black.copy(alpha = 0.15f),
                    offset = DpOffset(x = 0.dp, y = 4.dp)
                )
            )
            .clip(CircleShape)
            .background(SoptPalette.Red)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = "게시글 작성",
            modifier = Modifier.size(28.dp),
            tint = SoptPalette.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PostScreenPreview() {
    val posts = listOf(
        Post(
            id = 3,
            title = "본문이 길어지면",
            content = "본문은 천 자까지 쓸 수 있대서 카드가 어떻게 늘어나는지 보려고 길게 써봤어요. 생각보다 길게 쓰는 게 어렵네요. 아무 말이나 더 써보겠습니다. 오늘 저녁은무엇을먹을거냐면 비밀입니다. 목요일은 목에 기름칠하는 날인데 밥 먹기 귀찮네요 퇴근하고싶습니다. 지금 몇글자인지 잘 모르겠는데 1000자를 넘어갔는지 안넘어갔는지 잘 모르겠어요. 사실 1000자 확실하게 못채운건 알고 있습니다. 왜냐하면 1000자를 채우는건 꽤 어려운 일이기 때문이죠. 하지만 지금 300는 확실하게 넘었습니다. 어쩌면 500자도 넘었을지 모르겠는데 잘 모르겠어요. 방금 확인해보니 어쩌면 500자도 넘었을지 모르겠는데 잘 모르겠어요 부분에서 300자가 넘었네요 이런미친미 블로그 쓸 때도 느끼지만 창작의 고통은 정말 쉽지 않음을 느끼고 있습니다. 그런데 지금 여기에 이렇게 1000자를 쓰는게 의미가 있나 싶지만 이거를 그냥 프리뷰로만 끝내버리기는 아깝긴하네요 하지만 그 뭐냐 아피 테스트할 때 이 글을 그대로 사용한다면 제 신분이 노출될까봐 심히 걱정이 되긴 합니다. 제가 가장 늦게 제출을 한다면 아무도 안볼 수 있지 않을까 싶지만 잘 모르겠네요. 핸드폰으로 이렇게 긴 글을 작성하는 경우는 없으리라 믿습니다. 왜냐하면 컴퓨터로 타자를 치면 열손가락으로 치지만 핸드폰으로 타자를 치면 두손가락으로만 치니까요. 어쩌면 3손가락을 사용하거나 5손가락을 사용하는 사람이 있을수도 있지만 아직까지 그런 사람을 목격하지는 못했습니다. 이러한 특징을 고려했을 때 핸드폰 자판은 qwerty보다는 천지인이 더 인체공학적으로 좋은 키보드라는 생각이 드네요 크월틔로 타자치면 계속 오타났었는데 어릴때 천지인으로 타자를 치면 오타가 나는 일이 적었습니다. 어쩌면 어릴때보다 약 30키로가량 증가해서 입력 위치에 오차가 생기는 것 일수도 있겠네요. 여러분은 첫 핸드폰이 무엇이였나요? 저는 폴더폰이였습니다. 초등학교 1학년 때 첫 핸드폰이 생겼었는데 핸드폰을 받은 첫 날 밤새 테마도 바꿔보고 게임도 했던 기억이 납니다. 아침에 핸드폰 배터리가 없어서 그대로 빼았겼었지요. 어쩌면 모바일 개발의 길을 걷게 된 것은 운명일지도 모릅니다. 가장 오래된 기억 중 하나를 선보이자면 때는 5살 추석때 삼촌의 핸드폰을 가지고 놀다가 초기화를 해버린 기억이 나네요. 그 때도 이 조작으로 인해 핸드폰이 초기화될거라는 인식은 있었으나 순수악 시절이였기 때문에 결국 해버렸습니다. 무료했던 삼촌의 일상을 유료로 바꾸어드렸었지요. 이 이후로 모바일 개발자가 되어 어쩔 수 없는 재능의 거름으로 써먹게 되었습니다. 아 지금 몇글자지 오 1200자네 나이스",
            author = "안드로이드",
            createdDate = "2026.11.25"
        ),

        Post(
            id = 2,
            title = "화이팅",
            content = "다들 끝까지 화이팅입니다",
            author = "솝트",
            createdDate = "2026.11.20"
        ),

        Post(
            id = 1,
            title = "첫 글",
            content = "첫 글은 제가 가져갑니다",
            author = "닉네임은스무자까지된다길래꽉채워봤습니디",
            createdDate = "2026.11.19"
        )
    )

    SoptTheme {
        PostScreen(
            uiState = PostUiState(posts = posts),
            listState = rememberLazyListState(),
            keyword = rememberTextFieldState(),
            title = rememberTextFieldState(),
            content = rememberTextFieldState(),
            onSearch = {},
            onFloatingButtonClick = {},
            onSheetDismiss = {},
            onCreateClick = {},
            onPostLongClick = {},
            onDeleteDialogDismiss = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PostFloatingButtonPreview() {
    SoptTheme {
        PostFloatingButton(
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
