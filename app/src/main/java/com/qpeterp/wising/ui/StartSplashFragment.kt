package com.qpeterp.wising.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qpeterp.wising.R
import com.qpeterp.wising.databinding.FragmentStartSplashBinding
import com.qpeterp.wising.utils.shortToast

class StartSplashFragment : Fragment() {
    private val binding by lazy { FragmentStartSplashBinding.inflate(layoutInflater) }
    private val CAMERA_PERMISSION_REQUEST_CODE = 122
    private val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 121

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requestCameraPermission()
        requestGalleryPermission()

        return binding.root
    }

    private fun requestCameraPermission() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
    }

    private fun requestGalleryPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE)
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shortToast("카메라 권한 승인")
                } else {
                    // 권한이 거부된 경우 사용자에게 설명이나 추가 도움말을 제공할 수 있습니다.
                    showDialog(requireContext(), 0)
                }
            }

            READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shortToast("갤러리 권한 승인")
                    findNavController().navigate(R.id.action_startSplashFragment_to_mainFragment)
                } else {
                    // 권한이 거부된 경우 사용자에게 설명이나 추가 도움말을 제공할 수 있습니다.
                    showDialog(requireContext(), 1)
                }
            }
        }
    }

    private fun showDialog(context: Context, flags: Int) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
        builder.setTitle("권한 거부")
        builder.setMessage("권한 요청 거부 시, 위젯의 배경 추가 기능을 이용하실 수 없습니다.")
        builder.setPositiveButton("권한 요청") { dialog, _ ->
            if (flags == 0) {
                requestCameraPermission()
            } else {
                requestGalleryPermission()
            }
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.create().show()
    }
}