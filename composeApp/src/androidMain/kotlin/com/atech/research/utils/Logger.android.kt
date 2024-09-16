package com.atech.research.utils

import android.util.Log

actual fun researchHubLog(level: ResearchLogLevel, message: String) {
    when (level) {
        ResearchLogLevel.DEBUG -> Log.d(level.name.lowercase(), "researchHubLog: $message")
        ResearchLogLevel.INFO -> Log.i(level.name.lowercase(), "researchHubLog: $message")
        ResearchLogLevel.WARN -> Log.w(level.name.lowercase(), "researchHubLog: $message")
        ResearchLogLevel.ERROR -> Log.e(level.name.lowercase(), "researchHubLog: $message")
    }
}