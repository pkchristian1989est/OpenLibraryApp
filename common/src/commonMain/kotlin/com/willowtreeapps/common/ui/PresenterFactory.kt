package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.repo.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.StoreSubscriber
import org.reduxkotlin.StoreSubscription
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

/**
 * PresenterFactory that creates presenters for all views in the application.
 * Each view must attach/detach itself as it becomes visible/not visible.
 * Attaching returns a presenter to the view.
 * PresenterFactory subscribes to changes in state, and passes state to presenters.
 */
class PresenterFactory(private val libraryApp: LibraryApp,
                       uiContext: CoroutineContext) : CoroutineScope {
    var subscription: StoreSubscription? = null


    private val subscribers = mutableMapOf<View, StoreSubscriber>()

    override val coroutineContext: CoroutineContext = uiContext + Job()

    fun <T : View> attachView(view: T) {
        Logger.d("AttachView: $view", Logger.Category.LIFECYCLE)
        view.dispatch = libraryApp.store.dispatch
        if (subscription == null) {
            subscription = libraryApp.store.subscribe(this::onStateChange)
        }
        val tmp = getPresenter(view)//(view)(libraryApp.appStore)
        val subscriber = tmp(view)(libraryApp.store)
        //call subscriber to trigger inital view update
        subscriber()
        subscribers[view] = subscriber
    }

    fun detachView(view: View) {
        Logger.d("DetachView: $view", Logger.Category.LIFECYCLE)
        subscribers.remove(view)

        if (hasAttachedViews()) {
            subscription?.invoke()
            subscription = null
        }
    }

    private fun hasAttachedViews() = subscribers.isNotEmpty()

    private fun onStateChange() {
        launch {
            subscribers.forEach { it.value() }
        }
    }
    private fun <V : View> getPresenter(view: V): ViewUpdater<V> {
        return presenterMap[view::class]!! as ViewUpdater<V>
    }
}

interface LibraryProvider {
    val networkThunks: NetworkThunks
}

interface View {
    var dispatch: Dispatcher
        get() = dispatch
        set(value) {
            dispatch = value
        }
}

//TODO handle config changes on android where view has been destoryed and must be recreated.  Probably
//can be treated as if a new state - wipe out the selector cache and treat as new view?
