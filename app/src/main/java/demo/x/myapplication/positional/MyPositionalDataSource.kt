package demo.x.myapplication.positional

import androidx.paging.PositionalDataSource
import demo.x.myapplication.Item

class MyPositionalDataSource : PositionalDataSource<Item>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Item>) {
        val pageSize = params.pageSize
        val requestedStartPosition = params.requestedStartPosition
        val requestedLoadSize = params.requestedLoadSize // => PageSize *3
        val placeholdersEnabled = params.placeholdersEnabled
        val callbackData = getInitData(pageSize, requestedStartPosition, requestedLoadSize)

        if (placeholdersEnabled) {
            callback.onResult(callbackData, requestedStartPosition, getTotalDataSize())
        } else {
            callback.onResult(callbackData, requestedStartPosition)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Item>) {
        val startPosition = params.startPosition
        val loadSize = params.loadSize
        val callbackData = getRangeData(startPosition, loadSize)
        callback.onResult(callbackData)
    }

    private fun getInitData(pageSize:Int, requestedStartPosition: Int, requestedLoadSize: Int):List<Item> {
        return getRangeData(requestedStartPosition, requestedLoadSize)
    }

    private fun getRangeData(startPosition: Int, loadSize: Int): List<Item> {
        return dataList.subList(startPosition, startPosition + loadSize)
    }

    private fun getTotalDataSize(): Int = dataList.size

    private val dataList = arrayListOf<Item>()

    init {
        for (i in 0 until 60) {
            dataList.add(
                Item(i)
            )
        }
    }
}