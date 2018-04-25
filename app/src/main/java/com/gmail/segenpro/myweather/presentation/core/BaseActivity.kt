package com.gmail.segenpro.myweather.presentation.core

import android.os.Bundle
import android.support.annotation.LayoutRes
import butterknife.ButterKnife
import butterknife.Unbinder
import com.arellomobile.mvp.MvpAppCompatActivity

/**
 * Created by home on 21.04.18.
 *
 */
abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    private lateinit var unbinder: Unbinder

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        unbinder = ButterKnife.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()
    }
}