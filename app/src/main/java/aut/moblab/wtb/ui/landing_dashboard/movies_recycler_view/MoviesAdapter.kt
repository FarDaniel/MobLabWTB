package aut.moblab.wtb.ui.landing_dashboard.movies_recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aut.moblab.wtb.databinding.ItemMoviePreviewBinding
import aut.moblab.wtb.ui.landing_dashboard.NavigationHelper

class MoviesAdapter(val context: Context, private val navigator: NavigationHelper) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<MoviesModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemMoviePreviewBinding.inflate(layoutInflater, parent, false)
        return MoviesViewHolder(binding, navigator)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is MoviesViewHolder) {
            holder.bind(item, context)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(moviesItems: List<MoviesModel>) {
        items.addAll(moviesItems)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun removeItem(movieId: String) {
        for (i in 0 until items.size) {
            if (movieId == items[i].movieId) {
                items.removeAt(i)
                return
            }
        }
    }
}
