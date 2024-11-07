package com.atech.research.ui.compose.teacher.home.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import com.atech.research.common.DisplayCard
import com.atech.research.common.EditTextEnhance
import com.atech.research.common.TagItem
import com.atech.research.common.TitleComposable
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.ui.theme.spacing

/**
 * Tag Screen
 *
 * @param modifier Modifier
 * @param uid String
 * @param list List<TagModel>
 * @param selectedList List<TagModel>
 * @param error String
 * @param onAddTag Function1<TagModel, Unit>
 * @param onSelectOrRemoveTag Function1<List<TagModel>, Unit>
 * @param onDeleteTag Function1<TagModel, Unit>
 * @see TagModel
 * @see TagItem
 * @see EditTextEnhance
 * @see DisplayCard
 */
@Composable
fun TagScreen(
    modifier: Modifier = Modifier,
    uid: String = "",
    list: List<TagModel>,
    selectedList: List<TagModel> = emptyList(),
    error: String = "",
    onAddTag: (TagModel) -> Unit = {},
    onSelectOrRemoveTag: (List<TagModel>) -> Unit = {},
    onDeleteTag: (TagModel) -> Unit = {},
) {
//    if (list.isEmpty()) {
//        ProgressBar(PaddingValues())
//        return
//    }
    var typedTag by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize()
            .padding(MaterialTheme.spacing.medium)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            EditTextEnhance(modifier = Modifier.let {
                if (typedTag.isEmpty()) it.fillMaxWidth()
                else it.weight(3f)
            }.animateContentSize(),
                value = typedTag,
                placeholder = "Add Tag",
                singleLine = true,
                onValueChange = { typedTag = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(onDone = {
                    if (typedTag.isNotEmpty()) {
                        onAddTag(TagModel(name = typedTag, createdBy = uid))
                        typedTag = ""
                    }
                }),
                clearIconClick = {
                    typedTag = ""
                })
            AnimatedVisibility(visible = typedTag.isNotEmpty()) {
                IconButton(modifier = Modifier, onClick = {
                    onAddTag(TagModel(name = typedTag, createdBy = uid))
                    typedTag = ""
                }) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        AnimatedVisibility(error.isNotEmpty()) {
            DisplayCard(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(
                    width = CardDefaults.outlinedCardBorder().width,
                    color = MaterialTheme.colorScheme.error
                ),
            ) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        HorizontalDivider()
        TitleComposable(title = "Tags")
        list.forEach { tag ->
            TagItem(tag = tag,
                isDeleteEnable = if (uid.isNotEmpty()) tag.createdBy == uid else false,
                isSelected = selectedList.any { it.name.lowercase() == tag.name.lowercase() },
                onItemClicked = { it ->
                    if (it) {
                        onSelectOrRemoveTag(selectedList + tag)
                    } else {
                        onSelectOrRemoveTag(selectedList.filter { it.name.lowercase() != tag.name.lowercase() })
                    }
                },
                onDeleteTag = {
                    onDeleteTag(tag)
                })
        }
    }
}