package demo.x.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import demo.x.myapplication.itemkeyed.ItemKeyedActivity
import demo.x.myapplication.pagedkeyed.PagedKeyedActivity
import demo.x.myapplication.positional.PositionalActivity
import demo.x.myapplication.room.RoomActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemKeyDataSourceButton.setOnClickListener {
            startActivity(ItemKeyedActivity.startIntent(this))
        }

        pagedKeyedDataSourceButton.setOnClickListener {
            startActivity(PagedKeyedActivity.startIntent(this))
        }

        positionalDataSourceButton.setOnClickListener {
            startActivity(PositionalActivity.startIntent(this))
        }

        roomButton.setOnClickListener {
            startActivity(RoomActivity.startIntent(this))
        }
    }
}
