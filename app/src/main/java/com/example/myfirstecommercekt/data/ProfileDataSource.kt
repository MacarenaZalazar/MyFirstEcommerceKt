package com.example.myfirstecommercekt.data

import android.content.*
import androidx.datastore.preferences.core.*
import com.example.myfirstecommercekt.*
import dagger.hilt.android.qualifiers.*
import kotlinx.coroutines.flow.*
import javax.inject.*


object UserData {

    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
}

data class User(val email: String, val name: String)
class UserDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    val userName: Flow<String?> = dataStore.data.map { it[UserData.USER_NAME] }
    val userEmail: Flow<String?> = dataStore.data.map { it[UserData.USER_EMAIL] }
    suspend fun saveUser(name: String, email: String) {
        dataStore.edit {
            it[UserData.USER_NAME] = name
            it[UserData.USER_EMAIL] = email
        }
    }

    suspend fun getUser(): User? {
        val prefs: Preferences = context.dataStore.data.first()
        val name = prefs[UserData.USER_NAME]
        val email = prefs[UserData.USER_EMAIL]



        if (name.isNullOrBlank() || email.isNullOrBlank()) return null

        return User(name = name, email = email)
    }

    suspend fun clearUser() {
        dataStore.edit { it.clear() }
    }

}