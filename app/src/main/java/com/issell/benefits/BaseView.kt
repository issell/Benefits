package com.issell.benefits


interface BaseView<T> {
    fun setPresenter(presenter: T)
}