package com.atech.research.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox
import org.jetbrains.compose.resources.painterResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.ic_error

/**
 * Async image
 * Load image asynchronously
 *
 * @param modifier Modifier
 * @param isLoadCircular Boolean value to determine if the image should be circular
 * @param url String image url
 * @param errorImage Painter error image
 */
@Composable
fun AsyncImage(
    modifier: Modifier = Modifier,
    isLoadCircular: Boolean = false,
    url: String,
    errorImage: Painter = painterResource(Res.drawable.ic_error),
) {
    val imageModifier = Modifier.let { if (isLoadCircular) it.clip(CircleShape) else it }
    AutoSizeBox(
        url,
        modifier = modifier
    ) { action ->
        when (action) {
            is ImageAction.Success -> {
                Image(
                    modifier = imageModifier.fillMaxSize(),
                    painter = rememberImageSuccessPainter(action),
                    contentDescription = "image",
                )
            }

            is ImageAction.Loading -> {
                CircularProgressIndicator()
            }

            is ImageAction.Failure -> {
                Image(
                    modifier = modifier.fillMaxSize(),
                    painter = errorImage,
                    contentDescription = "error",
                )
            }
        }
    }
}