package com.example.myfirstecommercekt.data.local

import android.content.*
import androidx.datastore.preferences.core.*
import com.example.myfirstecommercekt.*
import dagger.hilt.android.qualifiers.*
import kotlinx.coroutines.flow.*
import javax.inject.*


object UserData {
    val USER_ID = stringPreferencesKey("user_id")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
}

data class User(val email: String, val name: String)
class UserDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    val userId: Flow<String?> = dataStore.data.map { it[UserData.USER_ID] }
    val userName: Flow<String?> = dataStore.data.map { it[UserData.USER_NAME] }
    val userEmail: Flow<String?> = dataStore.data.map { it[UserData.USER_EMAIL] }
    suspend fun saveUser(id: String, name: String, email: String) {
        dataStore.edit {
            it[UserData.USER_ID] = id.toString()
            it[UserData.USER_NAME] = name
            it[UserData.USER_EMAIL] = email
        }
    }

    suspend fun clearUser() {
        dataStore.edit { it.clear() }
    }

}