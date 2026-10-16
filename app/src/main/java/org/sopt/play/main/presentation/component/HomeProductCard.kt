package org.sopt.play.main.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.main.presentation.HomeSampleData
import org.sopt.play.main.presentation.model.HomeProduct
import org.sopt.play.ui.theme.SoptPalette
import org.sopt.play.ui.theme.SoptTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeProductCard(
    product: HomeProduct,
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

            Icon(
                painter = painterResource(id = favoriteIconRes),
                contentDescription = if (product.isFavorite) {
                    "이 상품이 좋아요"
                } else {
                    "이 상품은 별로.."
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(24.dp),
                tint = SoptTheme.colors.textPrimary
            )
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
                    color = SoptTheme.colors.textPrimary,
                    style = SoptTheme.typography.m14,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeProductCardPreview() {
    SoptTheme {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HomeSampleData.products.take(2).forEach { product ->
                HomeProductCard(
                    product = product,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}