package com.gmail.segenpro.myweather.presentation.location

import android.app.SearchManager.*
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.BaseColumns._ID
import com.gmail.segenpro.myweather.MyWeatherApp
import com.gmail.segenpro.myweather.data.network.Result
import com.gmail.segenpro.myweather.domain.location.LocationInteractor
import com.google.gson.Gson
import javax.inject.Inject

class SuggestionsProvider : ContentProvider() {

    @Inject
    lateinit var locationInteractor: LocationInteractor

    @Inject
    lateinit var gson: Gson

    override fun onCreate(): Boolean {
        MyWeatherApp.instance.component.inject(this)
        return true
    }

    override fun getType(uri: Uri): String = "vnd.android.cursor.dir/com.gmail.segenpro.myweather.suggestions"

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val locationsCursor = MatrixCursor(arrayOf(_ID, SUGGEST_COLUMN_TEXT_1, SUGGEST_COLUMN_TEXT_2, SUGGEST_COLUMN_INTENT_EXTRA_DATA))
        if (selectionArgs == null || selectionArgs.isEmpty() || selectionArgs[0].isBlank()) return locationsCursor

        //запрос query производится системой в рабочем потоке, поэтому можно использовать Rx blockingGet()
        val locationsResult = locationInteractor.searchLocationsAtServer(selectionArgs[0]).blockingGet()
        if (locationsResult is Result.Error) return locationsCursor

        val locations = (locationsResult as Result.Success).data
        locations.forEach {
            locationsCursor.addRow(listOf(it.id, it.name, it.country + ", " + it.region, gson.toJson(it)))
        }
        return locationsCursor
    }

    override fun insert(uri: Uri?, values: ContentValues?) = null

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?) = 0

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?) = 0
}
