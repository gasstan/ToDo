package com.gasstan.todo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gasstan.todo.model.TodoItem

@Database(entities = [TodoItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase() {
  abstract fun todoItemDao(): TodoItemDao
}

fun provideDatabase(context: Context) =
  Room.databaseBuilder(
    context = context,
    klass = ToDoDatabase::class.java,
    name = "ToDoDatabase"
  ).build()