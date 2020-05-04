package com.issell.benefits.join

import com.issell.benefits.BasePresenter
import com.issell.benefits.BaseView

interface SignUpContract {
    interface View : BaseView<Presenter> {

        // 가입 버튼 활성화
        fun activateCommitButton(on: Boolean)

    }

    interface Presenter : BasePresenter {
        // 이메일 유효성 검사
        fun checkEmail(s: String): Boolean

        // 비밀번호 유효성 검사
        fun checkPassword(s: String): Boolean

        // 두 비밀번호 일치 여부 검사
        fun checkEqualPasswords(s1: String, s2: String): Boolean

    }


}