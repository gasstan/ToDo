package com.gasstan.todo.network

import com.gasstan.todo.model.TodoItem
import io.ktor.client.HttpClient
import io.ktor.client.request.get

private const val ENDPOINT = "https://6669414f2e964a6dfed4548a.mockapi.io/api/v1/tasks"

class TasksApi(private val client: HttpClient) {

  suspend fun getTasks(
  ): List<TodoItem> = client.get(ENDPOINT)
}