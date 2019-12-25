package com.tzion.android.movies.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tzion.corepresentation.MviUi
import com.tzion.android.movies.R
import com.tzion.android.movies.presentation.FindMoviesViewModel
import com.tzion.android.movies.presentation.userintent.FindMoviesUserIntent
import com.tzion.android.movies.presentation.userintent.FindMoviesUserIntent.*
import com.tzion.android.movies.presentation.uistate.FindMoviesUiState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.*

class FindMoviesFragment: Fragment(), MviUi<FindMoviesUserIntent, FindMoviesUiState> {

    private val searchFilterIntentPublisher = PublishSubject.create<SearchFilterIntent>()
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: FindMoviesViewModel by lazy(NONE) {
        ViewModelProviders
            .of(this, viewModelFactory)
            .get(FindMoviesViewModel::class.java)
    }
    private val disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.find_movies_menu, menu)
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchFilterIntentPublisher.onNext(SearchFilterIntent(query ?: ""))
                return false
            }
        })
    }

    override fun render(state: FindMoviesUiState) {
        //loading.visibility = if (state.isLoading) View.VISIBLE else View.GONE
    }

    override fun onStart() {
        super.onStart()
        disposable.add(viewModel.uiStates().subscribe(this::render))
        viewModel.processUserIntent(intents())
    }

    override fun intents(): Observable<FindMoviesUserIntent> {
        return Observable.just(searchFilterIntent()).cast(FindMoviesUserIntent::class.java)
    }

    private fun searchFilterIntent(): Observable<SearchFilterIntent> {
        return searchFilterIntentPublisher
    }

    override fun onStop() {
        disposable.dispose()
        super.onStop()
    }

}