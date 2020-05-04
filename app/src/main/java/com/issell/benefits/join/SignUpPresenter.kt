package com.issell.benefits.join


class SignUpPresenter
constructor(
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


}