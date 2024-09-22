package com.atech.research.utils

interface PrefManager {
    fun saveString(key: String, value: String)
    fun getString(key: String): String
    fun saveInt(key: String, value: Int)
    fun getInt(key: String): Int
    fun saveBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
    fun clearAll()
    fun remove(key: String)
    fun contains(key: String): Boolean

    companion object {
        const val PREF_NAME = "user.preferences_pb"
    }
}