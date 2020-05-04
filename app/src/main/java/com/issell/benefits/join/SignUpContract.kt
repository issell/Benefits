package com.issell.benefits.join

import com.issell.benefits.BasePresenter
import com.issell.benefits.BaseView

interface SignUpContract {
    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter

}