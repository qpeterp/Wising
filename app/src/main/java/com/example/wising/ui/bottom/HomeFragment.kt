package com.example.wising.ui.bottom

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.wising.R
import com.example.wising.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        initView()

        return binding.root
//        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    private fun initView() {
        binding.copyToday.setOnClickListener {
            val clipboardManager = binding.copyToday.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clipData = ClipData.newPlainText("WiSing", binding.toDayWisingContent.text)
            clipboardManager?.setPrimaryClip(clipData)
        }

        binding.shareToday.setOnClickListener {
            // 공유할 텍스트 생성
            val shareText = "${binding.toDayWisingContent.text} - ${binding.toDayWisingAuthor.text}"
            Log.d("viewHolderPage", shareText)
            // 공유 인텐트 생성
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            // 인텐트 선택기를 통해 공유하기
            val chooser = Intent.createChooser(shareIntent, "공유하기")
            binding.shareToday.context.startActivity(chooser)
        }

    }

}