package com.tzion.execution

import com.tzion.execution.ExecutionThreadEnvironment.*

object ExecutionThreadFactory {

    fun makeExecutionThread(environment: ExecutionThreadEnvironment): ExecutionThread =
        when (environment) {
            APPLICATION -> AppExecutionThread()
            TESTING -> FakeExecutionThread()
        }

}