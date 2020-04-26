package com.tzion.execution

import io.reactivex.Scheduler

interface ExecutionThread {

    fun schedulerForObserving(): Scheduler

    fun schedulerForSubscribing(): Scheduler

}