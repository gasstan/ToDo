package com.gasstan.todo.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


sealed interface Resource<T> {
  class Loading<T> : Resource<T>
  class Empty<T> : Resource<T>
  class Error<T>(val message: String) : Resource<T>
  class Success<T>(val data: T) : Resource<T>

  fun getDataOrNull() = if (this is Success) data else null
  fun getErrorOrNull() = if (this is Error) message else null
}

@OptIn(ExperimentalContracts::class)
internal inline fun <T> Resource<T>.onSuccess(action: (value: T) -> Unit): Resource<T> {
  contract {
    callsInPlace(action, InvocationKind.AT_MOST_ONCE)
  }
  if (this is Resource.Success) action(data)
  return this
}

@OptIn(ExperimentalContracts::class)
internal inline fun <T> Resource<T>.onFailure(action: (message: String) -> Unit): Resource<T> {
  contract {
    callsInPlace(action, InvocationKind.AT_MOST_ONCE)
  }

  getErrorOrNull()?.let { action(it) }
  return this
}

@OptIn(ExperimentalContracts::class)
internal inline fun <T> Resource<T>.onLoading(action: () -> Unit): Resource<T> {
  contract {
    callsInPlace(action, InvocationKind.AT_MOST_ONCE)
  }

  if (this is Resource.Loading) action()
  return this
}

@OptIn(ExperimentalContracts::class)
internal inline fun <T> Resource<T>.onEmpty(action: () -> Unit): Resource<T> {
  contract {
    callsInPlace(action, InvocationKind.AT_MOST_ONCE)
  }

  if (this is Resource.Empty) action()
  return this
}