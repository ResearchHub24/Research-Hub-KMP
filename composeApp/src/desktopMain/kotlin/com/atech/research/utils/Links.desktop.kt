package com.atech.research.utils

import java.awt.Desktop
import java.net.URI


actual class LinkHelper {
    actual fun openLink(url: String) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop()
                .isSupported(Desktop.Action.BROWSE)
        ) {
            try {
                Desktop.getDesktop().browse(URI(url))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            println("Opening links is not supported on this platform.")
        }
    }
}