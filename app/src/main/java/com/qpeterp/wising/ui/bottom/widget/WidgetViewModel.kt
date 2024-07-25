package com.qpeterp.wising.ui.bottom.widget

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WidgetViewModel(application: Application) : AndroidViewModel(application) {
    private val _backgroundColor = MutableLiveData<Int>().apply {
        value = Color.parseColor("#FFE0DAFF")
    }
    val backgroundColor: LiveData<Int> get() = _backgroundColor

    fun setBackgroundColor(color: Int) {
        _backgroundColor.value = color
        saveColorToPreferences("widgetBackgroundColor", color)
    }

    private val _textColor = MutableLiveData<Int>().apply {
        value = Color.parseColor("#000000")
    }
    val textColor: LiveData<Int> get() = _textColor

    fun setTextColor(color: Int) {
        _textColor.value = color
        saveColorToPreferences("widgetTextColor", color)
    }


    private val _text = MutableLiveData<String>().apply {
        value = "명언을 만들어 주세요"
    }
    val text: LiveData<String> get() = _text

    fun setText(text: String) {
        _text.value = text
        saveTextToPreferences(text)
    }


    private fun saveColorToPreferences(key: String, color: Int) {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key, color)
        editor.apply()
    }

    private fun saveTextToPreferences(text: String) {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("widgetText", text)
        editor.apply()
    }
}