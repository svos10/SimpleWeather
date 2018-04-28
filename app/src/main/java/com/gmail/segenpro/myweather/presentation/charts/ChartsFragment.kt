package com.gmail.segenpro.myweather.presentation.charts

import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.presentation.core.BaseFragment

class ChartsFragment : BaseFragment(), ChartsView {

    @InjectPresenter
    lateinit var presenter: ChartsPresenter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_charts
    }

    companion object {

        fun newInstance() = ChartsFragment()
    }
}