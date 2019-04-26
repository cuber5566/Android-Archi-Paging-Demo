package demo.x.myapplication.pagedkeyed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import demo.x.myapplication.*
import kotlinx.android.synthetic.main.activity_paged_key.*
import java.util.concurrent.Executors

class PagedKeyedActivity : AppCompatActivity() {

    private val backgroundHandlerThread = HandlerThread("background")
    private lateinit var backgroundHandler: Handler

    companion object {
        fun startIntent(context: Context): Intent {
            return Intent(context, PagedKeyedActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paged_key)
        backgroundHandlerThread.start()
        backgroundHandler = Handler(backgroundHandlerThread.looper)

        val dataSource = MyPageKeyedDataSource()
        val config = PagedList.Config.Builder()
            .setPageSize(3) //Page size must be a positive number
            .setInitialLoadSizeHint(4) // 要比PageSize大，預設為 pageSize * 3
            .build()

        backgroundHandler.post {

            /* 設定BoundaryCallback，監聽資料讀取狀態 */
            val boundaryCallback = BoundaryCallback<Item>()
            boundaryCallback.onItemAtEndLoaded {
                Toast.makeText(this, "End_[${it.id}]", Toast.LENGTH_LONG).show()
            }

            /* 產生PagedList, 需要在背景執行 */
            val pagedList = PagedList.Builder<Int, Item>(dataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(UiExecutor())
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