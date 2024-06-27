package com.qpeterp.wising.ui.bottom.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.qpeterp.wising.R

class WisingWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,

    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

private val sharedViewModel = WidgetViewModel()


internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
) {
    val widgetText = WidgetFragment.wising
    val widgetTextColor = WidgetFragment.textColor
    val widgetBackgroundColor = WidgetFragment.backgroundColor

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.wising_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    views.setTextColor(R.id.appwidget_text, widgetTextColor)
    views.setInt(R.id.widgetLayout, "setBackgroundColor", widgetBackgroundColor)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}