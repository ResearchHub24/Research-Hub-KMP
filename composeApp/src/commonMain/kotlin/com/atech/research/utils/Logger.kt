package com.atech.research.utils

enum class LogLevel {
    DEBUG, INFO, WARN, ERROR
}

expect fun researchHubLog(level: LogLevel, message: String)
