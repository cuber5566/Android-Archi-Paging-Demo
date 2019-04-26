package demo.x.myapplication

import androidx.paging.PagedList

class PagedListCallback : PagedList.Callback() {

    private var onChanged: ((position: Int, count: Int) -> Unit)? = null
    private var onInserted: ((position: Int, count: Int) -> Unit)? = null
    private var onRemoved: ((position: Int, count: Int) -> Unit)? = null

    fun onChanged(onChanged: ((position: Int, count: Int) -> Unit)?): PagedListCallback {
        this.onChanged = onChanged
        return this
    }

    fun onInserted(onInserted: ((position: Int, count: Int) -> Unit)?): PagedListCallback {
        this.onInserted = onInserted
        return this
    }

    fun onRemoved(onRemoved: ((position: Int, count: Int) -> Unit)?): PagedListCallback {
        this.onRemoved = onRemoved
        return this
    }

    override fun onChanged(position: Int, count: Int) {
        onChanged?.invoke(position, count)
    }

    override fun onInserted(position: Int, count: Int) {
        onInserted?.invoke(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        onRemoved?.invoke(position, count)
    }
}