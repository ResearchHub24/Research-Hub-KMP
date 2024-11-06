package com.atech.research.utils

/**
 * PrefManager
 * This interface is used to manage the preferences
 */
interface PrefManager {
    /**
     * Save string
     * This function is used to save the string
     * @param key The key
     * @param value The value
     */
    fun saveString(key: String, value: String)

    /**
     * Get string
     * This function is used to get the string
     * @param key The key
     * @return [String]
     */
    fun getString(key: String): String

    /**
     * Save int
     * This function is used to save the int
     * @param key The key
     * @param value The value
     */
    fun saveInt(key: String, value: Int)

    /**
     * Get int
     * This function is used to get the int
     * @param key The key
     * @return [Int]
     */
    fun getInt(key: String): Int

    /**
     * Save long
     * This function is used to save the long
     * @param key The key
     * @param value The value
     */
    fun saveBoolean(key: String, value: Boolean)

    /**
     * Get boolean
     * This function is used to get the boolean
     * @param key The key
     * @return [Boolean]
     */
    fun getBoolean(key: String): Boolean

    /**
     * Clear all
     * This function is used to clear all the preferences
     */
    fun clearAll()

    /**
     * Remove
     * This function is used to remove the preference
     * @param key The key
     */
    fun remove(key: String)

    /**
     * Contains
     * This function is used to check if the preference contains the key
     * @param key The key
     * @return [Boolean]
     */
    fun contains(key: String): Boolean

    companion object {
        const val PREF_NAME = "user.preferences_pb"
    }
}

/**
 * Prefs
 * This enum class is used to represent the preferences
 */
enum class Prefs {
    USER_ID, USER_NAME, USER_EMAIL, USER_PROFILE_URL,
    SET_PASSWORD_DONE, USER_TYPE,
}