package demo.x.myapplication.itemkeyed

import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import demo.x.myapplication.BoundaryCallback
import demo.x.myapplication.PagedListCallback
import demo.x.myapplication.Item
import demo.x.myapplication.UiExecutor
import kotlinx.android.synthetic.main.activity_item_key.*
import java.util.concurrent.Executors

class ItemKeyedActivity : AppCompatActivity() {

    private val backgroundHandlerThread = HandlerThread("background")
    private lateinit var backgroundHandler: Handler

    companion object {
        fun startIntent(context: Context): Intent {
            return Intent(context, ItemKeyedActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(demo.x.myapplication.R.layout.activity_item_key)
        backgroundHandlerThread.start()
        backgroundHandler = Handler(backgroundHandlerThread.looper)


        val dataSource = MyItemKeyedDataSource()


        val config = PagedList.Config.Builder()

                //BJ4
                .setPageSize(3)

                // 要比PageSize大，預設為 pageSize * 3
                .setInitialLoadSizeHint(5)

                // 當資料剩下 N 筆時提早加載更多，預設 pageSize
                .setPrefetchDistance(4)

                // pagedList 最大size，FIFO，數字至少是 pageSize + ( 2 * PrefetchDistance  )
                .setMaxSize(20)

                // 有沒有 null 物件
                .setEnablePlaceholders(false)

                .build()

        backgroundHandler.post {

            /* 設定BoundaryCallback，監聽資料讀取狀態 */
            val boundaryCallback = BoundaryCallback<Item>()
            boundaryCallback.onItemAtEndLoaded {
                Toast.makeText(this, "End_[${it.id}]", Toast.LENGTH_LONG).show()
            }

            /* 產生PagedList, 需要在背景執行 */
            val pagedList = PagedList.Builder<Int, Item>(dataSource, config)

                    // 第一次 init 用的 key
                    .setInitialKey(30)

                    // require，一定要在 background thread
                    .setFetchExecutor(Executors.newSingleThreadExecutor())

                    // require，一定要在 ui thread
                    .setNotifyExecutor(UiExecutor())

                    // option，監聽讀取資料狀態
                    .setBoundaryCallback(boundaryCallback)

                    .build()

            onPagedListUpdate(pagedList)

            /* 設定PagedListCallback ，監聽 PagedList */
            val pagedListCallback = PagedListCallback()
            pagedList.addWeakCallback(null, pagedListCallback)
            pagedListCallback.onInserted { _, _ ->
                onPagedListUpdate(pagedList)
            }

            /* pagedList.loadAround 加載更多資料 */
            loadAroundButton.setOnClickListener {
                pagedList.loadAround(pagedList.loadedCount - 1)
            }
        }
    }

    private fun onPagedListUpdate(pagedList: PagedList<Item>) {
        dataText.text = pagedList.snapshot().map {
            if (it == null) {
                "null\n"
            } else {
                it.toString() + "\n"
            }
        }.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundHandlerThread.quit()
        backgroundHandler.removeCallbacksAndMessages(null)
    }

}