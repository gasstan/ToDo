package com.gasstan.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.gasstan.todo.navigation.AppNavHost
import com.gasstan.todo.theme.ToDoTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ToDoTheme {
        AppNavHost(navController = rememberNavController())
      }
    }
  }
}