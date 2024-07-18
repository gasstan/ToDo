package com.gasstan.todo.screen.home

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.gasstan.todo.R

@Composable
fun DeleteItemDialog(
  onDismissRequest: () -> Unit,
  onConfirmation: () -> Unit,
  dialogTitle: String,
  dialogText: String,
  icon: ImageVector,
) {
  AlertDialog(
    icon = {
      Icon(icon, contentDescription = null)
    },
    title = {
      Text(text = dialogTitle)
    },
    text = {
      Text(text = dialogText)
    },
    onDismissRequest = {
      onDismissRequest()
    },
    confirmButton = {
      TextButton(
        onClick = {
          onConfirmation()
        }
      ) {
        Text(stringResource(R.string.confirm))
      }
    },
    dismissButton = {
      TextButton(
        onClick = {
          onDismissRequest()
        }
      ) {
        Text(stringResource(R.string.dismiss))
      }
    }
  )
}