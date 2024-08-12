package com.qpeterp.wising.ui.bottom.widget

import android.content.Context.MODE_PRIVATE
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.qpeterp.wising.databinding.DialogWidgetBinding
import com.skydoves.colorpickerview.flag.BubbleFlag
import com.skydoves.colorpickerview.flag.FlagMode
import com.skydoves.colorpickerview.listeners.ColorListener
import com.skydoves.colorpickerview.preference.ColorPickerPreferenceManager

class WidgetDialogFragment : DialogFragment() {
    private val binding by lazy { DialogWidgetBinding.inflate(layoutInflater) }
    private val viewModel: WidgetViewModel by lazy { ViewModelProvider(requireActivity()).get(WidgetViewModel::class.java) }
    private var widgetColor = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initView()
        return binding.root
    }

    private fun initView() {
        val manager = ColorPickerPreferenceManager.getInstance(activity)
        manager.setColor("MyColorPicker", widgetColor)
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
            widgetColor = color
        })

        binding.widgetSelectButton.setOnClickListener {
            if (WidgetFragment.flags == 0) {
                viewModel.setBackgroundColor(widgetColor) // ViewModel에 배경 색상 변경 알림
                val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putInt("widgetBackgroundColor", widgetColor)
                editor.apply() // 데이터를 비동기적으로 저장
            } else {
                viewModel.setTextColor(widgetColor) // ViewModel에 글자 색상 변경 알림
                val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putInt("widgetTextColor", widgetColor)
                editor.apply() // 데이터를 비동기적으로 저장
            }
            dismiss()
        }
    }
}
