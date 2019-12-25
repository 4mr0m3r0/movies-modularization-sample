package com.tzion.corepresentation.execution

import io.reactivex.Scheduler

interface ExecutionThread {

    fun schedulerForObserving(): Scheduler

    fun schedulerForSubscribing(): Scheduler

}