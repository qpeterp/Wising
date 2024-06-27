package com.qpeterp.wising.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qpeterp.wising.R
import com.qpeterp.wising.databinding.FragmentPermissionCheckBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PermissionCheckFragment : BottomSheetDialogFragment() {
    private val binding by lazy { FragmentPermissionCheckBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding.buttonToHome.setOnClickListener {
            findNavController().navigate(R.id.action_checkPermissionFragment_to_homeFragment)
        }

        return binding.root
//        return inflater.inflate(R.layout.fragment_permission_check, container, false)
    }

}