package demo.x.myapplication.itemkeyed

import androidx.paging.ItemKeyedDataSource
import demo.x.myapplication.Item

class MyItemKeyedDataSource : ItemKeyedDataSource<Int, Item>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Item>) {
        val requestedLoadSize = params.requestedLoadSize
        val requestedInitialKey = params.requestedInitialKey
        val callbackData = getInitData(requestedInitialKey, requestedLoadSize)
        callback.onResult(callbackData)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Item>) {
        val requestedLoadSize = params.requestedLoadSize
        val key = params.key
        val callbackData = getNextData(key, requestedLoadSize)
        callback.onResult(callbackData)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Item>) {
        val requestedLoadSize = params.requestedLoadSize
        val key = params.key
        val callbackData = getPreviousData(key, requestedLoadSize)
        callback.onResult(callbackData)
    }

    override fun getKey(item: Item): Int {
        return item.id
    }

    private val dataList = arrayListOf<Item>()

    init {
        for (i in 30 downTo 0) {
            dataList.add(
                Item(i)
            )
        }
    }

    private fun getInitData(key: Int?, size: Int): List<Item> {
        return getNextData(key, size)
    }

    private fun getNextData(key: Int?, size: Int): List<Item> {
        if (key == null) return emptyList()
        return dataList
            .filter { it.id < key }
            .take(size)
    }

    private fun getPreviousData(key: Int?, size: Int): List<Item> {
        if (key == null) return emptyList()
        return dataList
            .filter { it.id > key }
            .take(size)
    }
}