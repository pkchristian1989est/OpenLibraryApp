package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.boundary.toBookListViewState
import kotlin.reflect.KClass

//a viewupdater typed to our app's AppState for convenience
fun <View> presenter(actions: ViewUpdaterBuilder<AppState,View>): ViewUpdater<View> {
    return viewUpdater(actions)
}

internal val presenterMap = mutableMapOf<KClass<out View>, Any>()

fun <V: View> typedPresenter(viewClass: KClass<V>, actions: ViewUpdaterBuilder<AppState, V>): ViewUpdater<V> {
    val updater = viewUpdater(actions)
    presenterMap[viewClass] = updater
   return updater
}


val searchPresenter = typedPresenter(SearchView::class) {
    {
        this+{it} + { }
        plus{}() {  }

        plus{ it.isLoadingItems} + {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        withSingleField({ it.isLoadingItems }) {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        withSingleField({ it.errorLoadingItems }) {
            showError(state.errorMsg)
        }

        withSingleField({it.searchBooks}) {
            showResults(state.searchBooks.toBookListViewState())
        }
    }
}