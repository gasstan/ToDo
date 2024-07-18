package com.gasstan.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gasstan.todo.utils.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
@Entity(tableName = "TodoItems")
data class TodoItem(
  @SerialName("createdAt")
  @Serializable(with = InstantSerializer::class)
  var createdAt: Instant,
  @SerialName("title")
  var title: String,
  @SerialName("description")
  var description: String,
  @SerialName("completed")
  var completed: Boolean,
  @SerialName("id")
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0
)

