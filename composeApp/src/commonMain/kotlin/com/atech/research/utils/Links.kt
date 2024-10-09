package com.atech.research.utils

fun String.isValidUrl(): Boolean {
    // Regular expression to match a valid URL structure
    val regex = "^(https?://)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}(/.*)?$".toRegex()

    // Add https:// if it's missing
    val urlWithScheme = if (!this.startsWith("https://")) {
        "https://$this"
    } else {
        this
    }

    // Return whether the modified URL matches the regex
    return urlWithScheme.matches(regex)
}

expect class LinkHelper{
    fun openLink(url: String)
}

