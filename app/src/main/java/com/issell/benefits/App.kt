package com.issell.benefits

import android.app.Application
import com.issell.benefits.util.AppSharedPreferences
import com.kakao.auth.*

class App : Application() {
    init {
        INSTANCE = this
    }
    companion object {
        lateinit var INSTANCE: App
        lateinit var prefs: AppSharedPreferences
    }

    class ConfigAdapter : KakaoAdapter() {

        override fun getSessionConfig(): ISessionConfig {
            return object : ISessionConfig {
                override fun getAuthTypes(): Array<AuthType> =
                    listOf(AuthType.KAKAO_ACCOUNT).toTypedArray()
                override fun getApprovalType(): ApprovalType = ApprovalType.INDIVIDUAL
                override fun isSaveFormData(): Boolean = true
                override fun isSecureMode(): Boolean = true
                override fun isUsingWebviewTimer(): Boolean = true
            }
        }
        override fun getApplicationConfig(): IApplicationConfig {
            return IApplicationConfig {
                INSTANCE
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        prefs = AppSharedPreferences(applicationContext)
        INSTANCE = this
    }

}