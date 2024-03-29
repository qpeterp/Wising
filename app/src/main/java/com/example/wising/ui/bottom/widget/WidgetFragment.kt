package com.example.wising.ui.bottom.widget

import android.app.Dialog
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
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
import com.example.wising.R
import com.example.wising.databinding.FragmentWidgetBinding


class WidgetFragment : Fragment() {
    private val binding by lazy { FragmentWidgetBinding.inflate(layoutInflater) }
    private val viewModel: WidgetViewModel by lazy { ViewModelProvider(requireActivity()).get(WidgetViewModel::class.java) }
    companion object {
        var flags = 0
        var wising = "명언을 만들어 주세요!"
        var textColor = 0
        var backgroundColor = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        initView()
        observeViewModel()
        applyWidget()

        binding.widgetText.setOnClickListener {
            changeText()
        }

        return binding.root
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
    }


    private fun observeViewModel() {
        viewModel.backgroundColor.observe(viewLifecycleOwner) { color ->
            binding.widgetTextBackground.setBackgroundColor(color)
        }

        viewModel.textColor.observe(viewLifecycleOwner) { color ->
            binding.widgetText.setTextColor(color)
        }
    }


    fun applyWidget() {
        binding.buttonDecideWidget.setOnClickListener {
            // 위젯을 업데이트하는 인텐트 생성
            val intent = Intent(context, WisingWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

            // 모든 위젯의 ID 가져오기
            val widgetManager = AppWidgetManager.getInstance(context)
            val widgetIds = widgetManager.getAppWidgetIds(ComponentName(requireContext(), WisingWidget::class.java))

            // 업데이트를 요청할 위젯 ID 배열 추가
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds)
            wising = binding.widgetText.text.toString()
            textColor = binding.widgetText.currentTextColor
            val backgroundDrawable = binding.widgetTextBackground.background
            backgroundColor = if (backgroundDrawable is ColorDrawable) {
                backgroundDrawable.color
            } else {
                // 배경이 색상이 아닌 다른 형태일 경우 기본값 설정
                Color.TRANSPARENT
            }

            // DB에다가 위젯 정보 저장하기

            // 업데이트 실행
            context?.sendBroadcast(intent)

            Toast.makeText(activity, "적용되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeText() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_widget_text)


        // Dialog 내부의 View를 참조합니다.
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