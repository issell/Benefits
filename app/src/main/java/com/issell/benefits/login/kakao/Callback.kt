package com.issell.benefits.login.kakao

import android.content.Context
import com.issell.benefits.login.LoginContract
import com.kakao.auth.ApiResponseCallback
import com.kakao.auth.ErrorCode
import com.kakao.auth.network.response.AccessTokenInfoResponse
import com.kakao.network.ErrorResult

class Callback(private val context: Context, private val presenter: LoginContract.Presenter) : ApiResponseCallback<AccessTokenInfoResponse>(){
    override fun onFailure(errorResult: ErrorResult) {
        val result: Int = errorResult.errorCode
        if (result == ErrorCode.CLIENT_ERROR_CODE.errorCode) {
            presenter.alertNetworkDisconnected()
        } else {
            presenter.alertFailedOnKakao(errorResult)
        }
    }

    override fun onSessionClosed(errorResult: ErrorResult?) {
        presenter.alertSessionClosed()
    }

    override fun onSuccess(result: AccessTokenInfoResponse?) {
        presenter.successOnKakao(result)
    }


    override fun onNotSignedUp() {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}