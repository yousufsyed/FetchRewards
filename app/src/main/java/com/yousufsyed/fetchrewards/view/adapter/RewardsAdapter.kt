package com.yousufsyed.fetchrewards.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yousufsyed.fetchrewards.databinding.RewardsHeaderBinding
import com.yousufsyed.fetchrewards.databinding.RewardsItemBinding
import com.yousufsyed.fetchrewards.inflater
import com.yousufsyed.fetchrewards.network.data.RewardItem
import com.yousufsyed.fetchrewards.view.adapter.RewardsAdapter.RewardsViewHolder

private const val HEADER_TYPE = 0
private const val ITEM_TYPE = 1

class RewardsAdapter : ListAdapter<RewardItem, RewardsViewHolder>(RewardsDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardsViewHolder {
        return if(viewType == ITEM_TYPE) {
            val binding = RewardsItemBinding.inflate(parent.inflater, parent, false)
            ItemViewHolder(binding)
        } else {
            val binding = RewardsHeaderBinding.inflate(parent.inflater, parent, false)
            HeaderViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RewardsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position) is RewardItem.Item) {
            ITEM_TYPE
        } else {
            HEADER_TYPE
        }
    }

    abstract class RewardsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        abstract fun bind(rewardItem: RewardItem)
    }

    class ItemViewHolder(
        private val binding: RewardsItemBinding
    ) : RewardsViewHolder(binding.root) {

        override fun bind(rewardItem: RewardItem) {
            val reward = (rewardItem as RewardItem.Item).reward
            with(binding) {
                rewardName.text = reward.name
            }
        }
    }

    class HeaderViewHolder(
        private val binding: RewardsHeaderBinding
    ) : RewardsViewHolder(binding.header) {

        override fun bind(rewardItem: RewardItem) {
            binding.header.text = (rewardItem as RewardItem.Header).rewardId.toString()
        }
    }
}

class RewardsDiffUtils : DiffUtil.ItemCallback<RewardItem>() {
    override fun areItemsTheSame(oldItem: RewardItem, newItem: RewardItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: RewardItem, newItem: RewardItem): Boolean =
        oldItem == newItem
}