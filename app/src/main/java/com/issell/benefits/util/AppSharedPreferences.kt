package com.issell.benefits.util

import android.content.Context
import android.content.SharedPreferences
import com.issell.benefits.session.SessionManager


class AppSharedPreferences(context: Context) {
    companion object {
        private const val FILENAME = "prefs"
        private const val AUTO_LOGIN = "auto_login"
        private const val UUID = "session_uuid" // String
        private const val AUTH_TYPE = "auth_type" // Int?
        private const val ACCESS_TOKEN = "session_access_token" // String?
        private const val REFRESH_TOKEN = "session_refresh_token" // String?
        private const val NAVER_GRANT_TYPE = "naver_grant_type" // String?
        private const val BEGIN_AT = "session_start" // Long? (최초 세션 시작 UTC)
        private const val EXPIRE_AT = "session_expire" // Long? (세션 시작 UTC + 만료시간)
    }
    private val prefs: SharedPreferences = context.getSharedPreferences(FILENAME, 0)

    var autoLogin: Boolean
        get() = prefs.getBoolean(AUTO_LOGIN, false)
        set(value) = prefs.edit().putBoolean(AUTO_LOGIN, value).apply()

    var uuid: String = java.util.UUID.randomUUID().toString()
        get() = prefs.getString(UUID, java.util.UUID.randomUUID().toString())!!
        private set

    var authType: Int
        get() = prefs.getInt( AUTH_TYPE, SessionManager.AUTH_TYPE_UNDEFINED )
        set(value) = prefs.edit().putInt(AUTH_TYPE, value).apply()

    var accessToken: String?
        get() = prefs.getString(ACCESS_TOKEN, null)
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()

    var refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, null)
        set(value) = prefs.edit().putString(REFRESH_TOKEN, value).apply()

    var naverGrantType: String?
        get() = prefs.getString(NAVER_GRANT_TYPE, null)
        set(value) = prefs.edit().putString(NAVER_GRANT_TYPE, value).apply()

    var beginAt: Long
        get() = prefs.getLong(
            BEGIN_AT,
            SessionManager.SESSION_NOT_CREATED
        )
        set(value) = prefs.edit().putLong(BEGIN_AT, value).apply()

    var expireAt: Long
        get() = prefs.getLong(
            EXPIRE_AT,
            SessionManager.SESSION_NOT_CREATED
        )
        set(value) = prefs.edit().putLong(EXPIRE_AT, value).apply()



    fun removePrefs(){
        prefs.edit().clear().apply()
    }
}