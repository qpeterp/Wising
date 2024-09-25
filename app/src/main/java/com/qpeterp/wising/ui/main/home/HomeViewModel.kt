package com.qpeterp.wising.ui.main.home

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qpeterp.wising.ui.main.qoutes.BookmarkManager

class HomeViewModel(
    private val sharedPreferences: SharedPreferences
): ViewModel() {
    private var bookmarkManager: BookmarkManager =
        BookmarkManager(sharedPreferences.getString("androidId", "").toString())
    private var _todayQuote = MutableLiveData<List<String>>()

    val todayQuote: LiveData<List<String>>
        get() = _todayQuote

    /*
    * _todayQuote index 0 <= quote
    * _todayQuote index 1 <= name
    * */
    fun setTodayQuote() {
        bookmarkManager.getRandomQuote { bookMarkData ->
            _todayQuote.value = listOf(bookMarkData.quote, bookMarkData.name)

            val editor = sharedPreferences.edit()
            editor.putString("quoteId", bookMarkData.id)
            editor.apply()
        }
    }
}