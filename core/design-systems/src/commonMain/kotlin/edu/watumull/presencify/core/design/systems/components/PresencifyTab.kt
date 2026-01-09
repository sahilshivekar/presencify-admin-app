package edu.watumull.presencify.core.design.systems.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PresencifyTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.primaryContainer,
    unselectedBorderColor: Color = Color.Unspecified,
) {
    Tab(
        text = {
            Text(text = text)
        },
        selected = selected,
        onClick = onClick,
        selectedContentColor = contentColorFor(selectedColor),
        unselectedContentColor = contentColorFor(unselectedColor),
        modifier = modifier
            .height(40.dp)
            .padding(horizontal = 4.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(if (selected) selectedColor else unselectedColor)
            .border(
                width = 1.dp,
                color = if (!selected) unselectedBorderColor else Color.Transparent,
                shape = MaterialTheme.shapes.extraLarge,
            ),
    )
}