package com.atech.research.utils

enum class Platform {
    ANDROID,
    DESKTOP,
}

expect fun getPlatformName(): Platform