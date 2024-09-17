package com.atech.research.ui.compose.login.compose.setup.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import com.atech.research.LocalDataStore
import com.atech.research.common.AppAlertDialog
import com.atech.research.common.ApplyButton
import com.atech.research.common.BodyComposable
import com.atech.research.common.DisplayCard
import com.atech.research.common.MainContainer
import com.atech.research.common.PasswordEditTextCompose
import com.atech.research.common.ProgressBar
import com.atech.research.common.TitleComposable
import com.atech.research.core.model.UserModel
import com.atech.research.core.model.UserType
import com.atech.research.ui.compose.login.compose.setup.SetUpScreenEvents
import com.atech.research.ui.navigation.MainScreenScreenRoutes
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.DataState
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.researchHubLog
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    user: DataState<UserModel>,
    passWord: String,
    userType: UserType,
    isPasswordValid: Boolean = false,
    onEvent: (SetUpScreenEvents) -> Unit = {}
) {
    val pref = LocalDataStore.current
    val scope = rememberCoroutineScope()
    MainContainer(
        modifier = modifier,
        appBarColor = MaterialTheme.colorScheme.primary
    ) { contentPadding ->
        AnimatedVisibility(user is DataState.Loading) {
            ProgressBar(paddingValues = contentPadding)
        }
        LaunchedEffect(user) {
            if (user is DataState.Success && user.data.password != null) {
                scope.launch {
                    pref.edit { pref ->
                        pref[booleanPreferencesKey(Prefs.SET_PASSWORD_DONE.key)] =
                            true
                    }
                }
                navigateToHome(navController)
            }
        }
        AnimatedVisibility(
            visible = user !is DataState.Loading && user is DataState.Success && user.data.password == null,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            var isDialogVisible by remember { mutableStateOf(false) }
            AnimatedVisibility(isDialogVisible) {
                AppAlertDialog(
                    dialogTitle = "Confirm",
                    dialogText = "Are you sure to save the details ? Account type can't be changed later",
                    onConfirmation = {
                        isDialogVisible = false
                        onEvent.invoke(SetUpScreenEvents.SaveChanges { dataState ->
                            if (dataState is DataState.Error) {
//                                TODO: Toast Message
                                return@SaveChanges
                            }
                            if (dataState is DataState.Success) {
                                scope.launch {
                                    pref.edit { pref ->
                                        pref[booleanPreferencesKey(Prefs.SET_PASSWORD_DONE.key)] =
                                            true
                                    }
                                }
                                navigateToHome(navController)
                            }
                        })
                    },
                    onDismissRequest = {
                        isDialogVisible = false
                    },
                    icon = Icons.Outlined.Warning
                )
            }

            LazyColumn(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
            ) {
                item(key = "image") {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        Image(
                            painterResource(Res.drawable.app_logo),
                            contentDescription = "Logo",
                            modifier = modifier.fillMaxWidth().fillMaxHeight()
                        )
                    }
                }
                item("Content") {
                    Column(
                        modifier = Modifier
                            .padding(MaterialTheme.spacing.medium)
                    ) {
                        Text("Your are ?")
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clickable {
                                    onEvent(SetUpScreenEvents.OnUserTypeChange(UserType.STUDENT))
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Checkbox(
                                checked = userType == UserType.STUDENT,
                                onCheckedChange = {
                                    onEvent(SetUpScreenEvents.OnUserTypeChange(UserType.STUDENT))
                                },
                                modifier = Modifier
                            )
                            Text("Student")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clickable {
                                    onEvent(SetUpScreenEvents.OnUserTypeChange(UserType.TEACHER))
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Checkbox(
                                checked = userType == UserType.TEACHER,
                                onCheckedChange = {
                                    onEvent(SetUpScreenEvents.OnUserTypeChange(UserType.TEACHER))
                                },
                                modifier = Modifier
                            )
                            Text("Teacher")
                        }
                        Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
                        TitleComposable(
                            title = "Set you password",
                            modifier = Modifier.fillMaxWidth()
                        )
                        PasswordEditTextCompose(
                            modifier = Modifier.fillMaxWidth(),
                            value = passWord,
                            onValueChange = {
                                onEvent(SetUpScreenEvents.OnPasswordChanged(it))
                            },
                            placeholder = "Password",
                            imeAction = ImeAction.Done
                        )
                        AnimatedVisibility(!isPasswordValid) {
                            Column {
                                Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
                                DisplayCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    border = BorderStroke(
                                        width = CardDefaults.outlinedCardBorder().width,
                                        color = MaterialTheme.colorScheme.error
                                    ),
                                ) {
                                    Column {
                                        Text(
                                            text = "Password must:\n" +
                                                    "• Be at least 8 characters long\n" +
                                                    "• Contain at least one uppercase letter\n" +
                                                    "• Contain at least one digit\n" +
                                                    "• Contain at least one special character",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                        BodyComposable(
                            body = "This allow you to access your account through desktop application"
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                        AnimatedVisibility(visible = isPasswordValid) {
                            ApplyButton(
                                text = "Done",
                                action = {
                                    isDialogVisible = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun navigateToHome(navController: NavController) {
    navController.navigate(MainScreenScreenRoutes.HomeScreen.route) {
        popUpTo(MainScreenScreenRoutes.HomeScreen.route) {
            inclusive = false
        }
    }
}

