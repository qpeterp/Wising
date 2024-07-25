package com.qpeterp.wising.ui.bottom.widget

import android.app.Dialog
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.qpeterp.wising.R
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.databinding.FragmentWidgetBinding

class WidgetFragment : Fragment() {
    private val binding by lazy { FragmentWidgetBinding.inflate(layoutInflater) }
    private val viewModel: WidgetViewModel by lazy { ViewModelProvider(requireActivity()).get(WidgetViewModel::class.java) }
    companion object {
        var flags = 0
        var wising = "명언을 만들어 주세요!"
        var textColor = Color.BLACK
        var backgroundColor = Color.parseColor("#FFE0DAFF")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            loadPreferences()
            initView()
            observeViewModel()
            applyWidget()
        } catch (e: Exception) {
            Log.e(Constant.TAG, "WidgetFragment Error in onCreateView", e)
        }
        return binding.root
    }

    private fun loadPreferences() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val backgroundColor = sharedPreferences.getInt("widgetBackgroundColor", Color.parseColor("#FFE0DAFF"))
        val textColor = sharedPreferences.getInt("widgetTextColor", Color.parseColor("#000000"))
        val text = sharedPreferences.getString("widgetText", "명언을 만들어주세요!")

        viewModel.setBackgroundColor(backgroundColor)
        viewModel.setTextColor(textColor)
        if (text != null) {
            viewModel.setText(text)
        }
    }

    private fun initView() {
        binding.backgroundColorBox.setOnClickListener {
            try {
                flags = 0
                findNavController().navigate(R.id.action_widgetFragment_to_widgetDialogFragment)
            }
            catch (e:Exception) {
                Log.e("WidgetFragment SSapBug", "bug : ",e)
            }
        }

        binding.textColorBox.setOnClickListener {
            try {
                flags = 1
                findNavController().navigate(R.id.action_widgetFragment_to_widgetDialogFragment)
            }
            catch (e:Exception) {
                Log.e("WidgetFragment SSapBug", "bug : ",e)
            }
        }

        binding.widgetText.setOnClickListener {
            changeText()
        }
    }

    private fun observeViewModel() {
        viewModel.backgroundColor.observe(viewLifecycleOwner) { color ->
            binding.widgetTextBackground.setBackgroundColor(color)
        }

        viewModel.textColor.observe(viewLifecycleOwner) { color ->
            binding.widgetText.setTextColor(color)
        }
    }

    private fun applyWidget() {
        binding.buttonDecideWidget.setOnClickListener {
            val intent = Intent(context, WisingWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

            val widgetManager = AppWidgetManager.getInstance(context)
            val widgetIds = widgetManager.getAppWidgetIds(ComponentName(requireContext(), WisingWidget::class.java))

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds)
            wising = binding.widgetText.text.toString()
            textColor = binding.widgetText.currentTextColor
            val backgroundDrawable = binding.widgetTextBackground.background
            backgroundColor = if (backgroundDrawable is ColorDrawable) {
                backgroundDrawable.color
            } else {
                Color.TRANSPARENT
            }

            val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("widgetText", wising)
            editor.apply() // 데이터를 비동기적으로 저장

            context?.sendBroadcast(intent)

            Toast.makeText(activity, "적용되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeText() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_widget_text)

        val buttonClose = dialog.findViewById<TextView>(R.id.widgetEditTextDecideButton)
        val textContent = dialog.findViewById<TextView>(R.id.widgetEditText)

        buttonClose.setOnClickListener {
            binding.widgetText.text = textContent.text
            dialog.dismiss()
        }

        textContent.text = binding.widgetText.text
        dialog.show()
    }
}