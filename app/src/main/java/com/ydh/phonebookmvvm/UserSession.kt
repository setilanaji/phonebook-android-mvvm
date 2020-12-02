package com.ydh.phonebookmvvm

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserSession(private val context: Context){
    companion object{
        const val SHARED_NAME = "com.ydh.phoneboookmvvm"

        const val TOKEN_KEY = "TOKEN_KEY"
        const val LOGGED_KEY = "LOGGED_KEY"
        const val LOGIN_USER_EMAIL = "LOGIN_USER_EMAIL"
        const val LOGIN_USER_PASSWORD = "LOGIN_USER_PASSWORD"
        const val LOGIN_USER_NAME = "LOGIN_USER_NAME"

    }

    private val prefs: SharedPreferences by lazy{
        context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    }

    var loggedIn : Boolean
        get() = prefs.getBoolean(LOGGED_KEY, false)
        set(value) = prefs.edit { putBoolean(LOGGED_KEY, value) }

    var password : String?
        get() = prefs.getString(LOGIN_USER_PASSWORD, "")
        set(value) = prefs.edit { putString(LOGIN_USER_PASSWORD, value) }

    var email : String?
        get() = prefs.getString(LOGIN_USER_EMAIL, "")
        set(value) = prefs.edit { putString(LOGIN_USER_EMAIL, value) }

    var name : String?
        get() = prefs.getString(LOGIN_USER_NAME, "")
        set(value) = prefs.edit { putString(LOGIN_USER_NAME, value) }

    var token : String?
        get() = prefs.getString(TOKEN_KEY, "")
        set(value) = prefs.edit { putString(TOKEN_KEY, value) }


}