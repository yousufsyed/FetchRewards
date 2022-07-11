package com.yousufsyed.fetchrewards.provider

import com.yousufsyed.fetchrewards.network.data.Reward
import com.yousufsyed.fetchrewards.network.data.RewardItem
import javax.inject.Inject

interface RewardsConverter<T> {
    suspend fun convert(rewardsList: List<Reward>) : List<T>
}

class DefaultRewardsConverter @Inject constructor() : RewardsConverter<RewardItem> {

    override suspend fun convert(rewardsList: List<Reward>) : List<RewardItem> {

        val rewardsMap = rewardsList
            .filter { !it.name.isNullOrEmpty() }
            .groupBy { it.listId ?: 0  }

        val sortedMap = LinkedHashMap<Int, List<Reward>>()
        rewardsMap.keys
            .sortedBy { it }
            .forEach { key ->
                sortedMap[key] = rewardsMap[key] ?: emptyList()
            }
        return convertToRewardItemList(sortedMap)
    }

    private fun convertToRewardItemList(rewardsMap: Map<Int, List<Reward>>) : List<RewardItem> {
        return mutableListOf<RewardItem>().apply {
            rewardsMap.forEach { entry ->
                add(RewardItem.Header(entry.key))
                entry.value
                    .sortedBy { it.id }
                    .forEach { reward -> add(RewardItem.Item(reward)) }
            }
        }
    }

}