package com.atech.research.utils

/**
 * Research log level
 * This class is used to represent the research log level
 */
enum class ResearchLogLevel {
    DEBUG, INFO, WARN, ERROR
}

/**
 * Research log
 * This function is used to log the research.
 * Please implement this function in the platform specific code.
 * @param level The log level
 * @param message The message
 */
expect fun researchHubLog(level: ResearchLogLevel, message: String)
