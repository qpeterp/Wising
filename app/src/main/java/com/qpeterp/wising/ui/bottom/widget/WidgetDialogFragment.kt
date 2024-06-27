package com.qpeterp.wising.ui.bottom.widget

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.qpeterp.wising.databinding.DialogWdigetBinding
import com.skydoves.colorpickerview.flag.BubbleFlag
import com.skydoves.colorpickerview.flag.FlagMode
import com.skydoves.colorpickerview.listeners.ColorListener
import com.skydoves.colorpickerview.preference.ColorPickerPreferenceManager


class WidgetDialogFragment : DialogFragment() {
    private val binding by lazy { DialogWdigetBinding.inflate(layoutInflater) }
    private val viewModel: WidgetViewModel by lazy { ViewModelProvider(requireActivity()).get(WidgetViewModel::class.java) }
    private var widgetBackgroundColor = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        initView()
        return binding.root
    }

    private fun initView() {
        val manager = ColorPickerPreferenceManager.getInstance(activity)
        manager.setColor("MyColorPicker", widgetBackgroundColor)
        manager.setSelectorPosition("MyColorPicker", Point(120, 120))
        manager.clearSavedAllData()
        manager.clearSavedColor("MyColorPicker")
        manager.restoreColorPickerData(binding.colorPickerView)

        binding.colorPickerView.attachAlphaSlider(binding.alphaSlideBar)
        binding.colorPickerView.attachBrightnessSlider(binding.brightnessSlide)

        val bubbleFlag = BubbleFlag(activity)
        bubbleFlag.flagMode = FlagMode.FADE
        binding.colorPickerView.setFlagView(bubbleFlag)

        binding.colorPickerView.setColorListener(ColorListener { color, fromUser ->
            binding.setWidgetColor.setBackgroundColor(color)
            widgetBackgroundColor = color
        })

        binding.widgetSelectButton.setOnClickListener {

            if (WidgetFragment.flags == 0) {
                viewModel.setBackgroundColor(widgetBackgroundColor) // ViewModel에 색상 변경 알림
            }
            else {
                viewModel.setTextColor(widgetBackgroundColor) // ViewModel에 색상 변경 알림
            }

            dismiss()
        }
    }
}
