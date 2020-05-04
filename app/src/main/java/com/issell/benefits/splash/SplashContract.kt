package com.issell.benefits.splash

import com.issell.benefits.BasePresenter
import com.issell.benefits.BaseView

interface SplashContract{
    interface View:BaseView<Presenter>{
        fun startActivityWithLoginFragment()
        fun startActivityWithMainFragment()
        fun showAutoLoginErrorDialog()
    }

    interface Presenter:BasePresenter{
        fun checkAutoLogin():Boolean
        fun checkSessionPrefs()
        fun sendAuthToOwn()
        fun sendAuthToKakao()
        fun sendAuthToNaver()
        fun alertAuthError()
    }
}