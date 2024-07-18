package com.gasstan.todo.screen.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.gasstan.todo.model.TodoItem
import com.gasstan.todo.navigation.Destinations
import com.gasstan.todo.screen.TasksViewModel
import com.gasstan.todo.utils.onLoading
import com.gasstan.todo.utils.onSuccess
import com.gasstan.todo.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: TasksViewModel = koinViewModel()) {
  val tasks by viewModel.tasks.collectAsStateWithLifecycle()
  var showBottomSheet by remember { mutableStateOf(false) }

  var openDeleteItemDialog by remember { mutableStateOf(false) }
  var taskToDelete: TodoItem? by remember { mutableStateOf(null) }

  if (openDeleteItemDialog) {
    DeleteItemDialog(
      onDismissRequest = { openDeleteItemDialog = false },
      onConfirmation = {
        openDeleteItemDialog = false
        taskToDelete?.let { viewModel.deleteTask(it) }
        taskToDelete = null
      },
      dialogTitle = stringResource(R.string.delete_item),
      dialogText = stringResource(R.string.delete_item_desc),
      icon = Icons.Default.Delete
    )
  }

  Scaffold(
    topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
    floatingActionButton = {
      FloatingActionButton(
        onClick = { showBottomSheet = true },
        shape = CircleShape
      ) {
        Icon(
          Icons.Default.Add,
          contentDescription = stringResource(R.string.add)
        )
      }
    },
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    if (showBottomSheet) {
      AddNewTaskBottomSheet(
        onAddNewTask = viewModel::addNewTask,
        onDismiss = { showBottomSheet = false }
      )
    }
    tasks
      .onSuccess { tasks ->
        ContentComposable(
          tasks = tasks,
          onCheckBoxClick = viewModel::updateTask,
          onItemClick = { todoItem -> navController.navigate(Destinations.Detail(todoItem.id.toString()).route) },
          onDeleteTask = {
            taskToDelete = it
            openDeleteItemDialog = true
          },
          modifier = Modifier.padding(innerPadding)
        )
      }
      .onLoading {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator()
        }
      }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ContentComposable(
  tasks: List<TodoItem>,
  onCheckBoxClick: (TodoItem) -> Unit,
  onItemClick: (TodoItem) -> Unit,
  onDeleteTask: (TodoItem) -> Unit,
  modifier: Modifier = Modifier
) {

  LazyColumn(
    modifier = modifier.animateContentSize(),
    contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding))
  ) {
    items(tasks, key = { it.id }) { task ->
      SwipeToDelete(
        modifier = Modifier.animateItemPlacement(),
        onDelete = { onDeleteTask(task) }
      ) {
        TodoItemComposable(
          item = task,
          onItemClick = onItemClick,
          onCheckBoxClick = onCheckBoxClick
        )
      }
      Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
    }
  }
}

@Composable
private fun TodoItemComposable(
  item: TodoItem,
  onItemClick: (TodoItem) -> Unit,
  onCheckBoxClick: (TodoItem) -> Unit
) {
  var checked by remember { mutableStateOf(item.completed) }

  Card(
    onClick = { onItemClick(item) },
    modifier = Modifier
      .fillMaxWidth()
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Checkbox(checked = checked, onCheckedChange = {
        checked = it
        onCheckBoxClick(item.copy(completed = it))
      })
      Text(text = item.title)
    }
  }
}

