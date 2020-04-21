package com.issell.benefits.login

import com.issell.benefits.BasePresenter
import com.issell.benefits.BaseView
import com.issell.progressbar.SimpleProgressBar
import com.nhn.android.naverlogin.OAuthLoginHandler


interface LoginContract {
    interface View : BaseView<LoginPresenter> {

        fun showNaverLoginButton(handler: OAuthLoginHandler)

        fun createProgressbar(): SimpleProgressBar

        fun showProgressbar(progressBar: SimpleProgressBar) {
            progressBar.on()
        }

        fun hideProgressbar(progressBar: SimpleProgressBar) {
            progressBar.off()
        }

        fun showConnectionErrorDialog()
        fun finish()

    }

    interface Presenter : BasePresenter {
        fun setNaverLoginModule()
        fun setKakaoLoginModule()
        fun checkNetwork()
        fun logoutKakao()
        fun logoutNaver()
    }
}