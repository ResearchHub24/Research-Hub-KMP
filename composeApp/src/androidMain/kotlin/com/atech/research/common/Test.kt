package com.atech.research.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.atech.research.core.ktor.model.EducationDetails
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.ui.theme.spacing

//@Composable
//fun EducationDetails(
//    modifier: Modifier = Modifier,
//    educationDetails: EducationDetails
//) {
//    Column(
//        modifier = modifier
//            .padding(MaterialTheme.spacing.medium)
//            .verticalScroll(rememberScrollState())
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        TitleComposable(
//            title = stringResource(R.string.education_details)
//        )
//        EditTextEnhance(
//            modifier = Modifier.fillMaxWidth(),
//            value = state.institute,
//            placeholder = stringResource(R.string.collage_school),
//            isError = state.institute.isEmpty(),
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Outlined.Person, contentDescription = null
//                )
//            },
//            onValueChange = { value ->
//                onEvent.invoke(
//                    ResumeScreenEvents.OnEducationEdit(
//                        model = state.copy(
//                            institute = value
//                        )
//                    )
//                )
//            },
//            clearIconClick = {
//                onEvent.invoke(
//                    ResumeScreenEvents.OnEducationEdit(
//                        model = state.copy(
//                            institute = ""
//                        )
//                    )
//                )
//            },
//            keyboardOptions = KeyboardOptions(
//                imeAction = ImeAction.Next,
//                capitalization = KeyboardCapitalization.Sentences,
//                keyboardType = KeyboardType.Text
//            )
//        )
//        EditTextEnhance(
//            modifier = Modifier.fillMaxWidth(),
//            value = state.degree,
//            placeholder = stringResource(R.string.degree_subject),
//            isError = state.degree.isEmpty(),
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Outlined.Book, contentDescription = null
//                )
//            },
//            onValueChange = { value ->
//                onEvent.invoke(
//                    ResumeScreenEvents.OnEducationEdit(
//                        model = state.copy(
//                            degree = value
//                        )
//                    )
//                )
//            },
//            clearIconClick = {
//                onEvent.invoke(
//                    ResumeScreenEvents.OnEducationEdit(
//                        model = state.copy(
//                            degree = ""
//                        )
//                    )
//                )
//            },
//            keyboardOptions = KeyboardOptions(
//                imeAction = ImeAction.Next,
//                capitalization = KeyboardCapitalization.Sentences,
//                keyboardType = KeyboardType.Text
//            )
//        )
//        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
//        TitleComposable(
//            title = stringResource(R.string.year_details)
//        )
//        AnimatedVisibility(
//            isYearValid.not()
//        ) {
//            DisplayCard(
//                modifier = Modifier.fillMaxSize(),
//                border = BorderStroke(
//                    width = CardDefaults.outlinedCardBorder().width,
//                    color = MaterialTheme.colorScheme.error
//                ),
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Outlined.ErrorOutline,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.error
//                    )
//                    Text(
//                        text = stringResource(R.string.end_year_should_be_greater_than_start_year),
//                        modifier = Modifier.padding(MaterialTheme.spacing.medium),
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.error
//                    )
//                }
//            }
//        }
//        EditTextEnhance(modifier = Modifier.fillMaxWidth(),
//            value = state.startYear,
//            placeholder = stringResource(R.string.start_year),
//            trailingIcon = null,
//            readOnly = true,
//            interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
//                LaunchedEffect(interactionSource) {
//                    interactionSource.interactions.collect {
//                        if (it is PressInteraction.Release) {
//                            request = Request.START_YEAR
//                            showBottomSheet = true
//                        }
//                    }
//                }
//            })
//        EditTextEnhance(modifier = Modifier.fillMaxWidth(),
//            value = state.endYear ?: "",
//            placeholder = stringResource(R.string.end_year),
//            trailingIcon = null,
//            readOnly = true,
//            interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
//                LaunchedEffect(interactionSource, isCurrentlyLearning) {
//                    interactionSource.interactions.collect {
//                        if (!isCurrentlyLearning) if (it is PressInteraction.Release) {
//                            request = Request.END_YEAR
//                            showBottomSheet = true
//                        }
//                    }
//                }
//            })
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .toggleable(value = isCurrentlyLearning, onValueChange = { value ->
//                    isCurrentlyLearning = value
//                    onEvent.invoke(
//                        ResumeScreenEvents.OnEducationEdit(
//                            model = state.copy(
//                                endYear = if (value) context.getString(
//                                    R.string.present
//                                ) else "",
//                                percentage = if (value) "0" else null
//                            )
//                        )
//                    )
//                }),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start
//        ) {
//            Checkbox(checked = isCurrentlyLearning, onCheckedChange = { value ->
//                isCurrentlyLearning = value
//                onEvent.invoke(
//                    ResumeScreenEvents.OnEducationEdit(
//                        model = state.copy(
//                            endYear = if (value) context.getString(
//                                R.string.present
//                            ) else "",
//                            percentage = if (value) "0" else null
//                        )
//                    )
//                )
//            })
//            Text(text = stringResource(R.string.current_pursuing))
//        }
//        AnimatedVisibility(isCurrentlyLearning.not()) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
//                TitleComposable(
//                    title = stringResource(R.string.grades)
//
//                )
//                AnimatedVisibility(
//                    isGradeValid.not()
//                ) {
//                    DisplayCard(
//                        modifier = Modifier.fillMaxSize(),
//                        border = BorderStroke(
//                            width = CardDefaults.outlinedCardBorder().width,
//                            color = MaterialTheme.colorScheme.error
//                        ),
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                imageVector = Icons.Outlined.ErrorOutline,
//                                contentDescription = null,
//                                tint = MaterialTheme.colorScheme.error
//                            )
//                            Text(
//                                text = stringResource(R.string.grade_should_be_less_than_100),
//                                modifier = Modifier.padding(MaterialTheme.spacing.medium),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.error
//                            )
//                        }
//                    }
//                }
//                EditTextEnhance(
//                    modifier = Modifier.fillMaxWidth(),
//                    value = if (state.percentage == null) "" else state.percentage.toString(),
//                    placeholder = stringResource(R.string.grades_percentage),
//                    leadingIcon = {
//                        Icon(
//                            imageVector = Icons.Outlined.Person, contentDescription = null
//                        )
//                    },
//                    onValueChange = { value ->
//                        onEvent.invoke(
//                            ResumeScreenEvents.OnEducationEdit(
//                                model = state.copy(percentage = value)
//                            )
//                        )
//                    },
//                    clearIconClick = {
//                        onEvent.invoke(
//                            ResumeScreenEvents.OnEducationEdit(
//                                model = state.copy(percentage = null)
//                            )
//                        )
//                    },
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Decimal,
//                        imeAction = ImeAction.Done
//                    ),
//                )
//            }
//        }
//        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
//        ApplyButton(
//            text = stringResource(R.string.add_education), enable = hasError.not()
//        ) {
//            onEvent(
//                ResumeScreenEvents.OnEducationSave { message ->
//                    if (message != null) {
//                        toast(context, message)
//                        return@OnEducationSave
//                    }
//                    toast(
//                        context,
//                        context.getString(R.string.education_details_added)
//                    )
//                    navController.popBackStack()
//                }
//            )
//        }
//        BottomPadding()
//    }
//}

@Preview(
    showBackground = true,
    /*uiMode = UI_MODE_NIGHT_YES*/
)
@Composable
private fun TestPreview() {
    ResearchHubTheme() {
//        ProfileScreen()
    }
}