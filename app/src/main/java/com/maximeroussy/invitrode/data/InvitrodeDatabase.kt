package com.maximeroussy.invitrode.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.maximeroussy.invitrode.data.words.Word
import com.maximeroussy.invitrode.data.words.WordDao

@Database(entities = [(Word::class)], version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class InvitrodeDatabase: RoomDatabase() {
  abstract fun wordDao(): WordDao

  companion object {
    private var INSTANCE: InvitrodeDatabase? = null

    fun getInstance(context: Context): InvitrodeDatabase {
      if (INSTANCE == null) {
        synchronized(InvitrodeDatabase::class) {
          INSTANCE = Room.databaseBuilder(context.applicationContext,
              InvitrodeDatabase::class.java, "weather.db")
              .build()
        }
      }
      return INSTANCE!!
    }

    fun destroyInstance() {
      INSTANCE = null
    }
  }
}
