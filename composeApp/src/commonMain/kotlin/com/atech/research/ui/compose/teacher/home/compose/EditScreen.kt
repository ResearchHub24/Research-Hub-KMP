package com.atech.research.ui.compose.teacher.home.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.atech.research.common.ApplyButton
import com.atech.research.common.BottomPadding
import com.atech.research.common.EditTextEnhance
import com.atech.research.common.MarkdownEditor
import com.atech.research.common.TitleComposable
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.ui.theme.spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    model: ResearchModel,
    isSaveButtonVisible: Boolean = true,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    hasError: Pair<Boolean, Boolean> = Pair(false, false),
    onQuestionChange: (List<String>) -> Unit = {},
    onViewMarkdownClick: () -> Unit = {},
    onAddTagClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    var typedQuestion by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spacing.medium)
    ) {
        EditTextEnhance(
            modifier = Modifier.fillMaxWidth(),
            value = model.title,
            placeholder = "Title",
            onValueChange = onTitleChange,
            isError = hasError.first,
            supportingText = if (hasError.first) {
                { Text("Title is required") }
            } else null,
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Next,
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences,
            )
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        MarkdownEditor(
            value = model.description,
            onValueChange = {
                onDescriptionChange(it)
            },
            viewInMarkdownClick = onViewMarkdownClick
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        HorizontalDivider()
        TitleComposable(title = "Tags")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        ) {
            model.tags.forEach {
                InputChip(
                    trailingIcon = {
                        Icon(imageVector = Icons.Outlined.Close, null)
                    },
                    selected = true,
                    onClick = {},
                    label = { Text(text = it.name) }
                )
            }
        }
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        ApplyButton(text = "Add Tags") {
            onAddTagClick()
        }
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        HorizontalDivider()
        TitleComposable(title = "Questions")
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            EditTextEnhance(
                modifier = Modifier
                    .let {
                        if (typedQuestion.isEmpty()) it.fillMaxWidth()
                        else it.weight(3f)
                    }
                    .animateContentSize(),
                value = typedQuestion,
                placeholder = "Add Question",
                onValueChange = { typedQuestion = it },
                isError = hasError.second,
                /*supportingText = if (hasError.second) {
                    { Text("Description is required") }
                } else null,*/
                keyboardOptions = KeyboardOptions(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(onDone = {
                    if (typedQuestion.isNotEmpty()) {
                        onQuestionChange(model.questions + typedQuestion)
                        typedQuestion = ""
                    }
                })
            )
            AnimatedVisibility(visible = typedQuestion.isNotEmpty()) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        onQuestionChange(model.questions + typedQuestion)
                        typedQuestion = ""
                    }
                ) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                }
            }
        }
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        Column(
            modifier = Modifier.fillMaxWidth()
                .animateContentSize(),
        ) {
            model.questions.forEach {
                QuestionComposable(question = it, onRemoveClick = {
                    onQuestionChange(model.questions.filter { allQuestion -> allQuestion != it })
                })
            }
        }
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        ApplyButton(
            text = "Save",
            enable = isSaveButtonVisible
        ) {
            onSaveClick()
        }
        BottomPadding()
    }
}

@Composable
fun QuestionComposable(
    question: String,
    onRemoveClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = question,
            modifier = Modifier.weight(3f),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Start
        )
        IconButton(
            onClick = onRemoveClick
        ) {
            Icon(imageVector = Icons.Outlined.Remove, contentDescription = null)
        }
    }
}