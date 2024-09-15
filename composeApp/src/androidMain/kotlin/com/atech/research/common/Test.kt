import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.atech.research.ui.theme.ResearchHubTheme


@Preview(showBackground = true)
@Composable
private fun TestPreview() {
    ResearchHubTheme(
        isDark = true
    ) {
    }
}