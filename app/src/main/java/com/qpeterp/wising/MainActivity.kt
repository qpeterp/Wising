package com.qpeterp.wising

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var androidId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDeviceIdentifier()

        setContentView(binding.root)
    }

    @SuppressLint("HardwareIds")
    private fun getDeviceIdentifier() {
        androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        handleStart()
    }

    private fun handleStart() {
        if (androidId == null) {
            getDeviceIdentifier()
            return
        }
        Log.d(Constant.TAG, "MainActivity handleStart androidId: $androidId")
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("androidId", androidId)
        editor.apply() // 데이터를 비동기적으로 저장

        // 저장된 pref 값 호출 코드
//        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
//        Log.d(Constant.TAG, "PermissionCheckFragment androidId : ${sharedPreferences.getString("androidId", "")}")
    }
}