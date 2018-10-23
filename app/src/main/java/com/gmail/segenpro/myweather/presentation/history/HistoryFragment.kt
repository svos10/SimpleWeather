package com.gmail.segenpro.myweather.presentation.history

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.domain.core.models.HistoryDay
import com.gmail.segenpro.myweather.presentation.core.basecontentfragment.BaseContentFragment

class HistoryFragment : BaseContentFragment(), HistoryView {

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    @InjectPresenter
    lateinit var presenter: HistoryPresenter

    private val historyAdapter by lazy { HistoryAdapter(MyWeatherApp.instance) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(MyWeatherApp.instance)
        recyclerView.adapter = historyAdapter
    }

    override fun getLayoutResId() = R.layout.fragment_history

    @OnClick(R.id.try_again)
    fun onTryAgainClick() = presenter.onTryAgainClicked()

    override fun updateState(historyDays: List<HistoryDay>) {
        historyAdapter.historyDays = historyDays
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }
}
