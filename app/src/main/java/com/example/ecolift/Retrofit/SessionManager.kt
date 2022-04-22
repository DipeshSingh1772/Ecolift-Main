package com.example.ecolift.Retrofit

import android.content.Context
import android.content.SharedPreferences
import com.example.ecolift.R

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_NAME = ""
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun saveName(name:String){
        val editor = prefs.edit()
        editor.putString(USER_NAME,name)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchUserName():String?{
        return prefs.getString(USER_NAME, null)
    }

    fun clearAllTokens(){
        val editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

}