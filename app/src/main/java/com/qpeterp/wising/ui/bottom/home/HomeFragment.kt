package com.qpeterp.wising.ui.bottom.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.qpeterp.wising.R
import com.qpeterp.wising.databinding.FragmentHomeBinding
import com.qpeterp.wising.ui.bottom.qoutes.BookmarkManager

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var bookMarkChecker = true
    private lateinit var bookmarkManager: BookmarkManager
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initView()

        return binding.root
    }

    private fun initView() {
        sharedPreferences = requireActivity()
            .getSharedPreferences("user_prefs", MODE_PRIVATE)

        bookmarkManager = BookmarkManager(
            sharedPreferences
                .getString("androidId", "")
                .toString()
        )
        setTodayWising()

        binding.copyToday.setOnClickListener {
            copy()
        }

        binding.shareToday.setOnClickListener {
            share()
        }

        binding.bookMarkToday.setOnClickListener {
            val quoteId = sharedPreferences.getString("quoteId", "").toString()
            handleBookMark(quoteId = quoteId)
        }
    }

    private fun setTodayWising() {
        bookmarkManager.getRandomQuote { bookMarkData ->
            if (bookMarkData != null) {
                binding.toDayWisingContent.text = bookMarkData.quote
                binding.toDayWisingAuthor.text = bookMarkData.name

                val editor = sharedPreferences.edit()
                editor.putString("quoteId", bookMarkData.id)
                editor.apply()
            }
        }
    }

    private fun share() {
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

    private fun copy() {
        val clipboardManager = binding.copyToday.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clipData = ClipData.newPlainText("WiSing", binding.toDayWisingContent.text)
        clipboardManager?.setPrimaryClip(clipData)
    }

    private fun handleBookMark(quoteId: String) {
        bookMarkChecker = if (bookMarkChecker) {
            binding.bookMarkToday.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_check_ok
                )
            )
            bookmarkManager.addBookmark(quoteId)
            false
        } else {
            binding.bookMarkToday.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_check_not
                )
            )
            bookmarkManager.removeBookmark(quoteId)
            true
        }
    }
}