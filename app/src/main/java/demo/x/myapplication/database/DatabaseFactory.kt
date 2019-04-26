package demo.x.myapplication.database

import android.app.Application
import androidx.room.Room

class DatabaseFactory(application: Application) : AppDatabase {

    private val database: Database = Room
        .databaseBuilder(application, Database::class.java, DatabaseConfig.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    override fun itemDao() = database.itemDao()

    override fun clear() {
        database.clearAllTables()
    }
}