import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Minimize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atech.research.common.EditText
import com.atech.research.common.GoogleButton
import com.atech.research.common.PasswordEditTextCompose
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.ui.theme.captionColor
import com.atech.research.ui.theme.spacing

@Composable
fun Test(
    modifier: Modifier = Modifier,
    isAndroid: Boolean = true
) {
    Column(
        modifier = modifier
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isAndroid) {
            GoogleButton { }
        } else {
            EditText(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = "Email",
            )
            PasswordEditTextCompose(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = "Password",
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            GoogleButton(
                icon = Icons.Outlined.AccountCircle
            ) { }
            Text(
                text = "Warning: You need to log in from a mobile device and set up your account before logging in on PC.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.captionColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AppWindowTitleBar(
    title :String = "Research Hub",
    icon : Painter? = null,
) = Column(
    modifier = Modifier.background(MaterialTheme.colorScheme.surface),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier.padding(start = MaterialTheme.spacing.medium)
                )
            }
        Text(
            modifier = Modifier
                .padding(start = MaterialTheme.spacing.medium),
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
        )
        }
        Row {
            IconButton(
                onClick = { /*TODO*/ },
                colors = iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(imageVector = Icons.Outlined.Minimize, contentDescription = null)
            }
            IconButton(
                onClick = { /*TODO*/ },
                colors = iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(imageVector = Icons.Outlined.CheckBoxOutlineBlank, contentDescription = null)
            }
            IconButton(
                onClick = { /*TODO*/ },
                colors = iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TestPreview() {
    ResearchHubTheme(
        isDark = true
    ) {
        AppWindowTitleBar()
    }
}