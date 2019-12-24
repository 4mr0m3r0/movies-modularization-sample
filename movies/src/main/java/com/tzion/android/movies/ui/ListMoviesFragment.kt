package com.tzion.android.movies.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tzion.android.core_presentation.MviUi
import com.tzion.android.movies.R
import com.tzion.android.movies.presentation.ListMoviesViewModel
import com.tzion.android.movies.presentation.intent.ListMoviesIntent
import com.tzion.android.movies.presentation.intent.ListMoviesIntent.*
import com.tzion.android.movies.presentation.state.ListMoviesUiState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.*

class ListMoviesFragment: Fragment(), MviUi<ListMoviesIntent, ListMoviesUiState> {

    private val searchFilterIntentPublisher = PublishSubject.create<SearchFilterIntent>()
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ListMoviesViewModel by lazy(NONE) {
        ViewModelProviders
            .of(this, viewModelFactory)
            .get(ListMoviesViewModel::class.java)
    }
    private val disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
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

    override fun render(state: ListMoviesUiState) {
        //loading.visibility = if (state.isLoading) View.VISIBLE else View.GONE
    }

    override fun onStart() {
        super.onStart()
        disposable.add(viewModel.states().subscribe(this::render))
        viewModel.processIntent(intents())
    }

    override fun intents(): Observable<ListMoviesIntent> {
        return Observable.merge(initialIntent(), searchFilterIntent())
    }

    private fun initialIntent(): Observable<InitialIntent> = Observable.just(InitialIntent)

    private fun searchFilterIntent(): Observable<SearchFilterIntent> {
        return searchFilterIntentPublisher
    }

    override fun onStop() {
        disposable.dispose()
        super.onStop()
    }

}