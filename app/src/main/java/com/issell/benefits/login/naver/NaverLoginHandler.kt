package com.issell.benefits.login.naver

import android.content.Context
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import com.issell.benefits.App
import com.issell.benefits.BuildConfig
import com.issell.benefits.login.LoginPresenter
import com.issell.benefits.session.SessionManager
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler


class NaverLoginHandler(
    private val mOAuthLoginModule: OAuthLogin,
    private val presenter: LoginPresenter
) : OAuthLoginHandler() {
    private val mContext: Context = presenter.context
    companion object {
        private const val TAG = "NaverLoginHandler"
        const val OAUTH_CLIENT_ID = BuildConfig.NAVER_CLIENT_ID
        const val OAUTH_CLIENT_SECRET = BuildConfig.NAVER_CLIENT_SECRET
        const val OAUTH_CLIENT_NAME = BuildConfig.APPLICATION_ID
    }

    override fun run(success: Boolean) {
        if (success) {
            SessionManager.Instance.accessToken = mOAuthLoginModule.getAccessToken(mContext)
            SessionManager.Instance.refreshToken = mOAuthLoginModule.getAccessToken(mContext)
            SessionManager.Instance.authType = SessionManager.AUTH_TYPE_NAVER
            SessionManager.Instance.beginAt = SystemClock.currentThreadTimeMillis()
            SessionManager.Instance.expireAt = mOAuthLoginModule.getExpiresAt(mContext) + App.prefs.beginAt
            presenter.onLoginSuccess()
        } else {
            val errorCode = mOAuthLoginModule.getLastErrorCode(mContext).code
            val errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext)
            Toast.makeText(
                mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
            ).show()
        }
    }
}
