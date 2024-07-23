package com.qpeterp.wising.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import com.qpeterp.wising.R

class CustomButtonView(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    init {
        val view =
            LayoutInflater.from(context).inflate(R.layout.custom_view_button, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomButtonView)
            val customTitle = typedArray.getString(R.styleable.CustomButtonView_customTitle)
            val customTextColor = typedArray.getColor(R.styleable.CustomButtonView_customTextColor, Color.WHITE)
            val customBgColor = typedArray.getColor(R.styleable.CustomButtonView_customBgColor, Color.WHITE)
            typedArray.recycle()

            val textView = view.findViewById<TextView>(R.id.customText)
            val layout = view.findViewById<RelativeLayout>(R.id.customLayout)

            textView.apply {
                text = customTitle
                setTextColor(customTextColor)
            }

            layout.backgroundTintList = ColorStateList.valueOf(customBgColor)
        }
    }
}