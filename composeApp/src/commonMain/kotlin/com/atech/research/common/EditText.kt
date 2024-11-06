/*
 *  Created by aiyu
 *  Copyright (c) 2021 . All rights reserved.
 *  BIT App
 *
 */

package com.atech.research.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Edit text
 * Custom edit text
 *
 * @param modifier Modifier
 * @param value String default value
 * @param placeholder String placeholder
 * @param onValueChange (String) -> Unit action to perform when the value changes
 * @param clearIconClick () -> Unit action to perform when the clear icon is clicked
 * @param isError Boolean value to determine if the edit text has an error
 * @param errorMessage String error message
 * @param supportingMessage String supporting message
 * @param keyboardOptions KeyboardOptions
 * @param focusRequester FocusRequester
 * @param enable Boolean value to determine if the edit text is enabled
 * @param colors TextFieldColors
 * @param leadingIcon (@Composable () -> Unit)? leading icon
 * @param trailingIcon @Composable (() -> Unit)? trailing icon
 * @param maxLines Int maximum lines
 * @param readOnly Boolean value to determine if the edit text is read only
 * @param interactionSource MutableInteractionSource
 */
@Composable
fun EditText(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit = {},
    clearIconClick: () -> Unit = {},
    isError: Boolean = false,
    errorMessage: String = "",
    supportingMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusRequester: FocusRequester? = null,
    enable: Boolean = true,
    colors: TextFieldColors = textFieldColors(),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = {
        if (value.isNotBlank()) Icon(imageVector = Icons.Outlined.Clear,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                clearIconClick()
            })
    },
    maxLines: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    LaunchedEffect(focusRequester) {
//        awaitFrame()
        focusRequester?.requestFocus()
    }

    OutlinedTextField(
        modifier = modifier.let {
            if (focusRequester == null) it
            else it.focusRequester(focusRequester)
        },
        value = value,
        maxLines = maxLines,
        onValueChange = onValueChange,
        label = {
            Text(text = placeholder)
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = colors,
        isError = isError,
        supportingText = {
            Text(
                text = if (isError) errorMessage else supportingMessage,
            )
        },
        keyboardOptions = keyboardOptions,
        enabled = enable,
        readOnly = readOnly,
        interactionSource = interactionSource,
    )
}

/**
 * Edit text enhance
 *
 * @param modifier Modifier
 * @param value String default value
 * @param placeholder String placeholder
 * @param onValueChange (String) -> Unit action to perform when the value changes
 * @param clearIconClick () -> Unit action to perform when the clear icon is clicked
 * @param isError Boolean value to determine if the edit text has an error
 * @param supportingText @Composable (() -> Unit)? supporting text
 * @param keyboardOptions KeyboardOptions
 * @param focusRequester FocusRequester
 * @param enable Boolean value to determine if the edit text is enabled
 * @param colors TextFieldColors
 * @param leadingIcon (@Composable () -> Unit)? leading icon
 * @param trailingIcon @Composable (() -> Unit)? trailing icon
 * @param maxLines Int maximum lines
 * @param readOnly Boolean value to determine if the edit text is read only
 * @param keyboardActions KeyboardActions
 * @param interactionSource MutableInteractionSource
 * @param singleLine Boolean value to determine if the edit text is single line
 */
@Composable
fun EditTextEnhance(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit = {},
    clearIconClick: () -> Unit = {},
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusRequester: FocusRequester? = null,
    enable: Boolean = true,
    colors: TextFieldColors = textFieldColors(),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = {
        if (value.isNotBlank()) Icon(imageVector = Icons.Outlined.Clear,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                clearIconClick()
            })
    },
    maxLines: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    singleLine : Boolean = false
) {

    LaunchedEffect(focusRequester) {
//        awaitFrame()
        focusRequester?.requestFocus()
    }

    OutlinedTextField(
        modifier = modifier.let {
            if (focusRequester == null) it
            else it.focusRequester(focusRequester)
        },
        value = value,
        maxLines = maxLines,
        onValueChange = onValueChange,
        label = {
            Text(text = placeholder)
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = colors,
        isError = isError,
        supportingText = supportingText,
        keyboardOptions = keyboardOptions,
        enabled = enable,
        readOnly = readOnly,
        interactionSource = interactionSource,
        keyboardActions = keyboardActions,
        singleLine = singleLine
    )
}

//@Composable
//fun MutableInteractionSource.clickable(
//    action: () -> Unit
//) = this.also { interactionSource ->
//    LaunchedEffect(key1 = interactionSource) {
//        interactionSource.interactions.collect {
//            if (it is PressInteraction.Release) {
//                action()
//            }
//        }
//    }
//
//}

/**
 * Password edit text compose
 * Custom password edit text
 *
 * @param modifier Modifier
 * @param value String default value
 * @param placeholder String placeholder
 * @param imeAction ImeAction
 * @param onValueChange (String) -> Unit action to perform when the value changes
 */
@Composable
fun PasswordEditTextCompose(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit = {}
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = {
            Text(placeholder)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        trailingIcon = {
            if (value.isNotBlank()) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Outlined.Visibility
                        else Icons.Outlined.VisibilityOff, contentDescription = null
                    )
                }
            }
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}

/**
 * Text field colors
 */
@Composable
fun textFieldColors() = TextFieldDefaults.colors(
    cursorColor = MaterialTheme.colorScheme.primary,
    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
    unfocusedContainerColor = MaterialTheme.colorScheme.surface
)
