package com.tzion.android.core_domain.executor

import io.reactivex.Scheduler

interface PostExecutionThread {

    val scheduler: Scheduler

}