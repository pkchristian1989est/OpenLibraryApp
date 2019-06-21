package com.jackson.openlibrary.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.willowtreeapps.common.Logger
import com.willowtreeapps.common.ui.View
import com.jackson.openlibrary.OpenLibraryApp
import com.willowtreeapps.common.ui.LibraryProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class BaseLibraryViewFragment: Fragment(), CoroutineScope, View, LibraryProvider by OpenLibraryApp.gameEngine() {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var viewRecreated: Boolean = false

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null)
            Logger.d("savedInstanceState == null")
        else {
            Logger.d("savedInstanceState != null")
            viewRecreated = true
        }
    }

    override fun onResume() {
        super.onResume()
        OpenLibraryApp.gameEngine().attachView(this)
        if (viewRecreated) {
            //TODO update view with all state
//            presenter?.recreateView()
        }
    }

    override fun onPause() {
        super.onPause()
        OpenLibraryApp.gameEngine().detachView(this)
    }
}
