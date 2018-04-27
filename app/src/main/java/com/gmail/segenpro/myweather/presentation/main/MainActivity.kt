package com.gmail.segenpro.myweather.presentation.main

import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.presentation.core.BaseActivity
import com.gmail.segenpro.myweather.presentation.main.navigator.Navigator

class MainActivity : BaseActivity(), MainView {

    override val navigator by lazy {
        Navigator(this)
    }

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }
}
