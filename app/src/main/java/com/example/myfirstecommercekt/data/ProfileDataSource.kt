package com.example.myfirstecommercekt.data

import android.content.*
import androidx.datastore.preferences.core.*
import com.example.myfirstecommercekt.*
import com.example.myfirstecommercekt.data.remote.api.*
import dagger.hilt.android.qualifiers.*
import kotlinx.coroutines.flow.*
import javax.inject.*


object UserData {
    val USER_ID = stringPreferencesKey("user_id")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
}

class UserDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    val userId: Flow<String?> = dataStore.data.map { it[UserData.USER_ID] }
    val userName: Flow<String?> = dataStore.data.map { it[UserData.USER_NAME] }
    val userEmail: Flow<String?> = dataStore.data.map { it[UserData.USER_EMAIL] }
    suspend fun saveUser(id: String, name: String, email: String) {
        dataStore.edit {
            it[UserData.USER_ID] = id
            it[UserData.USER_NAME] = name
            it[UserData.USER_EMAIL] = email
        }
    }

    suspend fun getUser(): User? {
        val prefs: Preferences = context.dataStore.data.first()
        val idStr = prefs[UserData.USER_ID]
        val name = prefs[UserData.USER_NAME]
        val email = prefs[UserData.USER_EMAIL]


        val id = idStr?.toIntOrNull() ?: return null
        if (name.isNullOrBlank() || email.isNullOrBlank()) return null

        return User(id = id, name = name, email = email)
    }

    suspend fun clearUser() {
        dataStore.edit { it.clear() }
    }

}