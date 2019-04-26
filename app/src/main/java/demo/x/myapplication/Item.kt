package demo.x.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(

    @PrimaryKey
    val id: Int
)