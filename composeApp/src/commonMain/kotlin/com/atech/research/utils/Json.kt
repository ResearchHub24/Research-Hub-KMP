package com.atech.research.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * To json
 * This function is used to convert the object to json
 * @receiver T
 * @return [String]
 */
inline fun <reified T> T.toJson(): String {
    return Json.encodeToString(this)
}