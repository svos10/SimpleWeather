package com.gmail.segenpro.myweather.presentation.core

import android.os.Bundle
import android.support.annotation.LayoutRes
import butterknife.ButterKnife
import butterknife.Unbinder
import com.arellomobile.mvp.MvpAppCompatActivity
import com.gmail.segenpro.myweather.MyWeatherApp
import ru.terrakok.cicerone.Navigator

abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    abstract val navigator : Navigator
    private lateinit var unbinder: Unbinder

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        unbinder = ButterKnife.bind(this)
    }

    override fun onResume() {
        super.onResume()
        MyWeatherApp.instance.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        MyWeatherApp.instance.cicerone.navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()
    }
}