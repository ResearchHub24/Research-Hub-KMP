package com.atech.research.utils


/**
 * Base deep link url
 */
const val BaseDeepLinkUrl = "https://aka-rhub.netlify.app"

/**
 * Deep link
 * This sealed class is used to represent the deep link
 * @property route The route
 * @constructor Create empty Deep link
 * @param route The route
 */
sealed class DeepLink(val route: String) {
    data class OpenResearch(val researchPath: String = "{researchPath}") :
        DeepLink("$BaseDeepLinkUrl/research/${researchPath}")

}

