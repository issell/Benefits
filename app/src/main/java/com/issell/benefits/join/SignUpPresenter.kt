package com.issell.benefits.join

import android.content.Context
import android.util.Log
import com.issell.benefits.BuildConfig
import com.issell.benefits.join.network.SignUp
import com.issell.benefits.join.network.SignUpInterface
import com.issell.benefits.util.ApiFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class SignUpPresenter
constructor(
    private val context: Context,
    private val signUpView: SignUpContract.View
) : SignUpContract.Presenter {
    override fun start() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkEmail(s: String): Boolean {
        return s.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()
    }

    override fun checkPassword(s: String): Boolean {
        return s.isNotEmpty() && s.length in 8..20
    }


    override fun checkEqualPasswords(s1: String, s2: String): Boolean {
        return s1.isNotEmpty() &&
                s2.isNotEmpty() &&
                s1.equals(s2, true)
    }


    override fun sendUserInfo(vo: SignUp) {
        val observable =
            Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ApiFactory.gsonConverter)
                .client(ApiFactory.client)
                .build()
                .create(SignUpInterface::class.java)!!
                .doSignUp(vo)
        val v = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                if (res.result) {
                    signUpView.showSignUpSuccessDialog(res.message)
                } else {
                    signUpView.showSignUpFailedDialog(res.message)
                }
            }, { error ->
                Log.e("ERROR", "ERROR on response. ${error.message}")
                signUpView.showSignUpFailedDialog(error.message?:"NULL")
            }
            )

    }
}