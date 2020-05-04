package com.issell.benefits.main

import com.issell.benefits.BasePresenter
import com.issell.benefits.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {
        fun startBenefitsFragment()
        fun startSignupFragment()
        fun startLoginFragment()
    }

    interface Presenter : BasePresenter

}