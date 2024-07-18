package com.gasstan.todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gasstan.todo.screen.detail.ItemDetail
import com.gasstan.todo.screen.home.HomeScreen

@Composable
fun AppNavHost(
  modifier: Modifier = Modifier,
  navController: NavHostController,
  startDestination: String = Destinations.Home.route,
) {
  NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = startDestination
  ) {
    composable(Destinations.Home.route) {
      HomeScreen(navController)
    }

    composable(Destinations.Detail("{taskId}").route) {
      val taskId = it.arguments?.getString("taskId")
      ItemDetail(taskId = taskId, navController = navController)
    }

    composable(Destinations.NewItem.route) {

    }
  }
}

sealed class Destinations(val route: String) {
  object Home : Destinations("home")
  class Detail(taskId: String) : Destinations("detail/$taskId")
  object NewItem : Destinations("newItem")
}