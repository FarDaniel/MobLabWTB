package aut.moblab.wtb.ui.landing_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aut.moblab.wtb.databinding.FragmentLandingBinding
import aut.moblab.wtb.ui.landing_dashboard.movies_recycler_view.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingFragment : Fragment(), NavigationHelper {
    private lateinit var adapter: MoviesAdapter
    override lateinit var navController: NavController

    private lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentLandingBinding
    val viewModel: LandingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.landingRecyclerview

        navController = findNavController()

        val layoutManager = GridLayoutManager(view.context, 2, GridLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        binding.fragment = this

        adapter = MoviesAdapter(view.context, this)

        initListeners()

    }

    override fun onResume() {
        viewModel.update()
        super.onResume()
    }

    private fun initListeners() {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.clear()
            adapter.addItems(it)
            recyclerView.adapter = adapter
        }

    }

    override fun watchDetails(movieId: String) {
        navController.navigate(
            LandingFragmentDirections.actionLandingFragmentToMovieDetailsFragment(movieId)
        )
    }

    override fun addToWatched(movieId: String, title: String) {
        viewModel.addMovieTag(movieId, title)
        val pos = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
        adapter.removeItem(movieId)
        recyclerView.adapter = adapter
        binding.executePendingBindings()
        (recyclerView.layoutManager as GridLayoutManager).scrollToPosition(pos)
    }

    fun navigateToLists() {
        navController.navigate(LandingFragmentDirections.actionLandingFragmentToListsFragment())
    }
}
