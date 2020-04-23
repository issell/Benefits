package com.issell.benefits.login

import android.content.Context
import android.util.Log
import com.issell.benefits.BuildConfig
import com.issell.benefits.R
import com.issell.benefits.login.kakao.App
import com.issell.benefits.login.kakao.Callback
import com.issell.benefits.login.naver.MyOAuthLoginHandler
import com.kakao.auth.*
import com.kakao.auth.network.response.AccessTokenInfoResponse
import com.kakao.network.ErrorResult
import com.kakao.util.exception.KakaoException
import com.nhn.android.naverlogin.OAuthLogin

class LoginPresenter
constructor(
    private val context: Context,
    private val loginView: LoginContract.View
) :
    LoginContract.Presenter {

    companion object {
        const val TAG = "LoginPresenter"
        const val OAUTH_CLIENT_ID = BuildConfig.NAVER_CLIENT_ID
        const val OAUTH_CLIENT_SECRET = BuildConfig.NAVER_CLIENT_SECRET
    }

    // 애플리케이션 아이디
    private val OAUTH_CLIENT_NAME = context.getString(context.applicationInfo.labelRes)

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
            , OAUTH_CLIENT_ID
            , OAUTH_CLIENT_SECRET
            , OAUTH_CLIENT_NAME
        )
        val mOAuthLoginHandler =
            MyOAuthLoginHandler(mOAuthLoginModule, context)
        loginView.showNaverLoginButton(mOAuthLoginHandler)
    }

    //
    override fun checkNetwork() {
//        if (!NetworkStatus.isNetworkConnected(context)) {
//            loginView.showLoginErrorDialog()
//        }
    }

    override fun setKakaoLoginModule() {
        try {
            KakaoSDK.init(App.ConfigAdapter())
        } catch (e:KakaoSDK.AlreadyInitializedException){
            // You do not have to fill here
        }
        Session.getCurrentSession().addCallback(kakaoCallback)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    override fun logoutKakao() {
        //  Session.getCurrentSession().removeCallback(sessionCallback)
        // https://developers.kakao.com/docs/latest/ko/kakaologin/android
    }

    override fun logoutNaver() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun alertNetworkDisconnected() {
        loginView.showLoginErrorDialog(R.string.login_error_network_failed)
    }


    override fun alertFailedOnKakako(e:ErrorResult) {
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
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if(result == null){
            Log.i("KAKAO_API", "result : null")
        }
        Log.i("KAKAO_API", "사용자 아이디: ${result!!.userId}" )
        Log.i("KAKAO_API", "남은 시간 (ms): ${result!!.expiresInMillis}")
    }

    override fun successOnNaver() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun success() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}