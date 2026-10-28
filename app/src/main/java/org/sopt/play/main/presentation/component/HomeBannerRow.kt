package org.sopt.play.main.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.main.presentation.HomeSampleData
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun HomeBannerRow(
    bannerImages: List<Int>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(SoptTheme.colors.background),
        state = listState,
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = bannerImages,
            key = { _, imageRes -> imageRes }
        ) { index, imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "프로모션 배너 ${index + 1}",
                modifier = Modifier
                    .fillParentMaxWidth()
                    .aspectRatio(328f / 195f),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeBannerRowPreview() {
    SoptTheme {
        HomeBannerRow(
            bannerImages = HomeSampleData.bannerImages
        )
    }
}
