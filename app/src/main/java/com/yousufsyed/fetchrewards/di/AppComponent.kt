package com.yousufsyed.fetchrewards.di

import android.app.Application
import com.yousufsyed.fetchrewards.view.RewardsFragment
import com.yousufsyed.fetchrewards.view.RewardsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun inject(rewardsActivity: RewardsActivity)
    fun inject(rewardsFragment: RewardsFragment)
}