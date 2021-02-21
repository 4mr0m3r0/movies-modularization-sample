package com.tzion.mvi.execution

import com.tzion.mvi.execution.ExecutionThreadEnvironment.APPLICATION
import com.tzion.mvi.execution.ExecutionThreadEnvironment.TESTING

object ExecutionThreadFactory {

    fun makeExecutionThread(environment: ExecutionThreadEnvironment): ExecutionThread =
        when (environment) {
            APPLICATION -> AppExecutionThread()
            TESTING     -> FakeExecutionThread()
        }

}