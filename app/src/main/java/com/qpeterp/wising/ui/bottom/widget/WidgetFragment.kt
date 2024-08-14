package com.qpeterp.wising.ui.bottom.widget

import android.app.Activity
import android.app.Dialog
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.qpeterp.wising.R
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.databinding.FragmentWidgetBinding
import java.io.ByteArrayOutputStream

class WidgetFragment : Fragment() {
    private val binding by lazy { FragmentWidgetBinding.inflate(layoutInflater) }
    private val viewModel: WidgetViewModel by lazy { ViewModelProvider(requireActivity()).get(WidgetViewModel::class.java) }
    companion object {
        var flags = 0
        var wising = "명언을 만들어 주세요!"
        var textColor = Color.BLACK
        var backgroundColor = Color.parseColor("#FFE0DAFF")
    }
    private val REQ_GALLERY = 124
    private val REQ_CAMERA = 123 // 어떤 고유한 정수 값이든 사용 가능

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
        val encodedImage = sharedPreferences.getString("widgetImage", null)
        val alphaValue = sharedPreferences.getFloat("widgetImageAlpha", 1.0F)

        viewModel.setBackgroundColor(backgroundColor)
        viewModel.setTextColor(textColor)
        viewModel.setBackgroundImageAlpha(alphaValue)

        val image: Bitmap? = decodeBase64ToBitmap(encodedImage)
        if (image != null) {
            viewModel.setBackgroundImage(image)
        } else {
            Log.e(Constant.TAG, "loadPreferences: Failed to decode image")
        }

