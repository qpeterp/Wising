package com.qpeterp.wising.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.qpeterp.wising.R
import com.qpeterp.wising.databinding.FragmentStartSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartSplashFragment : Fragment() {
    private val binding by lazy { FragmentStartSplashBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 비동기 작업을 수행할 때, 코루틴을 시작할 때 사용됨
        lifecycleScope.launch {
            delay(1500)

            findNavController().navigate(R.id.action_startSplashFragment_to_mainFragment)
        }

        return binding.root
    }
}