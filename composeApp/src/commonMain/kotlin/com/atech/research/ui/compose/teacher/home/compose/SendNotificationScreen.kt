package com.atech.research.ui.compose.teacher.home.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.atech.research.common.ApplyButton
import com.atech.research.common.EditTextEnhance
import com.atech.research.common.LottieAnim
import com.atech.research.common.LottieAnimationLinks
import com.atech.research.ui.compose.teacher.home.HomeScreenEvents
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.BackHandler

@Composable
fun SendNotificationScreen(
    modifier: Modifier = Modifier,
    titleWithUrl: Pair<String, String?>,
    researchId: String,
    created: Long,
    onEvent: (HomeScreenEvents) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var canGoBack by rememberSaveable { mutableStateOf(true) }



    BackHandler(enabled = canGoBack) {
        onBackClick()
    }

    Column(
        modifier = modifier.padding(MaterialTheme.spacing.medium)
    ) {
        AnimatedVisibility(!canGoBack) {
            ShowNotificationDialog(onDismissRequest = {
                canGoBack = true
            })
        }
        EditTextEnhance(
            modifier = Modifier.fillMaxWidth(),
            value = titleWithUrl.first,
            isError = titleWithUrl.first.isEmpty(),
            onValueChange = {
                onEvent(HomeScreenEvents.OnTitleAndImageUrlChange(it, titleWithUrl.second))
            },
            clearIconClick = {
                onEvent(HomeScreenEvents.OnTitleAndImageUrlChange("", titleWithUrl.second))
            },
            supportingText = {
                Text(
                    if (titleWithUrl.first.isEmpty()) "Body can't be empty"
                    //else if (title.length > 50) "Body must be less than 50 characters"
                    else "${titleWithUrl.first.length}/50"
                )
            },
            placeholder = "Title"
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        EditTextEnhance(
            modifier = Modifier.fillMaxWidth(),
            value = titleWithUrl.second ?: "",
            onValueChange = {
                onEvent(HomeScreenEvents.OnTitleAndImageUrlChange(titleWithUrl.first, it))
            },
            clearIconClick = {
                onEvent(HomeScreenEvents.OnTitleAndImageUrlChange(titleWithUrl.first, ""))
            },
            placeholder = "Image Url"
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        ApplyButton(
            enable = titleWithUrl.first.isNotBlank() /*&& title.length <= 50*/,
            text = "Send",
            horizontalPadding = MaterialTheme.spacing.default
        ) {
            canGoBack = false
            onEvent.invoke(
                HomeScreenEvents.SendNotification(
                    title = titleWithUrl.first,
                    imageLink = titleWithUrl.second,
                    researchId = researchId,
                    created = created,
                    onDone = {
                        canGoBack = true
                        onBackClick.invoke()
                    }
                )
            )
        }
    }
}

@Composable
private fun ShowNotificationDialog(
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(MaterialTheme.spacing.medium),
                    text = "Research Publish Notification"
                )
                LottieAnim(
                    link = LottieAnimationLinks.SendNotification.link
                )
            }
        }
    }
}