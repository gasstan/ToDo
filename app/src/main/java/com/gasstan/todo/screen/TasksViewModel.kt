package com.gasstan.todo.screen

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasstan.todo.model.TodoItem
import com.gasstan.todo.repository.TasksRepository
import com.gasstan.todo.utils.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant

class TasksViewModel(private val tasksRepo: TasksRepository) : ViewModel() {

  val tasks: StateFlow<Resource<List<TodoItem>>> =
    tasksRepo.getTasks().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Resource.Empty())

  fun getTask(id: String) = tasksRepo.getTask(id).distinctUntilChanged()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Resource.Empty())

  fun updateTask(todoItem: TodoItem) {
    viewModelScope.launch { tasksRepo.insertTask(todoItem) }
  }

  @SuppressLint("NewApi")
  fun addNewTask(title: String, description: String) {
    viewModelScope.launch {
      tasksRepo.insertTask(
        TodoItem(
          createdAt = Instant.now(),
          title = title,
          description = description,
          completed = false,
        )
      )
    }
  }

  fun deleteTask(task: TodoItem) {
    viewModelScope.launch {
      tasksRepo.deleteTask(task)
    }
  }
}