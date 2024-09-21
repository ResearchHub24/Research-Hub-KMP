package com.atech.research.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.ui.theme.spacing


@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    uid: String = "",
    list: List<TagModel>,
    selectedList: List<TagModel> = emptyList(),
    onAddTab: (List<TagModel>) -> Unit = {},
    onSelectOrRemoveTag: (List<TagModel>) -> Unit = {},
    onDeleteTag: (TagModel) -> Unit = {},
) {
    var typedTag by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            EditTextEnhance(modifier = Modifier
                .let {
                    if (typedTag.isEmpty()) it.fillMaxWidth()
                    else it.weight(3f)
                }
                .animateContentSize(),
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
                        onAddTab(list + TagModel(name = typedTag, createdBy = uid))
                        typedTag = ""
                    }
                }
                ),
                clearIconClick = {
                    typedTag = ""
                }
            )
            AnimatedVisibility(visible = typedTag.isNotEmpty()) {
                IconButton(modifier = Modifier, onClick = {
                    onAddTab(list + TagModel(name = typedTag, createdBy = uid))
                    typedTag = ""
                }) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                }
            }
        }
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        HorizontalDivider()
        TitleComposable(title = "Tags")
        list.forEach { tag ->
            TagItem(
                tag = tag,
                isDeleteEnable = tag.createdBy == uid,
                isSelected = selectedList.contains(tag),
                onItemClicked = { it ->
                    if (it) {
                        onSelectOrRemoveTag(selectedList + tag)
                    } else {
                        onSelectOrRemoveTag(selectedList.filter { it != tag })
                    }
                },
                onDeleteTag = {
                    onDeleteTag(tag)
                }
            )
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
        TestScreen(
            uid = "createdBy1",
            list = listOf(
                TagModel(name = "tag1", createdBy = "createdBy1"),
                TagModel(name = "tag2", createdBy = "createdBy2"),
                TagModel(name = "tag3", createdBy = "createdBy3"),
                TagModel(name = "tag4", createdBy = "createdBy4"),
            )
        )
    }
}