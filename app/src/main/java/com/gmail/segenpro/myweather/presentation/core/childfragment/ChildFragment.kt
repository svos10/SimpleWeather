package com.gmail.segenpro.myweather.presentation.core.childfragment

import com.gmail.segenpro.myweather.presentation.core.BaseFragment

abstract class ChildFragment : BaseFragment() {

    override fun showError(throwable: Throwable) {
        (parentFragment as? BaseFragment)?.showError(throwable)
    }

    override fun showProgress(isShown: Boolean) {
        (parentFragment as? BaseFragment)?.showProgress(isShown)
    }
}