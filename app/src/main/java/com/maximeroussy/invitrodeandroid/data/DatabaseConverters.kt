package com.maximeroussy.invitrodeandroid.data

import androidx.room.TypeConverter
import java.util.Date

class DatabaseConverters {
  @TypeConverter
  fun fromTimestamp(value: Long): Date {
    return Date(value)
  }

  @TypeConverter
  fun dateToTimestamp(date: Date): Long {
    return date.time
  }
}
