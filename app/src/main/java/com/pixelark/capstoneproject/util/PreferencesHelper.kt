package com.pixelark.capstoneproject.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val key = "KTechPreferences"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(key, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun putString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun remove(key: String) {
        editor.remove(key).apply()
    }

    fun clear() {
        editor.clear().apply()
    }
}
