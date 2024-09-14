package com.atech.research.utils

enum class Platform {
    ANDROID,
    DESKTOP,
}

expect fun getPlatformName(): Platform


fun isAndroid(): Boolean = getPlatformName() == Platform.ANDROID