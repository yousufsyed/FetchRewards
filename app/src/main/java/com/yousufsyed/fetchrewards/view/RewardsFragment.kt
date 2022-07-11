package com.yousufsyed.fetchrewards.view

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yousufsyed.fetchrewards.R
import com.yousufsyed.fetchrewards.component
import com.yousufsyed.fetchrewards.databinding.FragmentRewardsBinding
import com.yousufsyed.fetchrewards.network.data.RewardItem
import com.yousufsyed.fetchrewards.network.data.RewardsResults
import com.yousufsyed.fetchrewards.provider.NetworkConnectivityError
import com.yousufsyed.fetchrewards.view.adapter.RewardsAdapter
import com.yousufsyed.fetchrewards.viewmodel.RewardsViewModel
import com.yousufsyed.fetchrewards.viewmodel.RewardsViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RewardsFragment : Fragment(R.layout.fragment_rewards) {

    private var _binding: FragmentRewardsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val rewardsAdapter: RewardsAdapter by lazy {
        RewardsAdapter()
    }

    @Inject
    lateinit var rewardsViewModelFactory: RewardsViewModelFactory

    private val rewardsViewModel: RewardsViewModel by activityViewModels {
        rewardsViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRewardsBinding.bind(requireView())
        initViews()
        initObservers()
    }

    private fun initViews() {
        with(binding) {
            with(rewardsListRv) {
                adapter = rewardsAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
            }
        }
        resetViews()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                rewardsViewModel.rewardsLivedata.observe(viewLifecycleOwner) { state ->
                    resetViews()
                    when (state) {
                        is RewardsResults.Success -> showRewardsList(state.results)
                        is RewardsResults.Error -> showError(state.error)
                        is RewardsResults.Loading -> showLoading()
                        is RewardsResults.EmptyList -> showEmptyList()
                    }
                }
            }
        }
    }


    private fun showEmptyList() {
        binding.emptyStateTv.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(error: Throwable) {
        val message = when (error) {
            is NetworkConnectivityError -> getString(R.string.network_error_message)
            // TODO: Show Error messages based on Exceptions
            else -> getString(R.string.generic_error_message)
        }

        with(binding) {
            errorContainer.visibility = View.VISIBLE
            errorTv.text = message

            retry.setOnClickListener {
                rewardsViewModel.getRewards()
            }
        }
    }

    private fun showRewardsList(results: List<RewardItem>) {
        with(binding) {
            rewardsContainer.visibility = View.VISIBLE
            rewardsAdapter.submitList(results)
        }
    }

    private fun resetViews() {
        with(binding) {
            progressBar.visibility = View.GONE
            rewardsContainer.visibility = View.GONE
            errorContainer.visibility = View.GONE
            emptyStateTv.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}