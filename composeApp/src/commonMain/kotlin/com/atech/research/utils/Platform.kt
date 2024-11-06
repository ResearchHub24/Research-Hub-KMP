package com.atech.research.utils

/**
 * Platform
 * This class is used to represent the platform
 */
enum class Platform {
    ANDROID,
    DESKTOP,
}

/**
 * Get platform name
 * This function is used to get the platform name
 * Platform specific implementation is provided by the platform module.
 * @return [Platform]
 */
expect fun getPlatformName(): Platform

/**
 * Is android
 * This function is used to check if the platform is android
 * @return [Boolean]
 */
fun isAndroid(): Boolean = getPlatformName() == Platform.ANDROID