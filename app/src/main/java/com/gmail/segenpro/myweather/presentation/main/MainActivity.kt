package com.gmail.segenpro.myweather.presentation.main

import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.presentation.core.BaseActivity

class MainActivity : BaseActivity(), MainView {

   /* @Inject
    lateinit var sharedPreferences: SharedPreferences*/

    override fun getLayoutResId(): Int {
        //MyWeatherApp.instance.component.inject(this)
        return R.layout.activity_main
    }
}
