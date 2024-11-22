package com.example.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.domain.common.Constants.Companion.TOKEN_KEY
import com.example.domain.model.user.UserResponse
import com.example.domain.repositories.sessionManager.SessionManagerRepository
import com.google.gson.Gson

class SessionManagerRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SessionManagerRepository {
    override fun saveUserData(userResponse: UserResponse) {
        val userDataJson = Gson().toJson(userResponse)
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
        sharedPreferences.edit().putString(TOKEN_KEY, userDataJson).apply()
        Log.d("SessionManagerImpl", "User data updated: $userDataJson")
    }

    override fun getUserData(): UserResponse? {
        val userDataJson = sharedPreferences.getString(TOKEN_KEY, null)
        return try {
            if (userDataJson != null) Gson().fromJson(
                userDataJson,
                UserResponse::class.java
            ) else null
        } catch (e: Exception) {
            Log.e("SessionManagerImpl", "Error parsing user data: ${e.message}")
            null
        }
    }

    override fun logout() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
    }
}