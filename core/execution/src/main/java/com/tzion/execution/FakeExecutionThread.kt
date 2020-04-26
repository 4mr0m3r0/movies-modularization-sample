package com.tzion.execution

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

internal class FakeExecutionThread: ExecutionThread {

    override fun schedulerForObserving(): Scheduler = Schedulers.trampoline()

    override fun schedulerForSubscribing(): Scheduler = Schedulers.trampoline()

}