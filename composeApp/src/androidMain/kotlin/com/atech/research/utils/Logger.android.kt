package com.atech.research.utils

import android.util.Log

actual fun researchHubLog(level: LogLevel, message: String) {
    when (level) {
        LogLevel.DEBUG -> Log.d(level.name.lowercase(), "researchHubLog: $message")
        LogLevel.INFO -> Log.i(level.name.lowercase(), "researchHubLog: $message")
        LogLevel.WARN -> Log.w(level.name.lowercase(), "researchHubLog: $message")
        LogLevel.ERROR -> Log.e(level.name.lowercase(), "researchHubLog: $message")
    }
}