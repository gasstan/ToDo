package com.gasstan.todo.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDelete(
  modifier: Modifier = Modifier,
  onDelete: () -> Unit,
  content: @Composable () -> Unit,
) {
  val swipeState = rememberSwipeToDismissBoxState()

  val alignment = when (swipeState.dismissDirection) {
    SwipeToDismissBoxValue.StartToEnd -> {
      Alignment.CenterStart
    }

    else -> {
      Alignment.CenterEnd
    }
  }

  SwipeToDismissBox(
    state = swipeState,
    backgroundContent = {
      Box(
        contentAlignment = alignment,
        modifier = Modifier
          .fillMaxSize()
      ) {
        Icon(
          modifier = Modifier.minimumInteractiveComponentSize(),
          tint = Color.Red,
          imageVector = Icons.Outlined.Delete, contentDescription = null
        )
      }
    },
    modifier = modifier
  ) {
    content()
  }

  LaunchedEffect(swipeState.currentValue) {
    when (swipeState.currentValue) {
      SwipeToDismissBoxValue.Settled -> {}
      else -> {
        onDelete()
        swipeState.reset()
      }
    }
  }
}