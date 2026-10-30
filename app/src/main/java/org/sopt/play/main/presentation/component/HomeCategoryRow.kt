package org.sopt.play.main.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.preview.ComponentPreviews
import org.sopt.play.ui.theme.SoptPalette
import org.sopt.play.ui.theme.SoptTheme

@Composable
fun HomeCategoryRow(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val chipShape = RoundedCornerShape(6.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SoptTheme.colors.background)
            .horizontalScroll(scrollState)
            .selectableGroup()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        categories.forEach { category ->
            val isSelected = category == selectedCategory
            val backgroundColor = if (isSelected) SoptPalette.Gray5 else SoptPalette.White
            val contentColor = if (isSelected) SoptPalette.White else SoptPalette.Gray5
            val borderColor = if (isSelected) SoptPalette.Gray5 else SoptPalette.Gray2

            Box(
                modifier = Modifier
                    .clip(chipShape)
                    .background(backgroundColor)
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = chipShape
                    )
                    .selectable(
                        selected = isSelected,
                        onClick = { onCategorySelected(category) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category,
                    color = contentColor,
                    style = SoptTheme.typography.m14,
                    maxLines = 1
                )
            }
        }
    }
}

@ComponentPreviews
@Composable
private fun HomeCategoryRowPreview() {
    val categories = listOf("전체", "길이가 긴 카테고리는 길이가 길다")

    SoptTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            categories.forEach { selectedCategory ->
                HomeCategoryRow(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = {}
                )
            }
        }
    }
}
