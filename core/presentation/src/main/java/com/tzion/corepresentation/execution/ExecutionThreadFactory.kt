package com.tzion.corepresentation.execution

import com.tzion.corepresentation.execution.ExecutionThreadEnvironment.*

object ExecutionThreadFactory {

    fun makeExecutionThread(environment: ExecutionThreadEnvironment): ExecutionThread =
        when (environment) {
            APPLICATION -> AppExecutionThread()
            TESTING     -> FakeExecutionThread()
        }

}