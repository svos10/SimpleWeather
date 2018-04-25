package com.gmail.segenpro.myweather.presentation.core

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.arellomobile.mvp.MvpAppCompatFragment

/**
 * Created by home on 21.04.18.
 *
 */
abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    private lateinit var unbinder: Unbinder

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(getLayoutResId(), container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }
}