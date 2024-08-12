package com.qpeterp.wising.ui.bottom.widget

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
    val widgetBackgroundColor = sharedPreferences.getInt("widgetBackgroundColor", Color.parseColor("#FFE0DAFF"))
    val encodedImage = sharedPreferences.getString("widgetImage", null)
    val alphaValue = sharedPreferences.getFloat("widgetImageAlpha", 1.0F)

    val views = RemoteViews(context.packageName, R.layout.wising_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    views.setTextColor(R.id.appwidget_text, widgetTextColor)
    views.setInt(R.id.widgetLayout, "setBackgroundColor", widgetBackgroundColor)

    if (encodedImage != null) {
        views.setImageViewBitmap(R.id.appwidget_image, decodeBase64ToBitmapWithAlpha(encodedImage.toString(), alphaValue))
    } else {
        views.setImageViewBitmap(R.id.appwidget_image, null)
    }

    appWidgetManager.updateAppWidget(appWidgetId, views)
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