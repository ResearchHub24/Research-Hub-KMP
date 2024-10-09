package com.atech.research.ui.compose.profile.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.atech.research.common.ApplyButton
import com.atech.research.common.EditTextEnhance
import com.atech.research.common.TitleComposable
import com.atech.research.core.ktor.model.LinkModel
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.isValidUrl
import org.jetbrains.compose.resources.stringResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.add
import researchhub.composeapp.generated.resources.description
import researchhub.composeapp.generated.resources.link
import researchhub.composeapp.generated.resources.update

@Composable
fun AddOrEditLink(
    modifier: Modifier = Modifier,
    state: LinkModel? = null,
    onLinkSave: (LinkModel) -> Unit
) {
    var link by remember(state) { mutableStateOf(state?.link ?: "") }
    var description by remember(state) { mutableStateOf(state?.description ?: "") }
    val hasError = link.isValidUrl().not()

    Column(
        modifier = modifier.padding(MaterialTheme.spacing.medium)
            .verticalScroll(rememberScrollState()).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleComposable(
            title = stringResource(Res.string.link)
        )
        EditTextEnhance(
            modifier = Modifier.fillMaxWidth(),
            value = link,
            placeholder = stringResource(Res.string.link),
            isError = hasError,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Link, contentDescription = null
                )
            },
            onValueChange = { value ->
                link = value
            },
            clearIconClick = {
                link = ""
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            )
        )
        EditTextEnhance(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            placeholder = stringResource(Res.string.description),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Description, contentDescription = null
                )
            },
            onValueChange = { value ->
                description = value
            },
            clearIconClick = {
                description = ""
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            )
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
        ApplyButton(
            text = stringResource(
                Res.string.link,
            ) + " " + if (state == null) stringResource(Res.string.add) else stringResource(Res.string.update),
            enable = hasError.not(),
            action = {
                if (state == null) onLinkSave(LinkModel(link = link, description = description))
                else onLinkSave(state.copy(link = link, description = description))
            }
        )
    }
}