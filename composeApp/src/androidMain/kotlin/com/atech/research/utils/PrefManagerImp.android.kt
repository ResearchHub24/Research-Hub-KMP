package com.atech.research.utils

import android.content.SharedPreferences

class PrefManagerImp(
    private val prefManager: SharedPreferences
) : PrefManager {
    override fun saveString(key: String, value: String) =
        prefManager.edit().putString(key, value).apply()


    override fun getString(key: String): String =
        prefManager.getString(key, "") ?: ""


    override fun saveInt(key: String, value: Int) =
        prefManager.edit().putInt(key, value).apply()


    override fun getInt(key: String): Int =
        prefManager.getInt(key, 0)


    override fun saveBoolean(key: String, value: Boolean) =
        prefManager.edit().putBoolean(key, value).apply()


    override fun getBoolean(key: String): Boolean =
        prefManager.getBoolean(key, false)


    override fun clearAll() =
        prefManager.edit().clear().apply()


    override fun remove(key: String) =
        prefManager.edit().remove(key).apply()


    override fun contains(key: String): Boolean =
        prefManager.contains(key)

}