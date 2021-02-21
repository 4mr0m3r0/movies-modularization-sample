package com.tzion.mvi.execution

import kotlinx.coroutines.CoroutineDispatcher

interface ExecutionThread {

    fun uiThread(): CoroutineDispatcher

    fun ioThread(): CoroutineDispatcher

}