package com.qpeterp.wising.ui.main.widget

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WidgetViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = getApplication<Application>().getSharedPreferences("user_prefs", MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val _backgroundColor = MutableLiveData<Int>(Color.parseColor("#FFFFFFFF"))
    val backgroundColor: LiveData<Int> get() = _backgroundColor

    fun setBackgroundColor(color: Int) {
        _backgroundColor.value = color
        saveColorToPreferences("widgetBackgroundColor", color)
    }

    private val _textColor = MutableLiveData<Int>(Color.parseColor("#000000"))
    val textColor: LiveData<Int> get() = _textColor

    fun setTextColor(color: Int) {
        _textColor.value = color
        saveColorToPreferences("widgetTextColor", color)
    }


    private val _text = MutableLiveData<String>("명언을 만들어 주세요")
    val text: LiveData<String> get() = _text

    fun setText(text: String) {
        _text.value = text
        saveTextToPreferences(text)
    }

    private val _backgroundImage = MutableLiveData<Bitmap>(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))
    val backgroundImage: LiveData<Bitmap> get() = _backgroundImage

    fun setBackgroundImage(image: Bitmap) {
        _backgroundImage.value = image
        saveImageToPreferences(image.toString())
    }

    private val _backgroundImageAlpha = MutableLiveData<Float>(1.0F)
    val backgroundImageAlpha: LiveData<Float> get() = _backgroundImageAlpha

    fun setBackgroundImageAlpha(alpha: Float) {
        _backgroundImageAlpha.value = alpha
        saveImageAlphaToPreferences(alpha)
    }


    private fun saveColorToPreferences(key: String, color: Int) {
        editor.putInt(key, color)
        editor.apply()
    }

    private fun saveTextToPreferences(text: String) {
        editor.putString("widgetText", text)
        editor.apply()
    }

    private fun saveImageToPreferences(image: String) {
        editor.putString("widgetImage", image)
        editor.apply()
    }

    private fun saveImageAlphaToPreferences(alpha: Float) {
        editor.putFloat("widgetImageAlpha", alpha)
        editor.apply()
    }
}