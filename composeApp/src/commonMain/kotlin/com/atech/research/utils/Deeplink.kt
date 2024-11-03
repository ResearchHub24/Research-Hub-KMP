package com.atech.research.utils


const val BaseDeepLinkUrl = "https://aka-rhub.netlify.app"

sealed class DeepLink(val route: String) {
    data class OpenResearch(val researchPath: String = "{researchPath}") :
        DeepLink("$BaseDeepLinkUrl/research/${researchPath}")

}

