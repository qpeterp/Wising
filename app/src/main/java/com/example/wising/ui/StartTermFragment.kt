package com.example.wising.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.wising.R
import com.example.wising.databinding.FragmentStartSplashBinding
import com.example.wising.databinding.FragmentStartTermBinding

class StartTermFragment : Fragment() {
    private val binding by lazy { FragmentStartTermBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding.buttonStartTerm.setOnClickListener {
            findNavController().navigate(R.id.action_startTermFragment_to_checkPermissionFragment)
        }

        return binding.root
//        return inflater.inflate(R.layout.fragment_start_term, container, false)
    }
}