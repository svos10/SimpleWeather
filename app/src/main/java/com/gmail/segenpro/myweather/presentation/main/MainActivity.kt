package com.gmail.segenpro.myweather.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.presentation.core.navigator.Navigator

class MainActivity : AppCompatActivity(), Navigator.OnExitListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onExit() = finish()
}
