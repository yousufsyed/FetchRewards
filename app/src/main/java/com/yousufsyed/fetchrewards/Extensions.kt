package com.yousufsyed.fetchrewards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yousufsyed.fetchrewards.di.AppComponent

// Inflater Extension
val ViewGroup.inflater: LayoutInflater
    get() = LayoutInflater.from(context)

// App component Extension
val Fragment.component : AppComponent
    get() = run {
        (requireActivity().application as? FetchRewardsApplication)?.appComponent
            ?: throw AppComponentNotFoundException()
    }

class AppComponentNotFoundException : RuntimeException()