package com.atech.research.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class PrefManagerImp(
    private val pref: DataStore<Preferences>
) : PrefManager {
    override fun saveString(key: String, value: String): Unit = runBlocking {
        pref.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    override fun getString(key: String): String = runBlocking {
        pref.data.map {
            it[stringPreferencesKey(key)] ?: ""
        }.map { it }.first()
    }

    override fun saveInt(key: String, value: Int): Unit = runBlocking {
        pref.edit {
            it[intPreferencesKey(key)] = value
        }
    }

    override fun getInt(key: String): Int = runBlocking {
        pref.data.map {
            it[intPreferencesKey(key)] ?: 0
        }.map { it }.first()
    }

    override fun saveBoolean(key: String, value: Boolean): Unit = runBlocking {
        pref.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    override fun getBoolean(key: String): Boolean = runBlocking {
        pref.data.map {
            it[booleanPreferencesKey(key)] == true
        }.map { it }.first()
    }

    override fun clearAll(): Unit = runBlocking {
        pref.edit {
            it.clear()
        }
    }

    override fun remove(key: String): Unit = runBlocking {
        pref.edit {
            it.remove(stringPreferencesKey(key))
        }
    }

    override fun contains(key: String): Boolean = runBlocking {
        pref.data.map {
            it.contains(stringPreferencesKey(key))
        }.map { it }.first()
    }
}