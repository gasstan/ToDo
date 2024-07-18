package com.gasstan.todo.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant

class Converters {
  @RequiresApi(Build.VERSION_CODES.O)
  @TypeConverter
  fun fromInstant(value: Long?): Instant? {
    return value?.let { Instant.ofEpochMilli(value) }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  @TypeConverter
  fun dateToTimestamp(instant: Instant?): Long? {
    return instant?.toEpochMilli()
  }
}