package com.yousufsyed.fetchrewards.network

import com.yousufsyed.fetchrewards.network.data.Reward
import retrofit2.http.GET
import retrofit2.http.Url

private const val REWARDS_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

interface RewardsApi {

    @GET("/hiring.json")
    suspend fun getRewards(): List<Reward>

}