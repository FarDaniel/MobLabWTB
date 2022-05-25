package aut.moblab.wtb.ui.movie_details.award_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aut.moblab.wtb.databinding.ItemAwardBinding

class AwardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<AwardModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemAwardBinding.inflate(layoutInflater, parent, false)
        return AwardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is AwardViewHolder) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(moviesItems: List<AwardModel>) {
        items.addAll(moviesItems)
    }
}