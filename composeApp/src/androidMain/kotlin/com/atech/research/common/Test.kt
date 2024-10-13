package com.atech.research.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.ui.theme.ResearchHubTheme

@Composable
private fun DetailScreen(
    modifier: Modifier = Modifier,
    researchModel: ResearchModel
) {

}

@Preview(showBackground = true)
@Preview(device = TABLET, showBackground = true)
@Composable
private fun TestPreview() {
    ResearchHubTheme() {
        DetailScreen(
            researchModel = ResearchModel(
                title = "Enhancing Multiplatform Research Collaboration: A Study on the Development of the \\\"Research Hub\\\" Application",
                description = """### **Abstract:**  \r\nIn today's interconnected academic world, effective collaboration among researchers across different platforms is crucial for advancing scientific discovery. This paper presents the development of a multiplatform research collaboration application, \"Research Hub,\" designed to bridge the gap between educators and students by offering seamless, cross-device research opportunities. The project leverages cutting-edge technologies, including Kotlin Multiplatform (KMP), Ktor server for backend API interactions, and Firebase for real-time data management. By facilitating enhanced communication, document sharing, and push notifications, Research Hub empowers research teams to collaborate efficiently, irrespective of the platform—be it Android, Windows, Linux, or macOS. This paper explores the architecture, design considerations, and real-world application of the platform, as well as its potential to reshape research collaboration.\r\n\r\n### **Introduction:**  \r\nThe rapid evolution of technology has significantly impacted research methodologies, leading to the need for innovative tools that support research teams in managing tasks, communication, and collaboration. However, the challenge of ensuring consistent functionality across various devices remains a major obstacle. This paper explores the design and implementation of Research Hub, a multiplatform application that overcomes these challenges by offering a single, unified environment for research collaboration.\r\n\r\n### **System Architecture:**  \r\nResearch Hub utilizes Kotlin Multiplatform, enabling native performance across multiple operating systems. The backend is powered by Ktor, serving as a flexible and lightweight server solution to handle API requests. Firebase is integrated for real-time database capabilities and push notification services, ensuring seamless communication and collaboration.\r\n\r\n### **Features and Technologies:**  \r\n1. **Cross-Platform Compatibility:** Using Kotlin Multiplatform to ensure native performance across all major platforms, including Android, macOS, Linux, and Windows.\r\n2. **Backend API Interaction with Ktor:** Enabling efficient data exchange between the app and the cloud.\r\n3. **Firebase Integration:** Utilizing Firebase for real-time data management and push notifications, ensuring that research updates are immediately available.\r\n4. **Web Services for Notifications:** A web-based notification service is integrated to keep users updated on collaboration progress, tasks, and deadlines.\r\n5. **User Interface:** Built using Compose for Android with a focus on simplicity and usability, ensuring a smooth user experience.\r\n6. **Dependency Injection:** Implemented using Koin to ensure scalable and maintainable code across the application.\r\n\r\n### **Impact on Research Collaboration:**  \r\nThis application transforms how researchers interact and collaborate, simplifying project management and communication. The integration of multiplatform support allows diverse teams to collaborate effortlessly, regardless of the device they are using. Additionally, the platform's use of real-time data and push notifications enables swift responses to research changes and deadlines, enhancing productivity.\r\n\r\n### **Conclusion:**  \r\nResearch Hub presents a novel solution to the challenges of multiplatform research collaboration. By integrating cutting-edge technologies like Kotlin Multiplatform and Firebase, this application bridges the gap between researchers across different operating systems. Future iterations will focus on incorporating machine learning for advanced document analysis, thus adding another layer of innovation to the research process.\r\n\r\n### **Keywords/Tags:**  \r\nMultiplatform, Research Collaboration, Kotlin Multiplatform, KMP, Firebase, Ktor, Real-Time Communication, Research Hub, Cross-Platform Development, Push Notifications, Academic Collaboration.\r\n\r\n---\r\n""".trimIndent(),
                authorUid = "",
                author = "Ayaan",
                path = "",
                authorEmail = "ayaan35200@gmail.com",
                authorPhoto = "",
                tags = listOf(
                    TagModel(
                        createdBy = "X",
                        name = "ML"
                    ),

                    TagModel(
                        createdBy = "X",
                        name = "AI"
                    ),
                    TagModel(
                        createdBy = "X",
                        name = "Research"
                    ),

                    TagModel(
                        createdBy = "X",
                        name = "Android"
                    ),
                )
            )
        )
    }
}