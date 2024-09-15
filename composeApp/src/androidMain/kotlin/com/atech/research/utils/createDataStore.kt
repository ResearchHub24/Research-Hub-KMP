package com.atech.research.utils


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.atech.research.core.pref.DATA_STORE_FILE_NAME

fun createDataStore(
    context: Context,
): DataStore<Preferences> = com.atech.research.core.pref.createDataStore {
    context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
}



