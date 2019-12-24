package com.tzion.android.core_domain

import com.tzion.android.core_domain.executor.PostExecutionThread
import com.tzion.android.core_domain.executor.ThreadExecutor
import io.reactivex.Single
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<TDomain, in Params> constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread) {

    private val subscription = Disposables.empty()

    protected abstract fun buildUseCaseObservable(params: Params? = null): Single<TDomain>

    open fun execute(params: Params? = null): Single<TDomain> {
        return this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
    }

}