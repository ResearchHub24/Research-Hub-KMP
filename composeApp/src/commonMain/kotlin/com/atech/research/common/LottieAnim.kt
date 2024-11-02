package com.atech.research.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class LottieAnimationLinks(val link: String) {
    SendNotification("https://lottie.host/b55c8432-a0ce-441d-ac43-44ca7e404bea/4HiiqoPvB8.lottie")
}

@Composable
fun LottieAnim(
    modifier: Modifier = Modifier,
    link: String
) {
//    val animFromUrl by rememberLottieComposition {
//        LottieCompositionSpec.Url(link)
//    }
//    Image(
//        modifier = modifier,
//        painter = rememberLottiePainter(
//            composition = animFromUrl,
//            iterations = Compottie.IterateForever
//        ),
//        contentDescription = "Lottie animation"
//    )
}