package com.qpeterp.wising.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qpeterp.wising.R
import com.qpeterp.wising.databinding.FragmentStartSplashBinding

class StartSplashFragment : Fragment() {
    private val binding by lazy { FragmentStartSplashBinding.inflate(layoutInflater) }
    private val CAMERA_PERMISSION_REQUEST_CODE = 122
    private val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 121

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 권한을 체크하고 필요한 경우에만 권한을 요청
        checkPermissions()
        return binding.root
    }

    private fun checkPermissions() {
        val cameraPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        val readStoragePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
        } else if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            requestGalleryPermission()
        } else {
            // 모든 권한이 승인되었을 경우, 다음 화면으로 이동
            findNavController().navigate(R.id.action_startSplashFragment_to_mainFragment)
        }
    }

    private fun requestCameraPermission() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
    }

    private fun requestGalleryPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestGalleryPermission()
                } else {
                    showDialog(requireContext(), 0)
                }
            }
            READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    findNavController().navigate(R.id.action_startSplashFragment_to_mainFragment)
                } else {
                    showDialog(requireContext(), 1)
                }
            }
        }
    }

    private fun showDialog(context: Context, flags: Int) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
        builder.setTitle("권한 거부")
        builder.setMessage("권한 요청 거부 시, 위젯의 배경 추가 기능을 이용하실 수 없습니다.")
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
            activity?.finish()
        }
        builder.setCancelable(false)
        builder.create().show()
    }
}