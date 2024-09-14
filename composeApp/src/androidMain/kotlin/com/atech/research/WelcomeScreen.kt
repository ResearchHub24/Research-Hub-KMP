//package com.atech.research
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import com.atech.research.common.MainContainer
//import com.atech.research.ui.theme.ResearchHubTheme
//import researchhub.composeapp.generated.resources.Res
//import researchhub.composeapp.generated.resources.app_logo
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun WelcomeScreen(
//    modifier: Modifier = Modifier
//) {
//    MainContainer(){ contentPadding ->
//        Column(
//            modifier = Modifier.padding(contentPadding)
//        ) {
//            Box(
//                modifier = Modifier
//                    .background(MaterialTheme.colorScheme.primary)
//                    .fillMaxWidth()
//                    .fillMaxHeight(.5f)
//            ) {
//                Image(
//                    painterResource(Res.drawable.app_logo),
//                    contentDescription = "Logo",
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                )
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun WelcomeScreenPreview() {
//     ResearchHubTheme {
//        WelcomeScreen()
//    }
//}