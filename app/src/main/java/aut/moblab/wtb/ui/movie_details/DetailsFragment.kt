package aut.moblab.wtb.ui.movie_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aut.moblab.wtb.R
import aut.moblab.wtb.databinding.FragmentDetailsBinding
import aut.moblab.wtb.ui.movie_details.award_recycler_view.AwardAdapter
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var adapter: AwardAdapter
    private lateinit var navController: NavController
    private lateinit var recyclerView: RecyclerView
    private val args: DetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentDetailsBinding
    val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.detailsAwardsRecyclerview
        binding.fragment = this

        navController = findNavController()

        recyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        adapter = AwardAdapter()

        viewModel.loadMovie(args.movieId)
        viewModel.loadAwards(args.movieId)
        initListeners()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initListeners() {
        viewModel.awardModels.observe(viewLifecycleOwner) {
            adapter.addItems(it)
            recyclerView.adapter = adapter
        }
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            binding.detailsYear.text = movie.year.toString()
            binding.detailsDescription.text = movie.description
            binding.detailsScore.text = movie.rating.toString()
            binding.detailsTitle.text = movie.title
            context?.let {
                Glide.with(it)
                    .load(movie.image)
                    .into(binding.detailsMoviePoster)
            }
            binding.executePendingBindings()
        }
        viewModel.hasSeen.observe(viewLifecycleOwner) { hasSeen ->
            if (hasSeen) {
                binding.seenButton.background = context?.getDrawable(R.drawable.eye_active)
            } else {
                binding.seenButton.background = context?.getDrawable(R.drawable.eye_inactive)
            }
            binding.executePendingBindings()
        }
        viewModel.movieTag.observe(viewLifecycleOwner) {
            binding.tag = it
            binding.executePendingBindings()
        }
    }

    fun favouriteClicked() {
        viewModel.favouriteClicked()
    }

    fun blackListClicked() {
        viewModel.blackListClicked()
    }

    fun seenClicked() {
        viewModel.seenClicked()
    }

}
