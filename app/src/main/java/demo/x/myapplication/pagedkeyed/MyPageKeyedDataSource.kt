package demo.x.myapplication.pagedkeyed

import androidx.paging.PageKeyedDataSource
import demo.x.myapplication.Item

class MyPageKeyedDataSource : PageKeyedDataSource<Int, Item>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {
        val initKey = 0
        val requestedLoadSize = params.requestedLoadSize
        val callbackData = getInitData(requestedLoadSize, initKey)
        callback.onResult(callbackData, getPreviousPageKey(initKey), getNextPageKey(initKey))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        val pageSize = params.requestedLoadSize
        val nextKey = params.key
        val callbackData = getNextData(pageSize, nextKey)
        callback.onResult(callbackData, getNextPageKey(params.key))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        val pageSize = params.requestedLoadSize
        val previousKey = params.key
        val callbackData = getPreviousData(pageSize, previousKey)
        callback.onResult(callbackData, getPreviousPageKey(params.key))
    }

    private fun getInitData(pageSize: Int, key: Int): List<Item> {
        return dataList.subList(key, key + pageSize)
    }

    private fun getPreviousData(pageSize: Int, key: Int): List<Item> {
        return dataList.subList(key, key + pageSize)
    }

    private fun getNextData(pageSize: Int, key: Int): List<Item> {
        return dataList.subList(key, key + pageSize)
    }


    private fun getPreviousPageKey(currentKey: Int): Int? {
        return if (currentKey - 1 <= 0) {
            null
        } else {
            currentKey - 1
        }
    }

    private fun getNextPageKey(currentKey: Int): Int? {
        return if (currentKey + 1 >= dataList.size) {
            null
        } else {
            currentKey + 1
        }
    }

    private val dataList = arrayListOf<Item>()

    init {
        for (i in 43 downTo 0) {
            dataList.add(
                Item(i)
            )
        }
    }
}