package com.maximeroussy.invitrode.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
