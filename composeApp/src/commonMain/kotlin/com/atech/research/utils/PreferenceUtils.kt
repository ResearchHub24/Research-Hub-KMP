package com.atech.research.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class PreferenceUtils private constructor(private val pref: DataStore<Preferences>) {

    fun getAllPref() = pref.data

    @Composable
    fun getBooleanPrefAsState(key: String): State<Boolean> = pref.data.map {
        it[booleanPreferencesKey(key)] == true
    }.collectAsState(initial = false)

    @Composable
    fun getStringPrefAsState(key: String): State<String> = pref.data.map {
        it[stringPreferencesKey(key)] ?: ""
    }.collectAsState(initial = "")

    @Composable
    fun getIntPrefAsState(key: String): State<Int> = pref.data.map {
        it[intPreferencesKey(key)] ?: 0
    }.collectAsState(initial = 0)


    fun getIntPref(key: String): Int = runBlocking {
        pref.data.map {
            it[intPreferencesKey(key)] ?: 0
        }.map { it }.first()
    }

    fun getBooleanPref(key: String): Boolean = runBlocking {
        pref.data.map {
            it[booleanPreferencesKey(key)] == true
        }.map { it }.first()
    }

    fun getStringPref(key: String): String = runBlocking {
        pref.data.map {
            it[stringPreferencesKey(key)] ?: ""
        }.map { it }.first()
    }

    class Builder(private val pref: DataStore<Preferences>) {
        private val edits = mutableListOf<suspend (MutablePreferences) -> Unit>()

        fun saveBooleanPref(key: String, value: Boolean) = apply {
            edits.add { it[booleanPreferencesKey(key)] = value }
        }

        fun saveStringPref(key: String, value: String) = apply {
            edits.add { it[stringPreferencesKey(key)] = value }
        }

        fun saveIntPref(key: String, value: Int) = apply {
            edits.add { it[intPreferencesKey(key)] = value }
        }

        fun build(): PreferenceUtils {
            runBlocking {
                pref.edit { prefs ->
                    edits.forEach { it(prefs) }
                }
            }
            return PreferenceUtils(pref)
        }
    }

    companion object {
        fun builder(pref: DataStore<Preferences>) = Builder(pref)
    }
}

