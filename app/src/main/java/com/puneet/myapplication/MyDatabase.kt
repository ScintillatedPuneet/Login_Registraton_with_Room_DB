package com.puneet.myapplication

import android.app.Application
import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized



@Database(entities = [UserTable::class], version = 2)
abstract class MyDatabase : RoomDatabase() {
        abstract fun userdao(): UserTableDao
}
        class MyApp : Application() {
                val db by lazy {
                        Room.databaseBuilder(
                                applicationContext,
                                MyDatabase::class.java, "db"
                        ).fallbackToDestructiveMigration()
                                .build()
                }

}
/*
        companion object {
            private var INSTANCE: MyDatabase? = null
            @OptIn(InternalCoroutinesApi::class)
            fun getDatbase(context: Context): MyDatabase {
                val temInstance = INSTANCE
                if (temInstance != null) {
                    return temInstance
                }
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "user_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }

            }

        }
*/

