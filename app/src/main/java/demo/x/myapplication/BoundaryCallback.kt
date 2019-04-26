package demo.x.myapplication

import androidx.paging.PagedList

class BoundaryCallback<T> : PagedList.BoundaryCallback<T>() {

    private var onZeroItemsLoaded: (() -> Unit)? = null
    private var onItemAtEndLoaded: ((item: T) -> Unit)? = null
    private var onItemAtFrontLoaded: ((item: T) -> Unit)? = null

    fun onZeroItemsLoaded(onZeroItemsLoaded: () -> Unit): BoundaryCallback<T> {
        this.onZeroItemsLoaded = onZeroItemsLoaded
        return this
    }

    fun onItemAtEndLoaded(onItemAtEndLoaded: (item: T) -> Unit): BoundaryCallback<T> {
        this.onItemAtEndLoaded = onItemAtEndLoaded
        return this
    }

    fun onItemAtFrontLoaded(onItemAtFrontLoaded: (item: T) -> Unit): BoundaryCallback<T> {
        this.onItemAtFrontLoaded = onItemAtFrontLoaded
        return this
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        onZeroItemsLoaded?.invoke()
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        super.onItemAtEndLoaded(itemAtEnd)
        onItemAtEndLoaded?.invoke(itemAtEnd)
    }

    override fun onItemAtFrontLoaded(itemAtFront: T) {
        super.onItemAtFrontLoaded(itemAtFront)
        onItemAtFrontLoaded?.invoke(itemAtFront)
    }
}