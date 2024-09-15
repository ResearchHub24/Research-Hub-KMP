package com.atech.research.utils

actual fun researchHubLog(level: LogLevel, message: String) {
    when (level) {
        LogLevel.DEBUG -> println("researchHubLog: $message")
        LogLevel.INFO -> println("researchHubLog: $message")
        LogLevel.WARN -> println("researchHubLog: $message")
        LogLevel.ERROR -> System.err.println("researchHubLog: $message")
    }
}