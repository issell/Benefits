package com.issell.benefits.session

import com.issell.benefits.App

object SessionManager {
    const val SESSION_NOT_CREATED = -1L
    const val AUTH_TYPE_UNDEFINED = -1
    const val AUTH_TYPE_OWN = 0
    const val AUTH_TYPE_NAVER = 1
    const val AUTH_TYPE_KAKAO = 2

    object Instance {
        var authType:Int = AUTH_TYPE_UNDEFINED
        var accessToken:String? = null
        var refreshToken:String? = null
        var beginAt:Long = SESSION_NOT_CREATED
        var expireAt:Long = SESSION_NOT_CREATED
    }
    fun saveCurrentSession(){
        App.prefs.removePrefs()
        App.prefs.autoLogin = true
        App.prefs.authType = Instance.authType
        App.prefs.accessToken = Instance.accessToken
        App.prefs.refreshToken = Instance.refreshToken
        App.prefs.beginAt = Instance.beginAt
        App.prefs.expireAt = Instance.expireAt
    }
    fun removeSession(){
        App.prefs.removePrefs()
    }
    
}