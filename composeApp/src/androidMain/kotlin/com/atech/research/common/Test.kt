package com.atech.research.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.atech.research.core.ktor.model.EducationDetails
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.ui.theme.spacing
import org.jetbrains.compose.resources.stringResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.add_education
import researchhub.composeapp.generated.resources.collage_school
import researchhub.composeapp.generated.resources.current_pursuing
import researchhub.composeapp.generated.resources.degree_subject
import researchhub.composeapp.generated.resources.education_details
import researchhub.composeapp.generated.resources.end_year
import researchhub.composeapp.generated.resources.end_year_should_be_greater_than_start_year
import researchhub.composeapp.generated.resources.grade_should_be_less_than_100
import researchhub.composeapp.generated.resources.grades
import researchhub.composeapp.generated.resources.grades_percentage
import researchhub.composeapp.generated.resources.present
import researchhub.composeapp.generated.resources.start_year
import researchhub.composeapp.generated.resources.year_details
import java.util.Calendar

private enum class Request {
    START_YEAR, END_YEAR
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationDetails(
    modifier: Modifier = Modifier,
    state: EducationDetails
) {
    val yearList =
        (2010..Calendar.getInstance().get(Calendar.YEAR)).map { it.toString() }.reversed()
    var isCurrentlyLearning by remember { mutableStateOf(false) }
    var request by remember { mutableStateOf(Request.START_YEAR) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val isYearValid = state.endYear?.let { year ->
        if (year == Res.string.present.key) true
        else if (year.isNotBlank() && state.startYear.toInt() <= year.toInt()) true
        else false
    } ?: false

    var showBottomSheet by remember { mutableStateOf(false) }

    val isGradeValid = state.grade?.let { per ->
        per.isNotBlank() && per.toDouble() <= 100.0
    } ?: false

    val hasError =
        state.university.isEmpty() || state.degree.isEmpty() || state.startYear.isEmpty()
                || state.endYear.isNullOrEmpty() || state.grade.isNullOrEmpty()
                || !isYearValid || !isGradeValid
    Column(
        modifier = modifier
            .padding(MaterialTheme.spacing.medium)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleComposable(
            title = stringResource(Res.string.education_details)
        )
        EditTextEnhance(
            modifier = Modifier.fillMaxWidth(),
            value = state.university,
            placeholder = stringResource(Res.string.collage_school),
            isError = state.university.isEmpty(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Person, contentDescription = null
                )
            },
            onValueChange = { value ->
//                onEvent.invoke(
//                    ResumeScreenEvents.OnEducationEdit(
//                        model = state.copy(
//                            institute = value
//                        )
//                    )
//                )
            },
            clearIconClick = {
//                onEvent.invoke(
//                    ResumeScreenEvents.OnEducationEdit(
//                        model = state.copy(
//                            institute = ""
//                        )
//                    )
//                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            )
        )
        EditTextEnhance(
            modifier = Modifier.fillMaxWidth(),
            value = state.degree,
            placeholder = stringResource(Res.string.degree_subject),
            isError = state.degree.isEmpty(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Book, contentDescription = null
                )
            },
            onValueChange = { value ->
//                onEvent.invoke(
//                    ResumeScreenEvents.OnEducationEdit(
//                        model = state.copy(
//                            degree = value
//                        )
//                    )
//                )
            },
            clearIconClick = {
//                onEvent.invoke(
//                    ResumeScreenEvents.OnEducationEdit(
//                        model = state.copy(
//                            degree = ""
//                        )
//                    )
//                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            )
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        TitleComposable(
            title = stringResource(Res.string.year_details)
        )
        AnimatedVisibility(
            isYearValid.not()
        ) {
            DisplayCard(
                modifier = Modifier.fillMaxSize(),
                border = BorderStroke(
                    width = CardDefaults.outlinedCardBorder().width,
                    color = MaterialTheme.colorScheme.error
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ErrorOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = stringResource(Res.string.end_year_should_be_greater_than_start_year),
                        modifier = Modifier.padding(MaterialTheme.spacing.medium),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
        EditTextEnhance(modifier = Modifier.fillMaxWidth(),
            value = state.startYear,
            placeholder = stringResource(Res.string.start_year),
            trailingIcon = null,
            readOnly = true,
            interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            request = Request.START_YEAR
                            showBottomSheet = true
                        }
                    }
                }
            })
        EditTextEnhance(modifier = Modifier.fillMaxWidth(),
            value = state.endYear ?: "",
            placeholder = stringResource(Res.string.end_year),
            trailingIcon = null,
            readOnly = true,
            interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                LaunchedEffect(interactionSource, isCurrentlyLearning) {
                    interactionSource.interactions.collect {
                        if (!isCurrentlyLearning) if (it is PressInteraction.Release) {
                            request = Request.END_YEAR
                            showBottomSheet = true
                        }
                    }
                }
            })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(value = isCurrentlyLearning, onValueChange = { value ->
                    isCurrentlyLearning = value
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
                }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(checked = isCurrentlyLearning, onCheckedChange = { value ->
                isCurrentlyLearning = value
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
            })
            Text(text = stringResource(Res.string.current_pursuing))
        }
        AnimatedVisibility(isCurrentlyLearning.not()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
                TitleComposable(
                    title = stringResource(Res.string.grades)

                )
                AnimatedVisibility(
                    isGradeValid.not()
                ) {
                    DisplayCard(
                        modifier = Modifier.fillMaxSize(),
                        border = BorderStroke(
                            width = CardDefaults.outlinedCardBorder().width,
                            color = MaterialTheme.colorScheme.error
                        ),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ErrorOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = stringResource(Res.string.grade_should_be_less_than_100),
                                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
                EditTextEnhance(
                    modifier = Modifier.fillMaxWidth(),
                    value = if (state.grade == null) "" else state.grade.toString(),
                    placeholder = stringResource(Res.string.grades_percentage),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person, contentDescription = null
                        )
                    },
                    onValueChange = { value ->
//                        onEvent.invoke(
//                            ResumeScreenEvents.OnEducationEdit(
//                                model = state.copy(percentage = value)
//                            )
//                        )
                    },
                    clearIconClick = {
//                        onEvent.invoke(
//                            ResumeScreenEvents.OnEducationEdit(
//                                model = state.copy(percentage = null)
//                            )
//                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                )
            }
        }
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        ApplyButton(
            text = stringResource(Res.string.add_education), enable = hasError.not()
        ) {
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
        }
        BottomPadding()
    }
}

@Preview(
    showBackground = true,
    /*uiMode = UI_MODE_NIGHT_YES*/
)
@Composable
private fun TestPreview() {
    ResearchHubTheme() {
        EducationDetails(
            state = EducationDetails(
                university = "University",
                degree = "Degree",
                startYear = "2010",
                endYear = "2014",
                grade = "80",
                description = "Description"
            )
        )
    }
}