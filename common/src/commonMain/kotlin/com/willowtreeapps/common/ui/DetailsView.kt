package com.willowtreeapps.common.ui

import com.willowtreeapps.common.BookListItemViewState


interface DetailsView : View {
    fun render(book: BookListItemViewState)
}
