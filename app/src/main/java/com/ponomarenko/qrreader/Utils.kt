package com.ponomarenko.qrreader

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.view.View

/**
 * Created by Ponomarenko Oleh on 06.06.2018.
 */

const val PERMISSION_REQUEST_CODE = 201

fun View?.setVisible(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.GONE
}

fun Activity?.checkPermissions(array: Array<String>): Boolean {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val requestPermissionList = ArrayList<String>()

        array.forEach {
            if (this?.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionList.add(it)
            }
        }

        return if (requestPermissionList.isEmpty()) {
            true
        } else {
            this?.requestPermissions(array, PERMISSION_REQUEST_CODE)
            false
        }
    }

    return true
}