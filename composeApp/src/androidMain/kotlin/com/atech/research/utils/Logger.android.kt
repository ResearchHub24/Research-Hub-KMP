package com.atech.research.utils

import android.util.Log


private const val TAG = "ResearchHub"

actual fun researchHubLog(level: ResearchLogLevel, message: String) {
    when (level) {
        ResearchLogLevel.DEBUG -> Log.d(TAG, "researchHubLog: $message")
        ResearchLogLevel.INFO -> Log.i(TAG, "researchHubLog: $message")
        ResearchLogLevel.WARN -> Log.w(TAG, "researchHubLog: $message")
        ResearchLogLevel.ERROR -> Log.e(TAG, "researchHubLog: $message")
    }
}