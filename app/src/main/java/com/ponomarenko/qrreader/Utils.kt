package com.ponomarenko.qrreader

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by Ponomarenko Oleh on 06.06.2018.
 */

const val PERMISSION_REQUEST_CODE = 201

fun View?.setVisible(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.GONE
}

fun Activity.checkPermissions(array: Array<String>): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        array.filter {
            this.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
        }.takeIf { it.isNotEmpty() }?.let {
            requestPermissions(it.toTypedArray(), PERMISSION_REQUEST_CODE)
            return false
        }
    }
    return true
}


fun Fragment.checkPermissions(array: Array<String>): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        array.filter {
            ActivityCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }.takeIf { it.isNotEmpty() }?.let {
            this.requestPermissions(it.toTypedArray(), PERMISSION_REQUEST_CODE)
            return false
        }
    }
    return true
}