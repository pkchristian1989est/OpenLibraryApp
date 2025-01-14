package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.Actions
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.Store

internal class NavigationMiddleware(private val navigator: Navigator) {

    fun dispatch(store: Store) = { next: Dispatcher ->
        { action: Any ->
            when (action) {
                    is Actions.BookSelected -> navigator.goto(Screen.BOOK_DETAILS)
            }
            next(action)
        }
    }
}

enum class Screen {
    BOOK_DETAILS,
}

interface Navigator {
    fun goto(screen: Screen)
}
