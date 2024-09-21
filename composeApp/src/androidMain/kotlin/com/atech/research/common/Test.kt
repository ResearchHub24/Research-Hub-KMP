package com.atech.research.common

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.UiMode
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.ui.compose.teacher.home.compose.QuestionComposable
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.ui.theme.spacing
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeBlock
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.compose.extendedspans.ExtendedSpans
import com.mikepenz.markdown.compose.extendedspans.RoundedCornerSpanPainter
import com.mikepenz.markdown.compose.extendedspans.SquigglyUnderlineSpanPainter
import com.mikepenz.markdown.compose.extendedspans.rememberSquigglyUnderlineAnimator
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.model.markdownExtendedSpans
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxThemes


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    model: ResearchModel,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    hasError: Pair<Boolean, Boolean> = Pair(false, false),
) {
    var typedQuestion by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spacing.medium)
    ) {
        EditTextEnhance(
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
        EditTextEnhance(
            value = model.description,
            placeholder = "Description",
            onValueChange = onDescriptionChange,
            isError = hasError.second,
            supportingText = if (hasError.second) {
                { Text("Description is required") }
            } else null,
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Next,
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences
            )
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
        ApplyButton(text = "Add Tags") { }
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
                )
            )
            AnimatedVisibility(visible = typedQuestion.isNotEmpty()) {
                IconButton(
                    modifier = Modifier,
                    onClick = { /*TODO*/ }
                ) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                }
            }
        }
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        model.questions.forEach {
            QuestionComposable(question = it, onRemoveClick = { /*TODO*/ })
        }
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        ApplyButton(text = "Save") {

        }
    }
}



@Preview(
    showBackground = true,
    /*uiMode = UI_MODE_NIGHT_YES*/
)
@Composable
private fun TestPreview() {
    ResearchHubTheme() {
        MarkdownViewer(
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
            markdown = """
                # Hello
                ## World
                ### This is a test
                - Item 1
                - Item 2
                - Item 3
                ```kotlin
                void main() {
                    println("Hello World")
                }
                ```
            """.trimIndent()
        )
    }
}