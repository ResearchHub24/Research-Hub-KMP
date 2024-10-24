package com.atech.research.ui.compose.profile.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.atech.research.common.AppAlertDialog
import com.atech.research.common.ApplyButton
import com.atech.research.common.DisplayCard
import com.atech.research.common.EditText
import com.atech.research.common.bottomPaddingLazy
import com.atech.research.core.ktor.model.AnswerModel
import com.atech.research.ui.theme.spacing
import org.jetbrains.compose.resources.stringResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.apply
import researchhub.composeapp.generated.resources.require

@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier,
    questionList: List<String>,
    onApplyClick: (List<AnswerModel>) -> Unit = { }
) {
    var answers by remember {
        mutableStateOf(List(questionList.size) { "" })
    }
    var errorMessage by remember {
        mutableStateOf("")
    }
    var isDialogVisible by remember {
        mutableStateOf(false)
    }
    Scaffold(modifier = modifier.fillMaxSize(), bottomBar = {
        ApplyButton(
            text = stringResource(Res.string.apply),
            horizontalPadding = MaterialTheme.spacing.medium,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium),
            enable = errorMessage.isEmpty(),
        ) {

            if (answers.any { it.isEmpty() }) {
                errorMessage = "All answers are required !!"
                return@ApplyButton
            }
            if (answers.any { it.split(" ").size < 20 }) {
                errorMessage = "All answers should have at least 20 words !!"
                return@ApplyButton
            }
            errorMessage = ""
            isDialogVisible = true
        }
    }) { innerPadding ->
        AnimatedVisibility(isDialogVisible) {
            AppAlertDialog(dialogTitle = "Confirm Submit",
                dialogText = "Are you sure you want to submit?",
                icon = Icons.Outlined.Mail,
                onDismissRequest = {
                    isDialogVisible = false
                }) {
                val answerList = answers.mapIndexed { index, answer ->
                    AnswerModel(questionList[index], answer)
                }
                onApplyClick(answerList)
                isDialogVisible = false
            }
        }
        LazyColumn(
            modifier = modifier.fillMaxSize().padding(horizontal = MaterialTheme.spacing.medium),
            contentPadding = innerPadding
        ) {
            items(questionList.size) { pos ->
                QuestionItem(modifier = modifier.padding(bottom = MaterialTheme.spacing.medium),
                    question = questionList[pos],
                    answer = answers[pos],
                    onValueChange = { value ->
                        answers = answers.toMutableList().also {
                            it[pos] = value
                        }
                    },
                    onClearClick = {
                        answers = answers.toMutableList().also {
                            it[pos] = ""
                        }
                    })
            }

            item(
                key = "submit"
            ) {
                Box(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                ) {
                    AnimatedVisibility(
                        visible = errorMessage.isNotEmpty()
                    ) {
                        DisplayCard(
                            modifier = Modifier.fillMaxSize()
                                .padding(horizontal = MaterialTheme.spacing.medium),
                            border = BorderStroke(
                                width = CardDefaults.outlinedCardBorder().width,
                                color = MaterialTheme.colorScheme.error
                            ),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ErrorOutline,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                                Text(
                                    text = errorMessage,
                                    modifier = Modifier.padding(MaterialTheme.spacing.medium),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }

            bottomPaddingLazy(key = "bottomPadding")
            bottomPaddingLazy(key = "bottomPadding1")

        }
    }
}

@Composable
fun QuestionItem(
    modifier: Modifier = Modifier,
    question: String,
    answer: String = "",
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit = {},
    onClearClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = question,
            style = MaterialTheme.typography.titleMedium
        )
        if (readOnly) {
            EditText(
                modifier = Modifier.fillMaxWidth(),
                value = answer,
                placeholder = "Answer",
                supportingMessage = stringResource(Res.string.require),
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text
                ),
                clearIconClick = onClearClick,
                enable = false,
                trailingIcon = null,
                colors = TextFieldDefaults.colors().copy(
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            )
        } else EditText(
            modifier = Modifier.fillMaxWidth(),
            value = answer,
            placeholder = "Answer",
            supportingMessage = stringResource(Res.string.require),
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences, keyboardType = KeyboardType.Text
            ),
            clearIconClick = onClearClick,
        )

    }
}

