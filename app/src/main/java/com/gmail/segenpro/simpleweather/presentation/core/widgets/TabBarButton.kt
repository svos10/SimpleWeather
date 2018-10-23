package com.gmail.segenpro.simpleweather.presentation.core.widgets

import android.content.Context
import android.util.AttributeSet
import butterknife.BindColor
import butterknife.ButterKnife
import com.github.pwittchen.weathericonview.WeatherIconView
import com.gmail.segenpro.simpleweather.R

class TabBarButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0) :
        WeatherIconView(context, attrs, defStyleAttr) {

    @BindColor(R.color.active_tab_bar_item)
    @JvmField
    var activeTabBarItemColor = 0

    @BindColor(R.color.inactive_tab_bar_item)
    @JvmField
    var inactiveTabBarItemColor = 0

    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)
    }

    fun select(isSelected: Boolean) =
            if (isSelected) setIconColor(activeTabBarItemColor) else setIconColor(inactiveTabBarItemColor)
}