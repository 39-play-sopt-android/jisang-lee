package org.sopt.play.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.main.presentation.component.HomeBannerRow
import org.sopt.play.main.presentation.component.HomeCategoryRow
import org.sopt.play.main.presentation.component.HomeMenuRow
import org.sopt.play.main.presentation.component.HomeProductCard
import org.sopt.play.main.presentation.component.HomeTopBar
import org.sopt.play.ui.theme.SoptTheme


@Composable
fun MainScreen(
    name: String,
    modifier: Modifier = Modifier
) {
    var selectedMenu by rememberSaveable {
        mutableStateOf("홈")
    }

    var selectedCategory by rememberSaveable {
        mutableStateOf("전체")
    }

    val gridState = rememberLazyGridState()

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
                HomeTopBar(
                    onSearchClick = {}
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .imePadding()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize(),
                state = gridState,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item(
                    key = "home_header",
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        HomeMenuRow(
                            menus = HomeSampleData.menus,
                            selectedMenu = selectedMenu,
                            onMenuSelected = { menu ->
                                selectedMenu = menu
                            }
                        )

                        HomeBannerRow(
                            bannerImages = HomeSampleData.bannerImages
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "${name}님을 위한 추천 아이템",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            color = SoptTheme.colors.textPrimary,
                            style = SoptTheme.typography.m18,
                            maxLines = 1
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        HomeCategoryRow(
                            categories = HomeSampleData.categories,
                            selectedCategory = selectedCategory,
                            onCategorySelected = { category ->
                                selectedCategory = category
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }
                }

                items(
                    items = HomeSampleData.products,
                    key = { product -> product.id }
                ) { product ->
                    HomeProductCard(
                        product = product
                    )
                }

                item(
                    key = "bottom_padding",
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    Spacer(
                        modifier = Modifier.height(96.dp)
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun MainScreenPreview() {
    SoptTheme {
        MainScreen(
            name = HomeSampleData.userName
        )
    }
}