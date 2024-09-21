package com.atech.research.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.atech.research.ui.theme.captionColor
import com.atech.research.ui.theme.spacing
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo

@Composable
fun TitleComposable(
    modifier: Modifier = Modifier,
    title: String,
    padding: Dp = MaterialTheme.spacing.medium
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding),
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.captionColor
    )
}

@Composable
fun TextItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    endIcon: ImageVector? = null,
    onEndIconClick: (() -> Unit)? = null
) {
    Surface(modifier = Modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = modifier
                    .padding(MaterialTheme.spacing.medium),
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if (endIcon != null)
                IconButton(onClick = { onEndIconClick?.invoke() }) {
                    Icon(imageVector = endIcon, contentDescription = null)
                }
        }
    }
}

@Composable
fun DisplayCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    border: BorderStroke = CardDefaults.outlinedCardBorder(),
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Surface(modifier = modifier
        .clickable { onClick.invoke() }) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth(),
            border = border
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
                content = content
            )
        }
    }
}


@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Outlined.Edit,
    action: () -> Unit = {}
) {
    IconButton(
        onClick = action,
        modifier = modifier
    ) {
        Icon(imageVector = imageVector, contentDescription = "edit")
    }
}


@Composable
fun BottomPadding() {
    Spacer(
        modifier = Modifier.height(
            MaterialTheme.spacing.bottomPadding
        )
    )
}


fun LazyListScope.bottomPaddingLazy(key: String = "bottom_padding") {
    item(
        key = key
    ) {
        BottomPadding()
    }
}

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String,
    expand: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    var isExpand by rememberSaveable { mutableStateOf(expand) }
    Column(
        modifier = Modifier
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = isExpand,
                    onValueChange = { isExpand = it }
                )
                .padding(
                    horizontal = MaterialTheme.spacing.small
                )
                .height(
                    TextFieldDefaults.MinHeight
                )
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = modifier,
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.captionColor,
            )
            Icon(
                imageVector = if (isExpand) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                contentDescription = null
            )
        }
        AnimatedVisibility(isExpand) {
            Column(
                content = content
            )
        }
    }
}

@Composable
fun ProgressBar(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            trackColor = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.size(200.dp),
            strokeCap = StrokeCap.Round,
            strokeWidth = 10.dp,
        )
    }
}

@Composable
fun CardSection(
    modifier: Modifier = Modifier, title: String, content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            .5.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.captionColor
            )
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.large))
            content()
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.large))
        }
    }
}


@Composable
fun BodyComposable(
    modifier: Modifier = Modifier,
    body: String,
    padding: Dp = MaterialTheme.spacing.medium
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding),
        text = body,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.captionColor
    )
}

@Composable
fun EmptyWelcomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f)
        ) {
            Image(
                org.jetbrains.compose.resources.painterResource(Res.drawable.app_logo),
                contentDescription = "Logo",
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
        Text(
            "Welcome to Research Hub 🎉",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}