package com.issell.benefits.login

import androidx.annotation.Nullable
import androidx.annotation.StringRes
import com.issell.benefits.BasePresenter
import com.issell.benefits.BaseView
import com.issell.progressbar.SimpleProgressBar
import com.kakao.auth.network.response.AccessTokenInfoResponse
import com.kakao.network.ErrorResult
import com.nhn.android.naverlogin.OAuthLoginHandler
import org.jetbrains.annotations.TestOnly


interface LoginContract {
    interface View : BaseView<Presenter> {

        fun showNaverLoginButton(handler: OAuthLoginHandler)

        fun createProgressbar(): SimpleProgressBar

        fun showProgressbar(progressBar: SimpleProgressBar) {
            progressBar.on()
        }

        fun hideProgressbar(progressBar: SimpleProgressBar) {
            progressBar.off()
        }

        fun showLoginErrorDialog(@StringRes id:Int)
        fun isAutoLoginChecked():Boolean
        fun startMainActivity()
        @TestOnly
        fun showLoginErrorToast(@StringRes id:Int)
        fun finish()
    }

    interface Presenter : BasePresenter {

        fun checkNetwork()
        fun setNaverLoginModule()
        fun setKakaoLoginModule()
        fun successOnKakao(@Nullable result: AccessTokenInfoResponse?)
        fun onLoginSuccess()
        fun alertNetworkDisconnected()
        fun alertWrongPassword()
        fun alertSessionClosed()
        fun alertFailedOnKakao(e:ErrorResult)
        fun logout()
    }

}