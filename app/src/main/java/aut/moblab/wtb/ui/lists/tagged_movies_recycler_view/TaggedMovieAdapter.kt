package aut.moblab.wtb.ui.lists.tagged_movies_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aut.moblab.wtb.databinding.ItemTitleBinding
import aut.moblab.wtb.local_data.models.MovieTag
import aut.moblab.wtb.ui.lists.MovieTagRefreshener
import aut.moblab.wtb.ui.lists.Navigator

class TaggedMovieAdapter(
    private val tagRefreshener: MovieTagRefreshener,
    private val navigator: Navigator
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<MovieTag> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemTitleBinding.inflate(layoutInflater, parent, false)
        return TaggedMovieViewHolder(binding, tagRefreshener, navigator)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is TaggedMovieViewHolder) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(moviesItems: List<MovieTag>) {
        items.addAll(moviesItems)
    }

    fun clear() {
        items.clear()
    }

    fun removeAt(index: Int): MovieTag {
        val item = items.removeAt(index)
        notifyItemRemoved(index)
        return item
    }
}
