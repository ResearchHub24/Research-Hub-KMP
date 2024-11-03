package com.atech.research.utils


const val BaseDeepLinkUrl = "https://aka-rhub.netlify.app/"

sealed class DeepLink(val route: String) {
    data class OpenResearch(private val researchPath: String="no_path") :
        DeepLink("$BaseDeepLinkUrl/?researchPath=$researchPath")

}

