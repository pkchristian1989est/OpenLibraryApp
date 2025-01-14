package com.jackson.openlibrary.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackson.openlibrary.OpenLibraryApp
import com.jackson.openlibrary.R
import com.willowtreeapps.common.BookListItemViewState
import com.willowtreeapps.common.ui.CompletedPresenter
import com.willowtreeapps.common.ui.CompletedView
import kotlinx.android.synthetic.main.fragment_completed.*
import kotlinx.android.synthetic.main.fragment_to_read.*
import kotlinx.android.synthetic.main.fragment_to_read.loading_spinner
import kotlinx.android.synthetic.main.fragment_to_read.txt_error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class CompletedFragment : BaseLibraryViewFragment<CompletedPresenter?>(), CoroutineScope, CompletedView {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override var presenter: CompletedPresenter? = null
    private val adapter = BooksAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_completed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        completedRecycler.adapter = adapter
        completedRecycler.layoutManager = LinearLayoutManager(context)

    }

    override fun onResume() {
        super.onResume()
        OpenLibraryApp.gameEngine().attachView(this)
        presenter?.loadBooks()
    }

    override fun onPause() {
        super.onPause()
        OpenLibraryApp.gameEngine().detachView(this)
    }

    override fun hideLoading() {
        loading_spinner.visibility = View.GONE
    }

    override fun showLoading() {
        loading_spinner.visibility = View.VISIBLE
    }

    override fun showError(msg: String) {
        txt_error.text = msg
    }

    override fun showBooks(books: List<BookListItemViewState>) {
        adapter.setBooks(books)
    }

}
