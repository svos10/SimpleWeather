package com.gmail.segenpro.simpleweather.presentation.history

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gmail.segenpro.simpleweather.R
import com.gmail.segenpro.simpleweather.SimpleWeatherApp
import com.gmail.segenpro.simpleweather.di.AppComponent
import com.gmail.segenpro.simpleweather.domain.core.models.HistoryDay
import com.gmail.segenpro.simpleweather.presentation.core.basecontentfragment.BaseContentFragment
import javax.inject.Inject
import javax.inject.Provider

class HistoryFragment : BaseContentFragment(), HistoryView {

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var presenterProvider: Provider<HistoryPresenter>

    @InjectPresenter
    lateinit var presenter: HistoryPresenter

    private val historyAdapter by lazy { HistoryAdapter(SimpleWeatherApp.instance) }

    @ProvidePresenter
    fun providePresenter(): HistoryPresenter = presenterProvider.get()

    override fun getLayoutResId() = R.layout.fragment_history

    override fun inject(component: AppComponent) {
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(SimpleWeatherApp.instance)
        recyclerView.adapter = historyAdapter
    }

    @OnClick(R.id.try_again)
    fun onTryAgainClick() = presenter.onTryAgainClicked()

    override fun updateState(historyDays: List<HistoryDay>) {
        historyAdapter.historyDays = historyDays
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }
}
