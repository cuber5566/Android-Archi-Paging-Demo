package demo.x.myapplication.room

import androidx.recyclerview.widget.DiffUtil
import demo.x.myapplication.Item

class ItemDiff : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}