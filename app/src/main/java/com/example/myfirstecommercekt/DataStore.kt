package com.example.myfirstecommercekt

import android.content.*
import androidx.datastore.core.*
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*

private const val USER_PREFS_NAME = "user_prefs"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFS_NAME
)