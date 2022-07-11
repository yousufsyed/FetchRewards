package com.yousufsyed.fetchrewards.network.data

sealed class RewardsResults {
    data class Error(val error: Throwable) : RewardsResults()
    data class Success(val results: List<RewardItem>) : RewardsResults()
    object EmptyList: RewardsResults()
    object Loading : RewardsResults()
}

class Reward(
    val id: Int?,
    val listId: Int?,
    val name: String?
)

sealed class RewardItem {
    data class Header(val rewardId: Int) : RewardItem()
    data class Item(val reward: Reward) : RewardItem()
}