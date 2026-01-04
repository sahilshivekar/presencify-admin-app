package edu.watumull.presencify.core.design.systems.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PresencifyTextDivider(
    modifier: Modifier = Modifier
) {
    Text(
        text = "•",
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier.padding(horizontal = 5.dp)
    )
}