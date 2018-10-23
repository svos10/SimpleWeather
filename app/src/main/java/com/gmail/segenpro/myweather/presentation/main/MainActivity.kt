package com.gmail.segenpro.myweather.presentation.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gmail.segenpro.myweather.R
import com.gmail.segenpro.myweather.presentation.core.navigator.Navigator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), Navigator.OnExitListener, MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
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
        if (intent?.action == Intent.ACTION_VIEW && intent.extras?.containsKey(SearchManager.EXTRA_DATA_KEY) == true) {
            presenter.setLocation(intent.extras.getSerializable(SearchManager.EXTRA_DATA_KEY) as String)
            searchViewClearFocus()
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
}
