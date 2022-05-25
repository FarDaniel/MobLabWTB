package aut.moblab.wtb.ui.landing_dashboard.movies_recycler_view

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import aut.moblab.wtb.databinding.ItemMoviePreviewBinding
import aut.moblab.wtb.ui.landing_dashboard.NavigationHelper
import com.bumptech.glide.Glide

class MoviesViewHolder(
    val binding: ItemMoviePreviewBinding,
    private val navigator: NavigationHelper
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: MoviesModel, context: Context) {
        Glide.with(context)
            .load(model.image)
            .into(binding.moviePreviewFirstImage)
        binding.moviePreviewFirstTitle.text = model.title

        initListeners(model)
        binding.executePendingBindings()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners(model: MoviesModel) {
        binding.moviePreviewFirstImage.setOnClickListener {
            navigator.watchDetails(model.movieId)
            true
        }
        binding.moviePreviewFirstImage.setOnLongClickListener {
            navigator.addToWatched(model.movieId, model.title)
            true
        }
        binding.moviePreviewFirstImage.setOnLongClickListener {
            navigator.addToWatched(model.movieId, model.title)
            true
        }
    }
}