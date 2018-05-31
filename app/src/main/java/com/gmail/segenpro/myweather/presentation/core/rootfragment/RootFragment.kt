package com.gmail.segenpro.myweather.presentation.core.rootfragment

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.data.network.ErrorType
import com.gmail.segenpro.myweather.data.network.WeatherException
import com.gmail.segenpro.myweather.domain.AppSection
import com.gmail.segenpro.myweather.presentation.core.BaseFragment
import com.gmail.segenpro.myweather.presentation.core.navigator.Navigator
import com.gmail.segenpro.myweather.presentation.core.widgets.TabBarButton

class RootFragment : BaseFragment(), RootView {

    @BindView(R.id.forecast)
    lateinit var forecastButton: TabBarButton

    @BindView(R.id.charts)
    lateinit var chartsButton: TabBarButton

    @BindView(R.id.error_text_view)
    lateinit var errorText: TextView

    @BindView(R.id.error_layout)
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

    override fun getLayoutResId() = R.layout.fragment_root

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

    override fun selectAppSection(appSection: AppSection) =
            (0 until tabBarButtons.size).forEach {
                tabBarButtons[it].select(appSection.ordinal == it)
            }

    @OnClick(R.id.forecast)
    fun onForecastClick() = presenter.openForecast()

    @OnClick(R.id.charts)
    fun onChartsClick() = presenter.openCharts()

    @OnClick(R.id.try_again)
    fun onTryAgainClick() = presenter.onTryAgainClicked()

    fun showError(weatherException: WeatherException) {
        @Suppress("NON_EXHAUSTIVE_WHEN")
        when (weatherException.errorType) {
            ErrorType.NETWORK_UNAVAILABLE -> {
                errorLayout.visibility = VISIBLE
                errorText.visibility = GONE
            }
            ErrorType.SERVER_ERROR, ErrorType.SERVER_DATA_ERROR -> {
                errorLayout.visibility = GONE
                errorText.visibility = VISIBLE
            }
        }
    }

    override fun hideError() {
        errorLayout.visibility = GONE
        errorText.visibility = GONE
    }

    override fun showProgress(isShown: Boolean) {
        progress.visibility = if (isShown) VISIBLE else GONE
    }
}
