package demo.x.myapplication.room

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import demo.x.myapplication.Item

class ItemPagedListAdapter : PagedListAdapter<Item, ItemViewHolder>(ItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}