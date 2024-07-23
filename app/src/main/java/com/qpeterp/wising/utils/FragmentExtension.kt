package com.qpeterp.wising.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.shortToast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}