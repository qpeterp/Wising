package com.qpeterp.wising.ui.main.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.qpeterp.wising.R
import com.qpeterp.wising.databinding.FragmentHomeBinding
import com.qpeterp.wising.ui.main.qoutes.BookmarkManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bookmarkManager: BookmarkManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initView()
        checkDayChange()

        return binding.root
    }

    private fun initView() {
        sharedPreferences =
            requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        bookmarkManager = BookmarkManager(sharedPreferences.getString("androidId", "").toString())

        homeViewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory(sharedPreferences)).get(HomeViewModel::class.java)

        binding.copyToday.setOnClickListener { copy() }
        binding.shareToday.setOnClickListener { share() }

        binding.bookMarkToday.setOnClickListener {
            val quoteId = sharedPreferences.getString("quoteId", "").toString()
            handleBookMark(quoteId = quoteId)
        }
    }

    private fun checkDayChange() {
        val toDayDate = sharedPreferences.getString("todayDate", "0000-00-00").toString()
        if (getTodayDate() == toDayDate) {
            bookmarkManager.getQuote(
                sharedPreferences.getString("quoteId", "SDozgEIhIQeXfl5723s3").toString()
            ) { result ->
                binding.toDayWisingContent.text = result.quote
                binding.toDayWisingAuthor.text = result.name
            }
            return
        }

        homeViewModel.setTodayQuote()
        val editor = sharedPreferences.edit()
        editor.putString("todayDate", getTodayDate())
        editor.apply()
    }

    private fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun share() {
        val shareText = "${binding.toDayWisingContent.text} - ${binding.toDayWisingAuthor.text}"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        val chooser = Intent.createChooser(shareIntent, "공유하기")
        binding.shareToday.context.startActivity(chooser)
    }

    private fun copy() {
        val clipboardManager =
            binding.copyToday.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clipData = ClipData.newPlainText("WiSing", binding.toDayWisingContent.text)
        clipboardManager?.setPrimaryClip(clipData)
    }

    private fun handleBookMark(quoteId: String) {
        homeViewModel.toggleBookMark()
        val icon = if (homeViewModel.todayQuoteBookMarkState.value == true) R.drawable.ic_check_ok else R.drawable.ic_check_not
        binding.bookMarkToday.setImageDrawable(ContextCompat.getDrawable(requireContext(), icon))
        homeViewModel.handleBookMark(quoteId)
    }
}