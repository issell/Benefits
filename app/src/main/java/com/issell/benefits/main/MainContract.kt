package com.issell.benefits.main

import com.issell.benefits.BasePresenter
import com.issell.benefits.BaseView

interface MainContract {
    interface View:BaseView<Presenter>{
        fun startLoginActivity()
        fun showLogoutSuccessDialog()
    }
    interface Presenter:BasePresenter{
        fun logout()
        fun completeDeletion()
    }
}