package com.tzion.openmovies.ui.find

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tzion.android.MoviesApp
import com.tzion.mvi.MviUi
import com.tzion.openmovies.R
import com.tzion.openmovies.databinding.FragmentMoviesFindBinding
import com.tzion.openmovies.presentation.FindMoviesViewModel
import com.tzion.openmovies.presentation.model.UiMovie
import com.tzion.openmovies.presentation.uistate.FindMoviesUiState
import com.tzion.openmovies.presentation.uistate.FindMoviesUiState.*
import com.tzion.openmovies.presentation.userintent.FindMoviesUserIntent
import com.tzion.openmovies.presentation.userintent.FindMoviesUserIntent.SearchFilterUserIntent
import com.tzion.openmovies.ui.di.DaggerOpenMoviesComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class FindMoviesFragment: Fragment(), MviUi<FindMoviesUserIntent, FindMoviesUiState> {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val findMoviesViewModel: FindMoviesViewModel? by lazy {
        ViewModelProviders
            .of(this, viewModelFactory)
            .get(FindMoviesViewModel::class.java)
    }
    @Inject lateinit var findMoviesAdapter: FindMoviesAdapter
    private var binding: FragmentMoviesFindBinding? = null
    private val userIntents = MutableSharedFlow<SearchFilterUserIntent>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupInjection()
    }

    private fun setupInjection() {
        val appComponent = (activity?.applicationContext as MoviesApp).appComponent
        DaggerOpenMoviesComponent.factory().create(appComponent).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = FragmentMoviesFindBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiStatesAndSetUpUserIntents()
        setUpRecyclerView()
        setupMenuListeners()
    }

    private fun observeUiStatesAndSetUpUserIntents() {
        observeUiStates()
        setupUserIntents()
    }

    private fun observeUiStates() {
        findMoviesViewModel
            ?.uiStates()
            ?.onEach { uiStates -> renderUiStates(uiStates) }
            ?.launchIn(lifecycleScope)
    }

    override fun renderUiStates(uiState: FindMoviesUiState) {
        resetUi()
        when (uiState) {
            DefaultUiState       -> setScreenForDefault()
            LoadingUiState       -> setScreenForLoading()
            EmptyListUiState     -> setScreenForEmptyList()
            is ShowMoviesUiState -> setScreenForDisplayMovies(uiState.movies)
            is FailureUiState    -> setScreenForError(uiState.error)
        }
    }

    private fun resetUi() {
        binding?.apply {
            pbDisplayMovies.visibility = View.GONE
            ivError.visibility = View.GONE
            tvError.visibility = View.GONE
            rvDisplayMovies.visibility = View.GONE
            ivEmptyList.visibility = View.GONE
            tvEmptyList.visibility = View.GONE
            acivSearchDisplayMovies.visibility = View.GONE
            tvInstructions.visibility = View.GONE
        }
    }

    private fun setScreenForDefault() {
        binding?.apply {
            acivSearchDisplayMovies.visibility = View.VISIBLE
            tvInstructions.visibility = View.VISIBLE
        }
    }

    private fun setScreenForLoading() {
        binding?.apply {
            pbDisplayMovies.visibility = View.VISIBLE
        }
    }

    private fun setScreenForEmptyList() {
        binding?.apply {
            ivEmptyList.visibility = View.VISIBLE
            tvEmptyList.visibility = View.VISIBLE
        }
    }

    private fun setScreenForDisplayMovies(movies: List<UiMovie>) {
        binding?.apply {
            setAdapterData(movies)
            rvDisplayMovies.visibility = View.VISIBLE
        }
    }

    private fun setAdapterData(movies: List<UiMovie>) {
        findMoviesAdapter.setData(movies)
    }

    private fun setScreenForError(error: Throwable) {
        binding?.apply {
            ivError.visibility = View.VISIBLE
            tvError.text = getString(R.string.something_went_wrong, error.message)
            tvError.visibility = View.VISIBLE
        }
    }

    private fun setupUserIntents() {
        findMoviesViewModel?.processUserIntents(userIntents())
    }

    override fun userIntents(): Flow<FindMoviesUserIntent> = userIntents.asSharedFlow()

    private fun setUpRecyclerView() {
        try {
            binding?.apply {
                rvDisplayMovies.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                rvDisplayMovies.itemAnimator = DefaultItemAnimator()
                rvDisplayMovies.adapter = findMoviesAdapter
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun setupMenuListeners() {
        binding?.topAppBar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_search -> {
                    val searchView = menuItem.actionView as SearchView
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextChange(newText: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextSubmit(query: String?): Boolean {
                            viewLifecycleOwner.lifecycleScope.launch {
                                userIntents.emit(SearchFilterUserIntent(query.orEmpty()))
                            }
                            return false
                        }
                    })
                    true
                }
                else -> false
            }
        }
    }

}