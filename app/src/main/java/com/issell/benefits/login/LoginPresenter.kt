package com.issell.benefits.login

import android.content.Context
import com.issell.benefits.BuildConfig
import com.issell.benefits.login.naver.MyOAuthLoginHandler
import com.issell.benefits.util.NetworkStatus
import com.nhn.android.naverlogin.OAuthLogin

class LoginPresenter
constructor(private val context: Context,
            private val loginView: LoginContract.View) :
    LoginContract.Presenter {

    // 애플리케이션 아이디
    private val OAUTH_CLIENT_NAME = context.getString(context.applicationInfo.labelRes)

    companion object {
        const val OAUTH_CLIENT_ID = BuildConfig.CLIENT_ID
        const val OAUTH_CLIENT_SECRET = BuildConfig.CLIENT_SECRET
    }

    override fun start() {
        setNaverLoginModule()
    }

    override fun setNaverLoginModule() {
        val mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            context
            , OAUTH_CLIENT_ID
            , OAUTH_CLIENT_SECRET
            , OAUTH_CLIENT_NAME
        )
        val mOAuthLoginHandler =
            MyOAuthLoginHandler(mOAuthLoginModule, context)
        loginView.showNaverLoginButton(mOAuthLoginHandler)
    }

    override fun checkNetwork() {
        if (!NetworkStatus.isNetworkConnected(context)) {
            loginView.showConnectionErrorDialog()
        }
    }
}