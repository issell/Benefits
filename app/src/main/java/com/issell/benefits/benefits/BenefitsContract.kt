package com.issell.benefits.benefits

import com.issell.benefits.BasePresenter
import com.issell.benefits.BaseView

interface BenefitsContract {
    interface View : BaseView<Presenter> {
        fun startLoginActivity()
        fun showLogoutSuccessDialog()
    }

    interface Presenter : BasePresenter {
        fun logout()
        fun completeDeletion()
    }
}