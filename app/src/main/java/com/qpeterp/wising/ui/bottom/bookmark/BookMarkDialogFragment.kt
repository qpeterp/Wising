package com.qpeterp.wising.ui.bottom.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.qpeterp.wising.databinding.DialogBookMarkDetailBinding

class BookMarkDialogFragment: DialogFragment() {
    private val binding by lazy { DialogBookMarkDetailBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        initView()
        return binding.root
    }

    private fun initView() {

    }
}

