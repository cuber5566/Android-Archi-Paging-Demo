package demo.x.myapplication.database.dao

import androidx.paging.DataSource
import androidx.room.*
import demo.x.myapplication.Item

@Dao
interface ItemDao {

    @Query("SELECT * FROM Item ORDER BY id DESC ")
    fun queryAll(): DataSource.Factory<Int, Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newsList: List<Item>)

    @Query("DELETE FROM Item")
    fun deleteAll()
}