package com.issell.benefits.login

import android.content.Context
import android.util.Log
import com.issell.benefits.App
import com.issell.benefits.R
import com.issell.benefits.login.kakao.Callback
import com.issell.benefits.login.naver.NaverLoginHandler
import com.issell.benefits.session.SessionManager
import com.kakao.auth.*
import com.kakao.auth.network.response.AccessTokenInfoResponse
import com.kakao.network.ErrorResult
import com.kakao.util.exception.KakaoException
import com.nhn.android.naverlogin.OAuthLogin

class LoginPresenter
constructor(
    val context: Context,
    private val loginView: LoginContract.View
) : LoginContract.Presenter {

    companion object {
        const val TAG = "LoginPresenter"
    }

    private val kakaoCallback = object : ISessionCallback {
        override fun onSessionOpenFailed(exception: KakaoException?) {
            // 세션 실패
        }

        override fun onSessionOpened() {
            // 세션 생성
            AuthService.requestAccessTokenInfo(Callback(context, this@LoginPresenter))

        }
    }

    // Login 앱 화면 처음 실행할 때
    override fun start() {
        setNaverLoginModule()
        setKakaoLoginModule()
    }

    // 네이버 로그인 핸들러 초기화
    override fun setNaverLoginModule() {
        val mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            context
            , NaverLoginHandler.OAUTH_CLIENT_ID
            , NaverLoginHandler.OAUTH_CLIENT_SECRET
            , NaverLoginHandler.OAUTH_CLIENT_NAME
        )
        val mOAuthLoginHandler =
            NaverLoginHandler(mOAuthLoginModule, this)
        loginView.showNaverLoginButton(mOAuthLoginHandler)
    }

    //
    override fun checkNetwork() {
//        if (!NetworkStatus.isNetworkConnected(context)) {
//            loginView.errorDialog()
//        }
    }

    override fun setKakaoLoginModule() {
        try {
            KakaoSDK.init(App.ConfigAdapter())
        } catch (e: KakaoSDK.AlreadyInitializedException) {
            // You do not have to fill here
        }
        Session.getCurrentSession().addCallback(kakaoCallback)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    override fun alertNetworkDisconnected() {
        loginView.showLoginErrorDialog(R.string.login_error_network_failed)
    }


    override fun alertFailedOnKakao(e: ErrorResult) {
        Log.e(TAG, e.errorMessage)
        loginView.showLoginErrorDialog(R.string.login_error_on_process)
    }

    override fun alertWrongPassword() {
        loginView.showLoginErrorDialog(R.string.login_error_wrong_password)
    }

    override fun alertSessionClosed() {
        loginView.showLoginErrorDialog(R.string.login_error_session_closed)
    }

    override fun successOnKakao(result: AccessTokenInfoResponse?) {
        if (result == null) {
            Log.i("KAKAO_API", "The result is null.")
            return
        }
        Log.i("KAKAO_API", "사용자 아이디: ${result.userId}")
        Log.i("KAKAO_API", "남은 시간 (ms): ${result.expiresInMillis}")
    }


    override fun onLoginSuccess() {
        if(loginView.isAutoLoginChecked()){
            SessionManager.saveCurrentSession()
        }
        loginView.startMainActivity()
    }


    override fun logout() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}