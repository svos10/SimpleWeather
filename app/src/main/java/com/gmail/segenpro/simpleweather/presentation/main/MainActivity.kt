package com.gmail.segenpro.simpleweather.presentation.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gmail.segenpro.simpleweather.R
import com.gmail.segenpro.simpleweather.SimpleWeatherApp
import com.gmail.segenpro.simpleweather.presentation.core.navigator.Navigator
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : MvpAppCompatActivity(), Navigator.OnExitListener, MainView {

    private lateinit var searchView: SearchView

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenterProvider.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        SimpleWeatherApp.instance.component.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onExit() = finish()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar_actions, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            setOnQueryTextFocusChangeListener { _, hasFocus -> if (!hasFocus) searchViewClearFocus() }
        }
        return true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.action == Intent.ACTION_VIEW) {
            intent.extras?.let {
                if (it.containsKey(SearchManager.EXTRA_DATA_KEY)) {
                    presenter.setLocation(it.getSerializable(SearchManager.EXTRA_DATA_KEY) as String)
                    searchViewClearFocus()
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) searchViewClearFocus()
    }

    private fun searchViewClearFocus() = with(searchView) {
        clearFocus()
        setQuery("", false)
    }

    override fun onError() {
        Toast.makeText(this, getString(R.string.unexpected_error), Toast.LENGTH_LONG).show()
    }
}
