package edu.watumull.presencify.feature.users.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.*
import edu.watumull.presencify.core.design.systems.components.PresencifyActionBar
import edu.watumull.presencify.core.design.systems.components.PresencifyDefaultLoadingScreen
import edu.watumull.presencify.core.presentation.UiConstants

@Composable
fun UsersDashboardScreen(
    state: UsersDashboardState,
    onAction: (UsersDashboardAction) -> Unit
) {
    when (state.viewState) {
        UsersDashboardState.ViewState.Loading -> PresencifyDefaultLoadingScreen()
        UsersDashboardState.ViewState.Content -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.widthIn(max = UiConstants.MAX_CONTENT_WIDTH),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Section 1: Users
                    DashboardSection(title = "Users") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            PresencifyActionBar(
                                text = "Students",
                                leadingImageVector = Icons.Default.Person,
                                onClick = { onAction(UsersDashboardAction.ClickStudents) },
                                modifier = Modifier.weight(1f)
                            )
                            PresencifyActionBar(
                                text = "Teachers",
                                leadingImageVector = Icons.Default.AccountBox,
                                onClick = { onAction(UsersDashboardAction.ClickTeachers) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Section 2: Assign Students
                    DashboardSection(title = "Assign students") {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            PresencifyActionBar(
                                text = "Assign/Unassign Student to Semester",
                                leadingIcon = Res.drawable.add_to_semester,
                                onClick = { onAction(UsersDashboardAction.ClickAssignUnassignSemester) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            PresencifyActionBar(
                                text = "Assign/Unassign Student to Division",
                                leadingIcon = Res.drawable.group_division,
                                onClick = { onAction(UsersDashboardAction.ClickAssignUnassignDivision) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            PresencifyActionBar(
                                text = "Assign/Unassign Student to Batch",
                                leadingIcon = Res.drawable.group_batch,
                                onClick = { onAction(UsersDashboardAction.ClickAssignUnassignBatch) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            PresencifyActionBar(
                                text = "Modify Student's Division",
                                leadingIcon = Res.drawable.edit_outlined,
                                onClick = { onAction(UsersDashboardAction.ClickModifyDivision) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            PresencifyActionBar(
                                text = "Modify Student's Batch",
                                leadingIcon = Res.drawable.edit_outlined,
                                onClick = { onAction(UsersDashboardAction.ClickModifyBatch) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    // Section 3: Dropout
                    DashboardSection(title = "Dropout students management") {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            PresencifyActionBar(
                                text = "Add to dropout",
                                leadingIcon = Res.drawable.baseline_person_remove_24,
                                onClick = { onAction(UsersDashboardAction.ClickAddToDropout) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            PresencifyActionBar(
                                text = "Remove from dropout",
                                leadingIcon = Res.drawable.outline_person_add_alt_1_24,
                                onClick = { onAction(UsersDashboardAction.ClickRemoveFromDropout) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        content()
    }
}
