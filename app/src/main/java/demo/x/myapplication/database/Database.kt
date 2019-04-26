package demo.x.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import demo.x.myapplication.database.dao.ItemDao
import demo.x.myapplication.Item

@Database(entities = [Item::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}