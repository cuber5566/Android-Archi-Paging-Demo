package demo.x.myapplication.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import demo.x.myapplication.Item
import demo.x.myapplication.R
import kotlinx.android.synthetic.main.view_holder_item.view.*

class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item, parent, false)
) {

    fun onBind(item: Item?) {

        itemView.textView.text = item?.id?.toString() ?: "PlaceHolder"
    }
}