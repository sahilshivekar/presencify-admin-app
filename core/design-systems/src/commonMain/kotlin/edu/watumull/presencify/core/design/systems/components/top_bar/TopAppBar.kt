package edu.watumull.presencify.core.design.systems.components.top_bar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresencifyTopAppBar(
    modifier: Modifier = Modifier,
    topAppBarState: TopAppBarState,
    onBackIconButtonClick: (() -> Unit)? = null,
    onProfileIconButtonClick: (() -> Unit)? = null,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
) {
    TopAppBar(
        title = {
            topAppBarState.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                )
            }
            if (topAppBarState.isAppLogoNameVisible) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Placeholder for logo, using text
                    Text(
                        text = "P",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.size(42.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Presencify", fontWeight = FontWeight.SemiBold)
                }
            }
        },
        windowInsets = windowInsets,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        navigationIcon = {
            if (topAppBarState.isBackIconButtonVisible) {
                IconButton(
                    onClick = onBackIconButtonClick ?: {}
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back Button"
                    )
                }
            }
        },
        actions = {
            if (topAppBarState.isProfileIconButtonVisible) {
                IconButton(
                    onClick = onProfileIconButtonClick ?: {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Account Details",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
            canScroll = {
                false
            }
        ),
    )
}