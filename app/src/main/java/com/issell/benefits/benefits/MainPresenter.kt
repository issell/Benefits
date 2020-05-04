package com.issell.benefits.benefits

import android.content.Context
import com.issell.benefits.App
import com.issell.benefits.login.naver.NaverAuthHandler
import com.issell.benefits.session.SessionManager

class MainPresenter
constructor(
    private val context: Context,
    private val benefitsView: BenefitsContract.View
) : BenefitsContract.Presenter {
    companion object {
        const val TAG = "MainPresenter"
    }

    override fun logout() {
        when (App.prefs.authType) {
            SessionManager.AUTH_TYPE_OWN -> logoutOwn()
            SessionManager.AUTH_TYPE_NAVER -> logoutNaver()
            SessionManager.AUTH_TYPE_KAKAO -> logoutKakao()
            else -> return
        }
    }

    override fun completeDeletion() {
        App.prefs.removePrefs()
        benefitsView.showLogoutSuccessDialog()
    }


    override fun start() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun logoutNaver() {
        NaverAuthHandler.sendRequestToDelete(this)
    }

    private fun logoutKakao() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun logoutOwn() {
        //        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}