package com.atech.research.utils

enum class ResearchLogLevel {
    DEBUG, INFO, WARN, ERROR
}

expect fun researchHubLog(level: ResearchLogLevel, message: String)
