package com.yousufsyed.fetchrewards.provider

import com.yousufsyed.fetchrewards.network.RewardsRestClient
import com.yousufsyed.fetchrewards.network.data.Reward
import com.yousufsyed.fetchrewards.network.data.RewardItem
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RewardsProvider {
    suspend fun getRewards(): List<RewardItem>
}

class DefaultRewardsProvider @Inject constructor(
    private val rewardsRestClient: RewardsRestClient,
    private val dispatcherProvider: DispatcherProvider,
    private val rewardsConverter: RewardsConverter<RewardItem>
) : RewardsProvider {

    private var rewards: List<RewardItem> = emptyList()

    override suspend fun getRewards(): List<RewardItem> {
        return withContext(dispatcherProvider.io) {
            rewards.ifEmpty {
                fetchRewards().apply {
                    rewards = this
                }
            }
        }
    }

    private suspend fun fetchRewards(): List<RewardItem> {
        val results = rewardsRestClient.getRewards()
        return rewardsConverter.convert(results)
    }
}