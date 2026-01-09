package edu.watumull.presencify.core.design.systems.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PresencifyDotSeparator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
) {
    Text(
        text = "•",
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        modifier = modifier.padding(horizontal = 8.dp)
    )
}