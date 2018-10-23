package com.gmail.segenpro.myweather.presentation.core.basecontentfragment

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import butterknife.BindView
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.data.ErrorType
import com.gmail.segenpro.myweather.data.WeatherException
import com.gmail.segenpro.myweather.presentation.core.BaseFragment

abstract class BaseContentFragment : BaseFragment(), BaseContentView {

    @BindView(R.id.progress_layout)
    lateinit var progressLayout: View

    @BindView(R.id.errors_layout)
    lateinit var errorsLayout: View

    @BindView(R.id.connection_error_layout)
    lateinit var connectionErrorLayout: View

    @BindView(R.id.server_error_text_view)
    lateinit var serverErrorText: View

    @BindView(R.id.location_error_text_view)
    lateinit var locationErrorText: View

    override fun showProgress(isShown: Boolean) {
        progressLayout.visibility = if (isShown) VISIBLE else GONE
    }

    override fun showError(weatherException: WeatherException) {
        errorsLayout.visibility = VISIBLE
        @Suppress("NON_EXHAUSTIVE_WHEN")
        when (weatherException.errorType) {
            ErrorType.NETWORK_UNAVAILABLE -> {
                connectionErrorLayout.visibility = VISIBLE
                serverErrorText.visibility = GONE
                locationErrorText.visibility = GONE
            }
            ErrorType.SERVER_ERROR, ErrorType.SERVER_DATA_ERROR -> {
                connectionErrorLayout.visibility = GONE
                serverErrorText.visibility = VISIBLE
                locationErrorText.visibility = GONE
            }
            ErrorType.LOCATION_NOT_SELECTED -> {
                connectionErrorLayout.visibility = GONE
                serverErrorText.visibility = GONE
                locationErrorText.visibility = VISIBLE
            }
        }
    }

    override fun hideError() {
        errorsLayout.visibility = GONE
        connectionErrorLayout.visibility = GONE
        serverErrorText.visibility = GONE
        locationErrorText.visibility = GONE
    }
}
