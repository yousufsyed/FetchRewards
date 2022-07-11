package com.yousufsyed.fetchrewards

import android.app.Application
import com.yousufsyed.fetchrewards.di.AppComponent
import com.yousufsyed.fetchrewards.di.DaggerAppComponent

class FetchRewardsApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

}