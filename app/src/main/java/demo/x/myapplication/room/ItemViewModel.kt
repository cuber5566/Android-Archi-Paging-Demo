package demo.x.myapplication.room

import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import demo.x.myapplication.BoundaryCallback
import demo.x.myapplication.Item
import demo.x.myapplication.database.dao.ItemDao

class ItemViewModel : ViewModel() {

    private val networkList = arrayListOf<Item>()
    private lateinit var itemDao: ItemDao

    lateinit var itemPagedList: LiveData<PagedList<Item>>
    val isProgress = MutableLiveData<Boolean>()

    fun itemPagedList(config: PagedList.Config, itemDao: ItemDao): LiveData<PagedList<Item>> {

        this.itemDao = itemDao

        val boundaryCallback = BoundaryCallback<Item>()
                .onZeroItemsLoaded { onZeroItemsLoaded() }
                .onItemAtEndLoaded { onItemAtEndLoaded(it) }

        val pagedList = LivePagedListBuilder(itemDao.queryAll(), config)
                .setBoundaryCallback(boundaryCallback)
                .build()

        itemPagedList = pagedList
        return itemPagedList
    }

    private fun onZeroItemsLoaded() {
        loadInit()
    }

    private fun onItemAtEndLoaded(item: Item) {
        loadEnd(item)
    }

    /*
    * fake data resource
    * */
    private val backgroundHandlerThread = HandlerThread("background")
    private var backgroundHandler: Handler

    private fun loadInit() {
        isProgress.value = true
        Handler().postDelayed({
            val networkData = getNetworkData(networkList.first().id)
            insertDatabase(networkData)
            isProgress.value = false
        }, 2_000)
    }

    private fun loadEnd(item: Item) {
        isProgress.value = true
        Handler().postDelayed({
            val networkData = getNetworkData(item.id)
            insertDatabase(networkData)
            isProgress.value = false
        }, 2_000)
    }

    private fun getNetworkData(id: Int): List<Item> {
        isProgress.value = true
        val endIndex = if (id - 20 <= 0) 0 else id - 20
        val offset = networkList.size - 1
        return networkList.subList(offset - id, offset - endIndex + 1)
    }

    private fun insertDatabase(data: List<Item>) {
        backgroundHandler.post {
            itemDao.insertAll(data)
        }
    }

    fun clearAll() {
        backgroundHandler.post {
            itemDao.deleteAll()
        }
    }

    init {
        for (i in 199 downTo 0) {
            networkList.add(
                    Item(i)
            )
        }

        backgroundHandlerThread.start()
        backgroundHandler = Handler(backgroundHandlerThread.looper)
    }
}