package com.gmail.segenpro.myweather.presentation.main

import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.presentation.core.BaseActivity
import com.gmail.segenpro.myweather.presentation.core.widgets.TabBarButton
import com.gmail.segenpro.myweather.presentation.main.navigator.Navigator

class MainActivity : BaseActivity(), MainView {

    @BindView(R.id.forecast)
    lateinit var forecastButton: TabBarButton

    @BindView(R.id.charts)
    lateinit var chartsButton: TabBarButton

    private val tabBarButtons by lazy {
        arrayListOf(forecastButton, chartsButton)
    }

    override val navigator by lazy {
        Navigator(this)
    }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun selectAppSection(appSection: AppSection) {
        for (i in 0 until tabBarButtons.size) {
            tabBarButtons[i].select(appSection.ordinal == i)
        }
    }

    @OnClick(R.id.forecast)
    fun onForecastClick() {
        presenter.openForecast()
    }

    @OnClick(R.id.charts)
    fun onChartsClick() {
        presenter.openCharts()
    }
}
