package aut.moblab.wtb.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aut.moblab.wtb.databinding.FragmentListsBinding
import aut.moblab.wtb.ui.lists.tagged_movies_recycler_view.TaggedMovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListsFragment : Fragment(), Navigator {
    private lateinit var watchedAdapter: TaggedMovieAdapter
    private lateinit var blackListAdapter: TaggedMovieAdapter
    private lateinit var navController: NavController
    private lateinit var watchedMoviesRecyclerView: RecyclerView
    private lateinit var blackListRecyclerView: RecyclerView
    lateinit var binding: FragmentListsBinding
    val viewModel: ListsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watchedMoviesRecyclerView = binding.listsWatchedRecyclerView
        blackListRecyclerView = binding.listsBlackListRecyclerView

        navController = findNavController()

        binding.fragment = this

        watchedMoviesRecyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        blackListRecyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        watchedAdapter = TaggedMovieAdapter(viewModel, this)
        blackListAdapter = TaggedMovieAdapter(viewModel, this)

        initListeners()

    }

    private fun initListeners() {
        viewModel.watchedMovies.observe(viewLifecycleOwner) {
            watchedAdapter.clear()
            watchedAdapter.addItems(it)
            watchedMoviesRecyclerView.adapter = watchedAdapter
            binding.executePendingBindings()
        }
        viewModel.blackListedMovies.observe(viewLifecycleOwner) {
            blackListAdapter.clear()
            blackListAdapter.addItems(it)
            blackListRecyclerView.adapter = blackListAdapter
            binding.executePendingBindings()
        }

        addSwipeListener(ItemTouchHelper.LEFT, blackListAdapter, binding.listsBlackListRecyclerView)
        addSwipeListener(
            ItemTouchHelper.RIGHT,
            blackListAdapter,
            binding.listsBlackListRecyclerView
        )
        addSwipeListener(ItemTouchHelper.LEFT, watchedAdapter, binding.listsWatchedRecyclerView)
        addSwipeListener(ItemTouchHelper.RIGHT, watchedAdapter, binding.listsWatchedRecyclerView)
    }

    private fun addSwipeListener(
        direction: Int,
        adapter: TaggedMovieAdapter,
        recyclerView: RecyclerView
    ) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, direction) {
            override fun onMove(
                v: RecyclerView,
                h: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) {
                val removed = adapter.removeAt(h.adapterPosition)
                viewModel.removeTag(removed)
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun navigateToDetails(movieId: String) {
        navController.navigate(
            ListsFragmentDirections.actionListsFragmentToMovieDetailsFragment(
                movieId
            )
        )
    }

    override fun onResume() {
        viewModel.refreshLists()
        super.onResume()
    }
}
