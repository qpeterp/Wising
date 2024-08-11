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
    Log.d(Constant.TAG, "WisingWidget updateAppWidget is run")

    val views = RemoteViews(context.packageName, R.layout.wising_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    views.setTextColor(R.id.appwidget_text, widgetTextColor)
    views.setInt(R.id.widgetLayout, "setBackgroundColor", widgetBackgroundColor)

    if (encodedImage != null) {
        views.setImageViewBitmap(R.id.appwidget_image, decodeBase64ToBitmap(encodedImage.toString()))
    }

    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun decodeBase64ToBitmap(encodedImage: String): Bitmap? {
    return try {
        val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    } catch (e: Exception) {
        Log.e(Constant.TAG, "Failed to decode Base64 to Bitmap", e)
        null
    }
}