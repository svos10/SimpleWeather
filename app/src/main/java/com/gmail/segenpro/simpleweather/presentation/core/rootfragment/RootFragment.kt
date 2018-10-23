package com.gmail.segenpro.simpleweather.presentation.core.rootfragment

import android.content.Context
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.simpleweather.SimpleWeatherApp
import com.gmail.segenpro.simpleweather.R
import com.gmail.segenpro.simpleweather.domain.AppSection
import com.gmail.segenpro.simpleweather.presentation.core.BaseFragment
import com.gmail.segenpro.simpleweather.presentation.core.navigator.Navigator
import com.gmail.segenpro.simpleweather.presentation.core.widgets.TabBarButton

class RootFragment : BaseFragment(), RootView {

    @BindView(R.id.location)
    lateinit var locationView: TextView

    @BindView(R.id.forecast)
    lateinit var forecastButton: TabBarButton

    @BindView(R.id.history)
    lateinit var chartsButton: TabBarButton

    private val tabBarButtons by lazy {
        arrayListOf(forecastButton, chartsButton)
    }

    private val navigator by lazy {
        Navigator(childFragmentManager, R.id.content_container)
    }

    @InjectPresenter
    lateinit var presenter: RootPresenter

    override fun getLayoutResId() = R.layout.fragment_root

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        navigator.onExitListener = context as? Navigator.OnExitListener
    }

    override fun onResume() {
        super.onResume()
        SimpleWeatherApp.instance.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        SimpleWeatherApp.instance.cicerone.navigatorHolder.removeNavigator()
    }

    @OnClick(R.id.forecast)
    fun onForecastClick() {
        presenter.openForecast()
    }

    @OnClick(R.id.history)
    fun onHistoryClick() {
        presenter.openHistory()
    }

    override fun showLocationName(name: String) = with(locationView) {
        if (name.isNotEmpty()) {
            text = name
            visibility = VISIBLE
        } else {
            visibility = GONE
        }
    }

    override fun selectAppSection(appSection: AppSection) =
            (0 until tabBarButtons.size).forEach {
                tabBarButtons[it].select(appSection.ordinal == it)
            }
}
