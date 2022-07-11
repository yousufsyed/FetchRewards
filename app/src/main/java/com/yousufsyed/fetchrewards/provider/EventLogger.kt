package com.yousufsyed.fetchrewards.provider

import android.util.Log
import javax.inject.Inject

private const val TAG = "RewardsProvider"

interface EventLogger {
    fun logError(error: Throwable)

    fun logMessage(message: String)
}

class DefaultEventLogger @Inject constructor() : EventLogger {
    override fun logError(error: Throwable) {
        Log.e(TAG, error.message ?: "Unknown error occurred")
    }

    override fun logMessage(message: String) {
        Log.e(TAG, message)
    }
}