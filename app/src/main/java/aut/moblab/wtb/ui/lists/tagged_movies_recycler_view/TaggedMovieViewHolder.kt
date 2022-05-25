package aut.moblab.wtb.ui.lists.tagged_movies_recycler_view

import androidx.recyclerview.widget.RecyclerView
import aut.moblab.wtb.databinding.ItemTitleBinding
import aut.moblab.wtb.local_data.models.MovieTag
import aut.moblab.wtb.ui.lists.MovieTagRefreshener
import aut.moblab.wtb.ui.lists.Navigator

class TaggedMovieViewHolder(
    val binding: ItemTitleBinding,
    val tagRefreshener: MovieTagRefreshener,
    val navigator: Navigator
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tag: MovieTag) {
        binding.tag = tag

        initListeners()
    }

    private fun initListeners() {
        binding.favouriteIcon.setOnClickListener {
            val movieTag = binding.tag
            movieTag?.let { tag ->
                val newTag = MovieTag(
                    id = tag.id,
                    movieId = tag.movieId,
                    title = tag.title,
                    isFavourite = !tag.isFavourite,
                    isBlackListed = tag.isBlackListed
                )
                binding.tag = newTag
                tagRefreshener.refreshTag(newTag)
            }
            binding.executePendingBindings()
        }
        binding.titleCard.setOnClickListener {
            binding.tag?.movieId?.let {
                navigator.navigateToDetails(it)
            }
        }
    }
}
