package com.gasstan.todo.screen.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.gasstan.todo.screen.TasksViewModel
import com.gasstan.todo.utils.onSuccess
import com.gasstan.todo.R
import com.gasstan.todo.model.TodoItem
import org.koin.androidx.compose.koinViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetail(
  taskId: String?,
  navController: NavHostController,
  viewModel: TasksViewModel = koinViewModel()
) {
  if (taskId == null) return
  val task by viewModel.getTask(taskId).collectAsStateWithLifecycle()

  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
          IconButton(onClick = navController::popBackStack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = null
            )
          }
        })
    },
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    task
      .onSuccess {
        ContentComposable(
          task = it,
          onCheckBoxClick = viewModel::updateTask,
          modifier = Modifier.padding(innerPadding)
        )
      }
  }
}

@SuppressLint("NewApi")
@Composable
private fun ContentComposable(
  task: TodoItem,
  onCheckBoxClick: (TodoItem) -> Unit,
  modifier: Modifier = Modifier
) {
  var checked by remember { mutableStateOf(task.completed) }

  Card(
    modifier = modifier
      .padding(dimensionResource(id = R.dimen.padding_small))
      .fillMaxWidth()
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Checkbox(checked = checked, onCheckedChange = {
          checked = it
          onCheckBoxClick(task.copy(completed = it))
        })
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
          .withZone(ZoneId.systemDefault())

        val formattedInstant = formatter.format(task.createdAt)
        Text(
          text = formattedInstant,
          fontSize = 18.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
    Column(
      modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ) {
      Text(text = task.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
      Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
      Text(text = task.description)
    }
  }
}