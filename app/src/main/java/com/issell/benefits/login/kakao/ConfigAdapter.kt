package com.issell.benefits.login.kakao

import android.app.Application
import com.kakao.auth.*
import java.lang.IllegalArgumentException

class App : Application() {

    companion object {
        private var instance: App? = null
        fun getInstance(): App {
            if (instance == null)
                throw IllegalArgumentException("Don't use getInstance() until onCreate() is finished. ")
            return instance!!
        }
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
                getInstance()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }
}