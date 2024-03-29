package com.example.wising.ui.bottom.widget

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wising.R

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
