package com.yousufsyed.fetchrewards.viewmodel

import androidx.lifecycle.*
import com.yousufsyed.fetchrewards.network.data.RewardsResults
import com.yousufsyed.fetchrewards.provider.EventLogger
import com.yousufsyed.fetchrewards.provider.RewardsProvider
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

class RewardsViewModel @Inject constructor(
    private val rewardsProvider: RewardsProvider,
    eventLogger: EventLogger
) : ViewModel(), EventLogger by eventLogger {

    private var _rewardsLiveData = MutableLiveData<RewardsResults>()

    init {
        getRewards()
    }

    val rewardsLivedata: LiveData<RewardsResults> = _rewardsLiveData

    fun getRewards() {
        viewModelScope.launch {
            _rewardsLiveData.postValue(RewardsResults.Loading)
            val result = fetchRewards()
            _rewardsLiveData.postValue(result)
        }
    }

    private suspend fun fetchRewards() = try {
        val rewards = rewardsProvider.getRewards()
        if(rewards.isEmpty()) {
            RewardsResults.EmptyList
        } else {
            RewardsResults.Success(rewards)
        }
    } catch (exception: Exception) {
        logError(exception)
        RewardsResults.Error(exception)
    }

}

class RewardsViewModelFactory @Inject constructor(
    private val rewardsViewModel: RewardsViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RewardsViewModel::class.java)) {
            return rewardsViewModel as T
        }
        throw IllegalArgumentException("View-model is not an instance of RewardsViewModel")
    }
}