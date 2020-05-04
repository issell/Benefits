package com.issell.benefits.splash

import android.content.Context
import android.os.SystemClock
import com.issell.benefits.App
import com.issell.benefits.login.naver.NaverAuthHandler
import com.issell.benefits.session.SessionManager

class SplashPresenter
constructor(
    private val context: Context,
    private val splashView: SplashContract.View
) : SplashContract.Presenter {
    companion object {
        const val TAG = "SplashPresenter"
    }

    override fun start() {
        // 자동 로그인 설정 on
        if (checkAutoLogin()) {
            checkSessionPrefs()
        }

        // 자동 로그인 설정 off 시
        // MILLI_DELAYED 밀리초 후 로그인 액티비티 실행
        else {
            android.os.Handler().postDelayed(
                {
                    splashView.startActivityWithLoginFragment()
                }, MILLI_DELAYED
            )
        }
    }

    // 자동 로그인 여부 확인
    override fun checkAutoLogin(): Boolean {
        return App.prefs.autoLogin
    }

    //쿠키 확인 (인증타입, GUID, AccessToken)
    override fun checkSessionPrefs() {
        when (App.prefs.authType) {
            SessionManager.AUTH_TYPE_UNDEFINED -> alertAuthError()
            SessionManager.AUTH_TYPE_OWN -> sendAuthToOwn()
            SessionManager.AUTH_TYPE_NAVER -> sendAuthToNaver()
            SessionManager.AUTH_TYPE_KAKAO -> sendAuthToKakao()
            else -> alertAuthError()
        }
    }

    // 기존 세션 정보를 우리 서버에게 전송
    override fun sendAuthToOwn() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // 기존 세션 정보를 카카오 서버에게 전송
    override fun sendAuthToKakao() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // 기존 세션 정보를 네이버 서버에게 전송
    override fun sendAuthToNaver() {
        // 기존 accessToken 유효 여부 검사 ( 토큰 만료라면 재인증 요청 )
        if (SystemClock.currentThreadTimeMillis() >= App.prefs.expireAt) {
            NaverAuthHandler.sendRequestToRefresh(this)
        }
        else {
            splashView.startActivityWithMainFragment()
        }
    }

    fun onFinishRefresh() {
        if (App.prefs.autoLogin) {
            SessionManager.saveCurrentSession()
            splashView.startActivityWithMainFragment()
        }
    }

    // 세션 정보 조회 실패 (or 인증 실패)
    override fun alertAuthError() {
        splashView.showAutoLoginErrorDialog()
    }
}