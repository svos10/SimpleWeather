package com.gmail.segenpro.simpleweather.presentation.core

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.arellomobile.mvp.MvpAppCompatFragment
import com.gmail.segenpro.simpleweather.SimpleWeatherApp
import com.gmail.segenpro.simpleweather.di.AppComponent
import com.squareup.leakcanary.LeakCanary

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    private lateinit var unbinder: Unbinder

    private val refWatcher = LeakCanary.installedRefWatcher()

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected abstract fun inject(component: AppComponent)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject(SimpleWeatherApp.instance.component)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(getLayoutResId(), container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        refWatcher.watch(this)
    }
}
