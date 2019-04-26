package demo.x.myapplication.room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import demo.x.myapplication.Item
import demo.x.myapplication.R
import demo.x.myapplication.database.DatabaseFactory
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : AppCompatActivity() {

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var adapter: ItemPagedListAdapter

    companion object {
        private const val PAGE_SIZE = 20

        fun startIntent(context: Context): Intent {
            return Intent(context, RoomActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val itemDao = DatabaseFactory(application).itemDao()

        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setEnablePlaceholders(true)
                .setPageSize(PAGE_SIZE)
                .build()

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)

        itemViewModel.itemPagedList(config, itemDao)
                .observe(this, Observer {
                    onGetItemPageList(it)
                })

        itemViewModel.isProgress.observe(this, Observer {
            onProgress(it)
        })

        adapter = ItemPagedListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        clearButton.setOnClickListener {
            itemViewModel.clearAll()
        }
    }

    private fun onProgress(progress: Boolean) {
        progressBar.visibility =
                if (progress) View.VISIBLE
                else View.GONE
    }

    private fun onGetItemPageList(pagedList: PagedList<Item>) {
        adapter.submitList(pagedList)
    }
}