package com.atech.research.utils

/**
 * Is valid URL
 * This function is used to check if the string is a valid URL
 * @receiver String
 * @return [Boolean]
 */
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

/**
 * Open link
 * This function is used to open the link.
 * Platform specific implementation is provided by the platform module.
 */
expect class LinkHelper {
    /**
     * Open link
     * This function is used to open the link.
     * @param url The url
     */
    fun openLink(url: String)
}

