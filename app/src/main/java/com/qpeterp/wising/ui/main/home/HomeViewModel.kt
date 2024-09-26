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
    private var _todayQuoteBookMarkState = MutableLiveData(false)

    val todayQuote: LiveData<List<String>>
        get() = _todayQuote
    val todayQuoteBookMarkState: LiveData<Boolean>
        get() = _todayQuoteBookMarkState

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

    fun toggleBookMark() {
        _todayQuoteBookMarkState.value = _todayQuoteBookMarkState.value!!.not()
    }

    fun handleBookMark(quoteId: String) {
        if (_todayQuoteBookMarkState.value == true) {
            bookmarkManager.addBookmark(quoteId)
        } else {
            bookmarkManager.removeBookmark(quoteId)
        }
    }
}