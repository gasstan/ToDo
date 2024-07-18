package com.gasstan.todo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gasstan.todo.model.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTask(todoItem: TodoItem)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTasks(todoItems: List<TodoItem>)

  @Query("SELECT * FROM TodoItems WHERE id=:id")
  suspend fun getTask(id: String): TodoItem?

  @Query("SELECT * FROM TodoItems")
  fun getAllTasksFlow(): Flow<List<TodoItem>>

  @Query("SELECT (SELECT COUNT(*) FROM TodoItems) == 0")
  suspend fun isEmpty(): Boolean

  @Delete
  suspend fun delete(task: TodoItem)
}