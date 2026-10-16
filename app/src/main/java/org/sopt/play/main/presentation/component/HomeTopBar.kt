package org.sopt.play.main.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(SoptTheme.colors.background)
            .padding(start = 16.dp, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "PLAY SOPT",
            modifier = Modifier.weight(1f),
            color = SoptTheme.colors.textPrimary,
            style = SoptTheme.typography.b28,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            onClick = onSearchClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "검색",
                modifier = Modifier.size(28.dp),
                tint = SoptTheme.colors.textPrimary,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun HomeTopBarPreview() {
    SoptTheme {
        HomeTopBar(
            onSearchClick = {}
        )
    }
}