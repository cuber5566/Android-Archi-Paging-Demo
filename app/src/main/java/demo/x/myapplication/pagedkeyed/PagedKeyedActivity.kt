package demo.x.myapplication.pagedkeyed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import demo.x.myapplication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_paged_key.*

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

            RxPagedListBuilder<Int, Item>(MyDataSourceFactory(), config)
                .buildFlowable(BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { pagedList ->
                    onPagedListUpdate(pagedList)
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

        removeButton.setOnClickListener {
            pagedList.dataSource.invalidate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundHandlerThread.quit()
        backgroundHandler.removeCallbacksAndMessages(null)
    }

    class MyDataSourceFactory : DataSource.Factory<Int, Item>() {

        override fun create(): DataSource<Int, Item> {
            return MyPageKeyedDataSource()
        }
    }

}