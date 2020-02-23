package com.cuncis.simpledice

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PrefsManager {
    companion object {
        private const val KEY_VALUE = "value"

        private fun getPrefManager(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun setPrefValue(context: Context, number: Int?) {
            val editor = getPrefManager(context).edit()
            editor.putInt(KEY_VALUE, number!!)
            editor.apply()
        }

        fun getValue(context: Context): Int {
            return getPrefManager(context).getInt(KEY_VALUE, 1)
        }

        fun clearPref(context: Context) {
            val editor = getPrefManager(context).edit()
            editor.clear()
            editor.apply()
        }
    }
}