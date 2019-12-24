package com.tzion.android.movies.domain

import com.tzion.android.core_domain.SingleUseCase
import com.tzion.android.core_domain.executor.PostExecutionThread
import com.tzion.android.core_domain.executor.ThreadExecutor
import com.tzion.android.movies.domain.model.DomainMovie
import io.reactivex.Single
import javax.inject.Inject

class FindMoviesByTextUseCase @Inject constructor(
    private val repository: Repository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread)
    : SingleUseCase<List<DomainMovie>, String>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: String?): Single<List<DomainMovie>> {
        require(!params.isNullOrEmpty())
        return repository.findMoviesByText(params)
    }

}