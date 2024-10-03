package com.freshervnc.pharmacycounter.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefer(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Save user data
    fun saveToken(token: String?, name: String?, phone: String?, email: String?, address: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_FULL_NAME, name)
        editor.putString(KEY_PHONE, phone)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_ADDRESS, address)
        editor.apply()
    }

    val token: String? get() = sharedPreferences.getString(KEY_TOKEN, null)
    val name: String? get() = sharedPreferences.getString(KEY_FULL_NAME, null)
    val phone: String? get() = sharedPreferences.getString(KEY_PHONE, null)
    val email: String? get() = sharedPreferences.getString(KEY_EMAIL, null)
    val address: String? get() = sharedPreferences.getString(KEY_ADDRESS, null)

    fun clearUserData() {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_TOKEN)
        editor.remove(KEY_FULL_NAME)
        editor.remove(KEY_PHONE)
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_ADDRESS)
        editor.apply()
    }

    companion object {
        private const val PREF_NAME = "UserData"
        private const val KEY_TOKEN = "token"
        private const val KEY_FULL_NAME = "name"
        private const val KEY_PHONE = "phone"
        private const val KEY_EMAIL = "email"
        private const val KEY_ADDRESS = "address"
    }
}