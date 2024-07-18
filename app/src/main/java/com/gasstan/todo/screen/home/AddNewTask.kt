package com.gasstan.todo.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.gasstan.todo.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTaskBottomSheet(onDismiss: () -> Unit, onAddNewTask: (String, String) -> Unit) {
  val sheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = false,
  )
  var title by remember { mutableStateOf("") }
  var isTitleError by remember {
    mutableStateOf(false)
  }
  var description by remember { mutableStateOf("") }

  ModalBottomSheet(
    modifier = Modifier
      .fillMaxHeight()
      .padding(dimensionResource(id = R.dimen.padding_small)),
    sheetState = sheetState,
    onDismissRequest = onDismiss,
  ) {
    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))) {
      Text(
        text = stringResource(R.string.add_new_task),
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
      )
      Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding)))
      TextField(
        value = title,
        onValueChange = {
          title = it
          if (it.isNotEmpty()) isTitleError = false
        },
        supportingText = { if (isTitleError) Text(text = stringResource(R.string.this_field_is_mandatory)) },
        isError = isTitleError,
        label = { Text(text = stringResource(R.string.title)) },
        modifier = Modifier.fillMaxWidth()
      )
      Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
      TextField(
        value = description,
        onValueChange = { description = it },
        label = { Text(text = stringResource(R.string.description)) },
        modifier = Modifier.fillMaxWidth()
      )
      Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
      Button(
        onClick = {
          if (title.isEmpty()) {
            isTitleError = true
            return@Button
          }
          onAddNewTask(title, description)
          onDismiss()
        },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_small)),
        modifier = Modifier.align(Alignment.End)
      ) {
        Text(text = stringResource(id = R.string.add))
      }
    }
  }
}