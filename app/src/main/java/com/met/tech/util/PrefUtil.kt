package com.met.tech.util

import android.content.SharedPreferences
import androidx.core.content.edit

class PrefsUtil(
    private val pref: SharedPreferences
) {

    companion object {
        const val SHARED_PREFERENCE_ID = "STORE_APP_PREF"
        private const val KEY_DARK_MODE = "KEY_DARK_MODE"
        private const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
        private const val USER_MOBILE_NO = "USER_MOBILE_NO"
        private const val IS_LOGIN = "IS_LOGIN"
        private const val DEVICE_ID = "DEVICE_ID"
        private const val LANG = "LANGUAGE"
        private const val LATITUDE = "LATITUDE"
        private const val LONGITUDE = "LONGITUDE"

        object Login {
            const val KEY_LL_STATUS = "KEY_LL_STATUS"
            const val KEY_LL_NAME = "KEY_LL_NAME"
        }
    }


    var isLogin: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)
        set(value) = pref.edit()
            .putBoolean(IS_LOGIN, value)
            .apply()


    var lang: String
        get() = pref.getString(LANG, "")
            .toString()
        set(value) = pref.edit()
            .putString(LANG, value)
            .apply()


    fun logout() {
        pref.edit { clear() }
    }

    var accessToken: String?
        get() = pref.getString(KEY_ACCESS_TOKEN, null)
        set(value) = pref.edit { putString(KEY_ACCESS_TOKEN, value) }

}