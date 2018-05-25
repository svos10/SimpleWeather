package com.gmail.segenpro.myweather.presentation.core.rootfragment

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.presentation.core.BaseFragment
import com.gmail.segenpro.myweather.presentation.core.navigator.Navigator
import com.gmail.segenpro.myweather.presentation.core.widgets.TabBarButton

class RootFragment : BaseFragment(), RootView {

    @BindView(R.id.forecast)
    lateinit var forecastButton: TabBarButton

    @BindView(R.id.charts)
    lateinit var chartsButton: TabBarButton

    @BindView(R.id.layout_error)
    lateinit var errorLayout: View

    @BindView(R.id.layout_progress)
    lateinit var progress: View

    private val tabBarButtons by lazy {
        arrayListOf(forecastButton, chartsButton)
    }

    private val navigator by lazy {
        Navigator(childFragmentManager, R.id.content_container)
    }

    @InjectPresenter
    lateinit var presenter: RootPresenter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        navigator.onExitListener = context as? Navigator.OnExitListener
    }

    override fun onResume() {
        super.onResume()
        MyWeatherApp.instance.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        MyWeatherApp.instance.cicerone.navigatorHolder.removeNavigator()
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

    override fun showError(throwable: Throwable) {
        errorLayout.visibility = VISIBLE
        //todo настроить отображение для разных ошибок: отсутствие подключения, сетевые ошибки, другие ошибки
    }

    override fun showProgress(isShown: Boolean) {
        progress.visibility = if (isShown) VISIBLE else GONE
    }
}