package com.qpeterp.wising.ui.main.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.util.Log
import android.widget.RemoteViews
import com.qpeterp.wising.R
import com.qpeterp.wising.common.Constant

class WisingWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {}

    override fun onDisabled(context: Context) {}
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val widgetText = sharedPreferences.getString("widgetText", "명언을 만들어주세요!")
    val widgetTextColor = sharedPreferences.getInt("widgetTextColor", Color.BLACK)
    val widgetBackgroundColor = sharedPreferences.getInt("widgetBackgroundColor", Color.parseColor("#FFFFFFFF"))
    val encodedImage = sharedPreferences.getString("widgetImage", null)
    val alphaValue = sharedPreferences.getFloat("widgetImageAlpha", 1.0F)

    val views = RemoteViews(context.packageName, R.layout.wising_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    views.setTextColor(R.id.appwidget_text, widgetTextColor)
    views.setInt(R.id.widgetLayout, "setBackgroundColor", widgetBackgroundColor)

    /**
     * 배경 이미지를 삭제한 경우, encodedImage 에 null 값이 들어온다.
     * */
    if (encodedImage != null) {
        // 최적화된 비트맵 사용
        val bitmap = decodeBase64ToBitmapWithAlpha(encodedImage, alphaValue)
        val resizedBitmap = bitmap?.let { resizeBitmapToFitWidget(context, it) }
        views.setImageViewBitmap(R.id.appwidget_image, resizedBitmap)
    } else {
        views.setImageViewBitmap(R.id.appwidget_image, null)
    }

    appWidgetManager.updateAppWidget(appWidgetId, views)
}

// 위젯 크기에 맞춰 비트맵 크기를 조정하는 함수
private fun resizeBitmapToFitWidget(context: Context, bitmap: Bitmap): Bitmap {
    // 위젯의 크기를 가져오기
    val displayMetrics = context.resources.displayMetrics
    val widgetWidth = displayMetrics.widthPixels / 4  // 적당한 배율로 조정
    val widgetHeight = displayMetrics.heightPixels / 8

    return Bitmap.createScaledBitmap(bitmap, widgetWidth, widgetHeight, true)
}

private fun decodeBase64ToBitmapWithAlpha(encodedImage: String, alpha: Float): Bitmap? {
    return try {
        val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

        // Create a mutable copy of the bitmap to apply alpha
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        // Iterate through each pixel to apply alpha
        for (x in 0 until mutableBitmap.width) {
            for (y in 0 until mutableBitmap.height) {
                val color = mutableBitmap.getPixel(x, y)
                val newColor = Color.argb((alpha * 255).toInt(), Color.red(color), Color.green(color), Color.blue(color))
                mutableBitmap.setPixel(x, y, newColor)
            }
        }

        mutableBitmap
    } catch (e: Exception) {
        Log.e(Constant.TAG, "Failed to decode Base64 to Bitmap with alpha", e)
        null
    }
}