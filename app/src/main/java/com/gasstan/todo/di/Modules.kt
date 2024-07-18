package com.gasstan.todo.di

import com.gasstan.todo.db.provideDatabase
import com.gasstan.todo.network.TasksApi
import com.gasstan.todo.network.provideKtorHttpClient
import com.gasstan.todo.repository.TasksRepository
import com.gasstan.todo.screen.TasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  single { provideKtorHttpClient() }
  single { provideDatabase(get()) }

  single { TasksApi(get()) }
  single { TasksRepository(get(), get()) }

  viewModel { TasksViewModel(get()) }
}