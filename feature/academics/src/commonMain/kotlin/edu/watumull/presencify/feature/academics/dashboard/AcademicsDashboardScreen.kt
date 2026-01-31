package edu.watumull.presencify.feature.academics.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun AcademicsDashboardScreen(
    state: AcademicsDashboardState,
    onAction: (AcademicsDashboardAction) -> Unit
) {
    when (state.viewState) {
        AcademicsDashboardState.ViewState.Loading -> PresencifyDefaultLoadingScreen()
        AcademicsDashboardState.ViewState.Content -> {
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
                    // Section 1: Curriculum and Governance
                    DashboardSection(title = "Curriculum and Governance") {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                PresencifyActionBar(
                                    text = "Branch",
                                    leadingIcon = Res.drawable.branch_24,
                                    onClick = { onAction(AcademicsDashboardAction.ClickBranch) },
                                    modifier = Modifier.weight(1f)
                                )
                                PresencifyActionBar(
                                    text = "Scheme",
                                    leadingIcon = Res.drawable.scheme_24,
                                    onClick = { onAction(AcademicsDashboardAction.ClickScheme) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                PresencifyActionBar(
                                    text = "Course",
                                    leadingIcon = Res.drawable.round_menu_book_24,
                                    onClick = { onAction(AcademicsDashboardAction.ClickCourse) },
                                    modifier = Modifier.weight(1f)
                                )
                                PresencifyActionBar(
                                    text = "University",
                                    leadingIcon = Res.drawable.apartment_24,
                                    onClick = { onAction(AcademicsDashboardAction.ClickUniversity) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            PresencifyActionBar(
                                text = "Link/Unlink Courses",
                                leadingIcon = Res.drawable.round_menu_book_24,
                                onClick = { onAction(AcademicsDashboardAction.ClickLinkUnlinkCourse) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    // Section 2: Academic Time & Cohort
                    DashboardSection(title = "Academic Time & cohort") {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            PresencifyActionBar(
                                text = "Semesters",
                                leadingIcon = Res.drawable.clock_icon,
                                onClick = { onAction(AcademicsDashboardAction.ClickSemester) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                PresencifyActionBar(
                                    text = "Division",
                                    leadingIcon = Res.drawable.group_division,
                                    onClick = { onAction(AcademicsDashboardAction.ClickDivision) },
                                    modifier = Modifier.weight(1f)
                                )
                                PresencifyActionBar(
                                    text = "Batch",
                                    leadingIcon = Res.drawable.group_batch,
                                    onClick = { onAction(AcademicsDashboardAction.ClickBatch) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
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
