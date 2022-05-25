package aut.moblab.wtb.ui.movie_details.award_recycler_view

import androidx.recyclerview.widget.RecyclerView
import aut.moblab.wtb.databinding.ItemAwardBinding

class AwardViewHolder(val binding: ItemAwardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: AwardModel) {
        binding.awardText.text = model.title
        binding.awardDetails.text = model.description
        binding.executePendingBindings()
    }
}
