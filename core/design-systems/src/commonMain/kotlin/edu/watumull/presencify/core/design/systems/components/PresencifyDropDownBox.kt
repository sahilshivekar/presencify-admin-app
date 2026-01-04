package edu.watumull.presencify.core.design.systems.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PresencifyDropDownMenuBox(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDropDownVisibilityChanged: (Boolean) -> Unit,
    value: String,
    enabled: Boolean,
    supportingText: String? = null,
    options: List<T>,
    onSelectItem: (T) -> Unit,
    label: String,
) {

    val localFocusManager = LocalFocusManager.current
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onDropDownVisibilityChanged(it) },
        modifier = modifier
    ) {
        PresencifyTextField(
            value = value,
            onValueChange = {},
            label = label,
            readOnly = true,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                    modifier = Modifier.menuAnchor(MenuAnchorType.SecondaryEditable),
                )
            },
            enabled = enabled,
            supportingText = supportingText,
            isError = supportingText != null
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onDropDownVisibilityChanged(false)
                localFocusManager.clearFocus()
            },
            shape = MaterialTheme.shapes.medium,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            if (options.isEmpty()) {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .size(24.dp)
                    )
                }
            }
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onSelectItem(option)
                        onDropDownVisibilityChanged(false)
                        localFocusManager.clearFocus()
                    },
                    text = {
                        Text(text = option.toString(), color = MaterialTheme.colorScheme.onSurface)
                    }
                )
            }
        }
    }
}