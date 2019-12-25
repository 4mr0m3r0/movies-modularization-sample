package com.tzion.corepresentation.execution

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppExecutionThread: ExecutionThread {

    override fun schedulerForObserving(): Scheduler = AndroidSchedulers.mainThread()

    override fun schedulerForSubscribing(): Scheduler = Schedulers.io()

}