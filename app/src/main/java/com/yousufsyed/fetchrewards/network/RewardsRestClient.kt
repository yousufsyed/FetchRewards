package com.yousufsyed.fetchrewards.network

import com.yousufsyed.fetchrewards.network.data.Reward
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject

interface RewardsRestClient {
    suspend fun getRewards(): List<Reward>
}

class DefaultRewardsRestClient @Inject constructor(
    private val rewardsApi: RewardsApi
) : RewardsRestClient {

    override suspend fun getRewards(): List<Reward> =
        rewardsApi.getRewards()
}