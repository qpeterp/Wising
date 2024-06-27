package com.qpeterp.wising.ui.bottom.widget

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WidgetViewModel : ViewModel() {
    private val _backgroundColor = MutableLiveData<Int>().apply {
        value = Color.parseColor("#FFE0DAFF")
    }
    val backgroundColor: LiveData<Int> get() = _backgroundColor

    fun setBackgroundColor(color: Int) {
        _backgroundColor.value = color
    }

    private val _textColor = MutableLiveData<Int>().apply {
        value = Color.parseColor("#000000")
    }
    val textColor: LiveData<Int> get() = _textColor

    fun setTextColor(color: Int) {
        _textColor.value = color
    }
}
