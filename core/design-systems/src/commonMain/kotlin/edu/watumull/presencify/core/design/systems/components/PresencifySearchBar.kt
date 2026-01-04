package edu.watumull.presencify.core.design.systems.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction

@Composable
fun PresencifySearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryValueChange: (String) -> Unit,
    onSearchIconClick: () -> Unit,
    onFilterIconClick: (() -> Unit)? = null,
    showFilterIcon: Boolean = true,
    searchBarPlaceholder: String
) {

    TextField(
        value = searchQuery,
        onValueChange = { onSearchQueryValueChange(it) },
        modifier = modifier
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxWidth(),
        placeholder = {
            Text(text = searchBarPlaceholder, style = MaterialTheme.typography.bodyMedium)
        },
        leadingIcon = {
            IconButton(
                onClick = onSearchIconClick
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        trailingIcon = {
            if (showFilterIcon) {
                IconButton(
                    onClick = {
                        if (onFilterIconClick != null) {
                            onFilterIconClick()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearchIconClick() }
        )
    )
}