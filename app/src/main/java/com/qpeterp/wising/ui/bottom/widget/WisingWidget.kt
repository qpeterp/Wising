package com.qpeterp.wising.ui.bottom.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Color
import android.widget.RemoteViews
import com.qpeterp.wising.R

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
    appWidgetId: Int,
) {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val widgetText = sharedPreferences.getString("widgetText", "명언을 만들어주세요!")
    val widgetTextColor = sharedPreferences.getInt("widgetTextColor", Color.BLACK)
    val widgetBackgroundColor = sharedPreferences.getInt("widgetBackgroundColor", Color.parseColor("#FFE0DAFF"))

    val views = RemoteViews(context.packageName, R.layout.wising_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    views.setTextColor(R.id.appwidget_text, widgetTextColor)
    views.setInt(R.id.widgetLayout, "setBackgroundColor", widgetBackgroundColor)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}
