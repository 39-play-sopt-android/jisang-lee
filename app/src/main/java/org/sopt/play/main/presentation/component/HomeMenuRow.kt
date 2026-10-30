package org.sopt.play.main.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.preview.ComponentPreviews
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun HomeMenuRow(
    menus: List<String>,
    selectedMenu: String,
    onMenuSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SoptTheme.colors.background)
            .horizontalScroll(scrollState)
            .selectableGroup()
    ) {
        menus.forEach { menu ->
            val isSelected = menu == selectedMenu
            val textColor = if (isSelected) SoptTheme.colors.textPrimary else SoptTheme.colors.textSecondary
            val dotColor = if (isSelected) textColor else Color.Transparent

            Column(
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = { onMenuSelected(menu) },
                        role = Role.Tab
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(
                            color = dotColor,
                            shape = CircleShape
                        )
                )

                Text(
                    text = menu,
                    color = textColor,
                    style = SoptTheme.typography.m18,
                    maxLines = 1
                )
            }
        }
    }
}

@ComponentPreviews
@Composable
private fun HomeMenuRowPreview() {
    val menus = listOf("홈", "길이가 긴 메뉴 이름을 뭐로 지을지 고민된다")

    SoptTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            menus.forEach { selectedMenu ->
                HomeMenuRow(
                    menus = menus,
                    selectedMenu = selectedMenu,
                    onMenuSelected = {}
                )
            }
        }
    }
}
