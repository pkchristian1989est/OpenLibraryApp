package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.SelectorSubscriberFn
import com.willowtreeapps.common.boundary.toBookListViewState

val completedPresenter = typedPresenter(CompletedView::class) {{
    withSingleField({it.completed}) { showBooks(state.completed.toList().toBookListViewState())}
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
}}

class CompletedPresenter(private val engine: LibraryApp) : Presenter<CompletedView>() {
    override fun recreateView() {
        //no-op
    }

    override fun makeSubscriber() = SelectorSubscriberFn<AppState>(engine.store) {
        withSingleField({it.completed}) { view?.showBooks(state.completed.toList().toBookListViewState())}
        withSingleField({ it.isLoadingItems }) {
            if (state.isLoadingItems) {
                view?.showLoading()
            } else {
                view?.hideLoading()
            }
        }

        withSingleField({ it.errorLoadingItems }) {
            view?.showError(state.errorMsg)
        }
    }


    fun loadBooks() {
        engine.dispatch(Actions.LoadCompleted())
    }
}