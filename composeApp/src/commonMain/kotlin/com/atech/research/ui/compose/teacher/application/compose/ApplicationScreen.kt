package com.atech.research.ui.compose.teacher.application.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.atech.research.common.ProgressBar
import com.atech.research.ui.compose.teacher.application.ApplicationEvents
import com.atech.research.ui.compose.teacher.application.ResearchApplicationsViewModel
import com.atech.research.utils.DataState
import com.atech.research.utils.Toast
import com.atech.research.utils.ToastDuration
import com.atech.research.utils.koinViewModel
import org.koin.compose.koinInject


@Composable
fun ApplicationScreen(
    modifier: Modifier = Modifier,
    researchId: String,
    onViewProfileClick: (String) -> Unit = {},
) {
    val viewModel: ResearchApplicationsViewModel = koinViewModel()
    LaunchedEffect(true) {
        viewModel.onEvent(ApplicationEvents.LoadData(researchId = researchId))
    }
    val allApplication by viewModel.allApplication
    if (allApplication is DataState.Loading) {
        ProgressBar(paddingValues = PaddingValues())
        return
    }
    val data = (allApplication as? DataState.Success)?.data ?: return
    val toast = koinInject<Toast>()
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(data) { application ->
            ApplicationItem(
                model = application,
                onViewProfileClick = {
                    onViewProfileClick.invoke(application.userUid)
                },
                onActionClick = {
                    viewModel.onEvent(
                        ApplicationEvents.OnStatusChange(
                            researchId,
                            application.userUid,
                            it,
                            onComplete = { message ->
                                toast.show(
                                    message ?: "Action Completed",
                                    ToastDuration.LONG
                                )
                            }
                        )
                    )
                },
                action = application.action,
            )
        }
    }
}