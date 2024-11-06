package com.atech.research.common

import KottieAnimation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition

/**
 * Lottie animation links
 *
 * @property link String
 * @constructor Create empty Lottie animation links
 */
enum class LottieAnimationLinks(val link: String) {
    SendNotification("https://lottie.host/c17856bb-c9e4-4fde-a66e-a8bd91a161ab/RHerAdvPqS.json"),
    NoteFound("https://lottie.host/9dcbe616-4bd5-450d-8637-868944d9218b/1ADhLhOw0L.json")
}

/**
 * Lottie anim
 * Composable to load Lottie animation
 * <br>
 * Uses [KottieAnimation](https://github.com/ismai117/kottie)
 *
 * @param modifier Modifier
 * @param link String link to the Lottie animation
 * @see LottieAnimationLinks
 */
@Composable
fun LottieAnim(
    modifier: Modifier = Modifier,
    link: String
) {
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.Url(link)
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        iterations = Int.MAX_VALUE
    )

    KottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = modifier
    )
}