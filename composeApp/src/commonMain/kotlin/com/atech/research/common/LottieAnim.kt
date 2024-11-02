package com.atech.research.common

import KottieAnimation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition

enum class LottieAnimationLinks(val link: String) {
    SendNotification("https://lottie.host/c17856bb-c9e4-4fde-a66e-a8bd91a161ab/RHerAdvPqS.json")
}

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