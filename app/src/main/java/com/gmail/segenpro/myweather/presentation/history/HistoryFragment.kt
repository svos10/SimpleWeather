package com.gmail.segenpro.myweather.presentation.history

import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.presentation.core.childfragment.ChildFragment

class HistoryFragment : ChildFragment(), HistoryView {

    @InjectPresenter
    lateinit var presenter: HistoryPresenter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_history
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }
}