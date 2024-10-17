package com.atech.research.ui.compose.profile.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.atech.research.common.EditTextEnhance
import com.atech.research.common.TagItem
import com.atech.research.common.TitleComposable
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.ui.theme.spacing
import org.jetbrains.compose.resources.stringResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.all_skills
import researchhub.composeapp.generated.resources.selected
import researchhub.composeapp.generated.resources.skills

@Composable
fun AddOrEditSkill(
    modifier: Modifier = Modifier,
    skillList: List<String> = emptyList(),
    selectedList: List<String> = emptyList(),
    onDoneClick: (List<String>) -> Unit = {},
) {
    var searchTags by remember { mutableStateOf("") }
    var updatedSelectedList by remember(selectedList) { mutableStateOf(selectedList) }
    val map: Map<String, List<String>> by mutableStateOf(
        buildMap {
            val selectedKey = stringResource(Res.string.selected)
            val allSkillsKey = stringResource(Res.string.all_skills)

            // Only add the 'selected' entry if updatedSelectedList is not empty
            if (updatedSelectedList.isNotEmpty()) {
                put(selectedKey, updatedSelectedList)
            }

            // Filter and add the 'all skills' entry
            val filteredSkillList = skillList.filter { !updatedSelectedList.contains(it) }
            val finalSkillList = if (searchTags.isNotEmpty()) {
                filteredSkillList.filter { it.contains(searchTags, ignoreCase = true) }
            } else {
                filteredSkillList
            }

            put(allSkillsKey, finalSkillList)
        }
    )
    Scaffold(
        modifier = modifier.fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    EditTextEnhance(modifier = Modifier
                        .weight(3f),
                        value = searchTags,
                        placeholder = "Add Tag",
                        singleLine = true,
                        onValueChange = { searchTags = it },
                        keyboardOptions = KeyboardOptions(
                            imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            if (searchTags.isNotEmpty()) {
                                searchTags = ""
                            }
                        }),
                        clearIconClick = {
                            searchTags = ""
                        })
                    IconButton(modifier = Modifier, onClick = {
                        onDoneClick(updatedSelectedList)
                    }) {
                        Icon(imageVector = Icons.Outlined.Check, contentDescription = null)
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                HorizontalDivider()
                TitleComposable(title = stringResource(Res.string.skills))
            }
            map.forEach { (initial, contactsForInitial) ->
                stickyHeader {
                    TitleComposable(title = initial)
                }
                items(contactsForInitial) { skill ->
                    TagItem(tag = TagModel(name = skill, createdBy = ""),
                        isDeleteEnable = false,
                        isSelected = updatedSelectedList.any { it.lowercase() == skill.lowercase() },
                        onItemClicked = {
                            updatedSelectedList = if (updatedSelectedList.contains(skill)) {
                                updatedSelectedList.filter { it != skill }
                            } else {
                                updatedSelectedList + skill
                            }
                        })
                }
            }
        }
    }
}