        if (text != null) {
            viewModel.setText(text)
            Log.d(Constant.TAG, "WidgetFragment loadPreferences text is $text")
        } else {
            Log.d(Constant.TAG, "WidgetFragment loadPreferences text is null")
        }
    }

    private fun initView() {
        binding.backgroundColorBox.setOnClickListener {
            flags = 0
            findNavController().navigate(R.id.action_widgetFragment_to_widgetDialogFragment)
        }

        binding.textColorBox.setOnClickListener {
            flags = 1
            findNavController().navigate(R.id.action_widgetFragment_to_widgetDialogFragment)
        }

        binding.widgetText.setOnClickListener { changeText() }

        binding.wisingCameraBox.setOnClickListener { openCamera() }

        binding.wisingGalleryBox.setOnClickListener { openGallery() }

        binding.wisingAlphaBox.setOnClickListener { setImageAlpha() }

        binding.colorReset.setOnClickListener { resetWidgetColor() }

        binding.imageReset.setOnClickListener { resetWidgetImage() }
    }

    private fun observeViewModel() {
        viewModel.backgroundColor.observe(viewLifecycleOwner) { color ->
            binding.widgetTextBackground.setBackgroundColor(color)
        }

        viewModel.textColor.observe(viewLifecycleOwner) { color ->
            binding.widgetText.setTextColor(color)
        }

        viewModel.backgroundImage.observe(viewLifecycleOwner) { image ->
            binding.widgetImage.setImageBitmap(image)
        }

        viewModel.text.observe(viewLifecycleOwner) { text ->
            binding.widgetText.text = text
        }

        viewModel.backgroundImageAlpha.observe(viewLifecycleOwner) { alpha ->
            binding.widgetImage.alpha = alpha
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

            // 텍스트, 색상 정보 저장
            editor.putString("widgetText", wising)
            editor.putInt("widgetTextColor", textColor)
            editor.putInt("widgetBackgroundColor", backgroundColor)

            // 이미지가 있는지 확인한 후 Base64로 인코딩하여 저장
            val bitmap = (binding.widgetImage.drawable as? BitmapDrawable)?.bitmap
            if (bitmap != null) {
                val encodedImage = encodeBitmapToBase64(bitmap)
                editor.putString("widgetImage", encodedImage)
            }

            editor.apply() // 데이터를 비동기적으로 저장

            context?.sendBroadcast(intent)

            Toast.makeText(activity, "적용되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeText() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_widget_text)

        val buttonClose = dialog.findViewById<TextView>(R.id.widgetEditTextDecideButton)
        val textContent = dialog.findViewById<TextView>(R.id.widgetEditText)

        buttonClose.setOnClickListener {
            binding.widgetText.text = textContent.text
            editor.putString("widgetText", textContent.text.toString())
            dialog.dismiss()
        }

        textContent.text = binding.widgetText.text
        dialog.show()
    }

    private fun openCamera() {
        Log.d(Constant.TAG, "WidgetFragment openCamera is run")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)
    }

    private fun openGallery() {
        Log.d(Constant.TAG, "WidgetFragment openGallery is run")
        val intent = Intent(Intent.ACTION_PICK)

        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQ_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data.extras?.get("data") as Bitmap
                        binding.widgetImage.setImageBitmap(bitmap)
                    }
                }

                REQ_GALLERY -> {
                    // 갤러리에서 이미지를 가져오는 부분
                    val selectedImageUri: Uri? = data?.data
                    val selectedImagePath: String? = selectedImageUri?.let { getPathFromUri(it) }

                    if (selectedImagePath != null) {
                        // 선택한 이미지를 비트맵으로 변환하여 이미지뷰에 설정
                        val bitmap = BitmapFactory.decodeFile(selectedImagePath)
                        binding.widgetImage.setImageBitmap(resizeBitmapToSize(bitmap))
                    }
                }
            }
        }
    }

    private fun resizeBitmapToSize(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, 300, 300, true)
    }

    private fun getPathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = requireContext().contentResolver.query(uri, projection, null, null, null)
        return cursor?.use {
            val columnIndex: Int = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            it.getString(columnIndex)
        } ?: ""
    }

    // Bitmap을 Base64로 인코딩하는 메서드
    private fun encodeBitmapToBase64(bitmap: Bitmap): String {
        return try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            byteArrayOutputStream.close()
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e(Constant.TAG, "encodeBitmapToBase64: Failed to encode bitmap", e)
            ""
        }
    }

    // Base64를 Bitmap으로 디코딩하는 메서드
    private fun decodeBase64ToBitmap(encodedImage: String?): Bitmap? {
        if (encodedImage.isNullOrEmpty()) {
            Log.e(Constant.TAG, "decodeBase64ToBitmap: Encoded image is null or empty")
            return null
        }
        return try {
            val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)?.let { bitmap ->
                // Bitmap 압축을 사용하여 메모리 사용량 최적화
                optimizeBitmap(bitmap)
            } ?: run {
                Log.e(Constant.TAG, "decodeBase64ToBitmap: Decoded byte array is null")
                null
            }
        } catch (e: IllegalArgumentException) {
            Log.e(Constant.TAG, "decodeBase64ToBitmap: Invalid Base64 input", e)
            null
        }
    }

    // Bitmap 최적화 메서드
    private fun optimizeBitmap(bitmap: Bitmap): Bitmap {
        return try {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val byteArray = outputStream.toByteArray()
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: Exception) {
            Log.e(Constant.TAG, "optimizeBitmap: Failed to optimize bitmap", e)
            bitmap
        }
    }

    private fun setImageAlpha() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_image_alpha)

        val buttonClose = dialog.findViewById<TextView>(R.id.widgetImageAlphaButton)
        val seekBar = dialog.findViewById<SeekBar>(R.id.widgetImageAlphaSeekbar)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 사용자가 SeekBar를 움직일 때 호출됨
                // progress 값을 사용
                val alphaValue = progress / 100.0f // 0에서 100 범위를 0.0에서 1.0 범위로 변환

                viewModel.setBackgroundImageAlpha(alphaValue)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        buttonClose.setOnClickListener {
            if (viewModel.backgroundImageAlpha.value != null) {
                editor.putFloat("widgetImageAlpha", viewModel.backgroundImageAlpha.value!!)
            } else {
                editor.putFloat("widgetImageAlpha", 1.0F)
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun resetWidgetColor() {
        backgroundColor = Color.WHITE
        textColor = Color.BLACK
        viewModel.setBackgroundColor(backgroundColor) // ViewModel에 배경 색상 변경 알림
        viewModel.setTextColor(textColor) // ViewModel에 배경 색상 변경 알림

        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("widgetTextColor", Color.BLACK)
        editor.putInt("widgetBackgroundColor", Color.WHITE)

        editor.apply()
    }

    private fun resetWidgetImage() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("widgetImage", null)
        editor.putFloat("widgetImageAlpha", 1.0F)
        viewModel.setBackgroundImageAlpha(1.0F)
        binding.widgetImage.setImageBitmap(null)

        editor.apply()

        applyWidget()
    }
}