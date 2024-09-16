package com.atech.research.utils

actual fun researchHubLog(level: ResearchLogLevel, message: String) {
    when (level) {
        ResearchLogLevel.DEBUG -> println("researchHubLog: $message")
        ResearchLogLevel.INFO -> println("researchHubLog: $message")
        ResearchLogLevel.WARN -> println("researchHubLog: $message")
        ResearchLogLevel.ERROR -> System.err.println("researchHubLog: $message")
    }
}