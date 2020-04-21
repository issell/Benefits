package com.issell.benefits.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.issell.benefits.BuildConfig
import com.issell.benefits.login.kakao.App
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
            AuthService.requestAccessTokenInfo(object :ApiResponseCallback<AccessTokenInfoResponse>(){
                override fun onFailure(errorResult: ErrorResult) {
                    val result: Int = errorResult.errorCode
                    if (result == ErrorCode.CLIENT_ERROR_CODE.errorCode) {
                        Toast.makeText(context, "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT)
                            .show()
                        loginView.showConnectionErrorDialog()
                    } else {
                        Toast.makeText(
                            context,
                            "로그인 도중 오류가 발생했습니다: " + errorResult.errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    Toast.makeText(
                        context,
                        "세션이 닫혔습니다. 다시 시도해 주세요: " + if (errorResult == null) "" else errorResult!!.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onSuccess(result: AccessTokenInfoResponse?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    if(result == null){
                        Log.i("KAKAO_API", "result : null")
                    }
                    Log.i("KAKAO_API", "사용자 아이디: ${result!!.userId}" )
                    Log.i("KAKAO_API", "남은 시간 (ms): ${result!!.expiresInMillis}")
                }


                override fun onNotSignedUp() {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })

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
//            loginView.showConnectionErrorDialog()
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
}