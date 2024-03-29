package com.example.wising.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.wising.R
import com.example.wising.databinding.FragmentStartSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartSplashFragment : Fragment() {
    private val binding by lazy { FragmentStartSplashBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        // 비동기 작업을 수행할 때, 코루틴을 시작할 때 사용됨
        lifecycleScope.launch {
            delay(1500)

            Log.d("StartSplashFragment", "delay 1000 play")

            findNavController().navigate(R.id.action_startSplashFragment_to_startTermFragment)
        }


        return binding.root
//        return inflater.inflate(R.layout.fragment_start_splash, container, false)
    }
}