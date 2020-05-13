package com.issell.benefits.join

import com.issell.benefits.BasePresenter
import com.issell.benefits.BaseView
import com.issell.benefits.join.network.SignUp

interface SignUpContract {
    interface View : BaseView<Presenter> {

        // 가입 버튼 활성화
        fun activateCommitButton(on: Boolean)

        // 회원가입 성공 메시지
        fun showSignUpSuccessDialog(message:String)

        // 회원가입 실패 메시지
        fun showSignUpFailedDialog(message:String)

        fun resetSignUpForm()
    }

    interface Presenter : BasePresenter {
        // 이메일 유효성 검사
        fun checkEmail(s: String): Boolean

        // 비밀번호 유효성 검사
        fun checkPassword(s: String): Boolean

        // 두 비밀번호 일치 여부 검사
        fun checkEqualPasswords(s1: String, s2: String): Boolean

        // 회원 정보 송신 + 비밀번호 암호화
        fun sendUserInfo(vo: SignUp)


    }


}