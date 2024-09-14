package com.atech.research

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.atech.research.ui.compose.main.Container
import com.atech.research.ui.navigation.ResearchNavigationGraph
import com.atech.research.ui.theme.ResearchHubTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(
) {
    ResearchHubTheme {
        val navController = rememberNavController()
        ResearchNavigationGraph(
            modifier = Modifier, navHostController = navController
        )
//        Container()
    }
}
//var showContent by remember { mutableStateOf(false) }
//Scaffold {
//    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//        Button(onClick = { showContent = !showContent }) {
//            Text("Click me!")
//        }
//        AnimatedVisibility(showContent) {
//            val greeting = remember { Greeting().greet() }
//            Column(
//                Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(painterResource(Res.drawable.compose_multiplatform), null)
//                Text("Compose: $greeting")
//            }
//        }
//    }
//}