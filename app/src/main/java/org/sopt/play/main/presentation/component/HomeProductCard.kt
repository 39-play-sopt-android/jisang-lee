package org.sopt.play.main.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.main.presentation.HomeSampleData
import org.sopt.play.main.presentation.model.HomeProduct
import org.sopt.play.ui.preview.ComponentPreviews
import org.sopt.play.ui.theme.SoptPalette
import org.sopt.play.ui.theme.SoptTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeProductCard(
    product: HomeProduct,
    onFavoriteChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteIconRes = if (product.isFavorite) {
        R.drawable.ic_favorite_filled
    } else {
        R.drawable.ic_favorite_outline
    }

    val formattedPrice = NumberFormat
        .getNumberInstance(Locale.KOREA)
        .format(product.price)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SoptTheme.colors.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 5f)
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = "상품 이미지",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                IconToggleButton(
                    checked = product.isFavorite,
                    onCheckedChange = onFavoriteChanged,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = favoriteIconRes),
                        contentDescription = if (product.isFavorite) {
                            "안좋아졌다"
                        } else {
                            "좋다"
                        },
                        modifier = Modifier.size(24.dp),
                        tint = if (product.isFavorite) SoptPalette.Red else SoptPalette.White
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = 12.dp,
                    end = 8.dp,
                    bottom = 20.dp
                ),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = product.storeName,
                modifier = Modifier.fillMaxWidth(),
                color = SoptTheme.colors.textPrimary,
                style = SoptTheme.typography.m14,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.productName,
                modifier = Modifier.fillMaxWidth(),
                color = SoptTheme.colors.textPrimary,
                style = SoptTheme.typography.r14,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${product.discountPercent}%",
                    color = SoptPalette.Red,
                    style = SoptTheme.typography.m14,
                    maxLines = 1
                )

                Text(
                    text = formattedPrice,
                    modifier = Modifier
                        .weight(1f)
                        .basicMarquee(iterations = Int.MAX_VALUE),
                    color = SoptTheme.colors.textPrimary,
                    style = SoptTheme.typography.m14,
                    maxLines = 1
                )
            }
        }
    }
}

class HomeProductPreviewParameterProvider : PreviewParameterProvider<HomeProduct> {
    private val baseProduct = HomeSampleData.products.first()
    private val longStoreName = "이름이 아주 긴 스토어 트랜디어패럴"
    private val cases = listOf(
        "짧은 상점명, 짧은 상품명" to baseProduct.copy(
            storeName = "어텀",
            productName = "보울 티셔츠",
            discountPercent = 0
        ),
        "짧은 상점명, 긴 상품명" to baseProduct.copy(
            storeName = "어텀"
        ),
        "긴 상점명, 짧은 상품명" to baseProduct.copy(
            storeName = longStoreName,
            productName = "보울 티셔츠"
        ),
        "긴 상점명, 긴 상품명, 긴 가격" to baseProduct.copy(
            storeName = longStoreName,
            price = 1_234_567_890
        )
    )

    override val values = cases.asSequence().map { it.second }

    override fun getDisplayName(index: Int): String = cases[index].first
}

@ComponentPreviews
@Composable
private fun HomeProductCardPreview(
    @PreviewParameter(HomeProductPreviewParameterProvider::class) product: HomeProduct
) {
    SoptTheme {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(false, true).forEach { isFavorite ->
                HomeProductCard(
                    product = product.copy(
                        id = "${product.id}_$isFavorite",
                        isFavorite = isFavorite
                    ),
                    onFavoriteChanged = {},
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
