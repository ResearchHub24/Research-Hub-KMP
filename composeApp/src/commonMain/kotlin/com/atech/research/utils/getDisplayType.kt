package com.atech.research.utils

import androidx.compose.runtime.Composable

enum class DeviceType {
    MOBILE, TABLET, DESKTOP
}


@Composable
expect fun getDisplayType(): DeviceType