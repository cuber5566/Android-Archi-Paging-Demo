package demo.x.myapplication.database

import demo.x.myapplication.database.dao.ItemDao

interface AppDatabase {

    fun itemDao(): ItemDao

    fun clear()
}