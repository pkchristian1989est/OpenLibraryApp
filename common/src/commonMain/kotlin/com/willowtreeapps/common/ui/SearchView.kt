package com.willowtreeapps.common.ui

import com.willowtreeapps.common.BookListItemViewState

interface SearchView : View {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showResults(books: List<BookListItemViewState>)
}
