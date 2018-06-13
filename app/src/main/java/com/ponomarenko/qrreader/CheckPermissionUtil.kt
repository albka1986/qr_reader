package com.ponomarenko.qrreader

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build

/**
 * Created by Ponomarenko Oleh on 13.06.2018.
 */


fun checkPermissions(activity: Activity, array: Array<String>): Boolean {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val requestPermissionList = ArrayList<String>()

        array.forEach {
            if (activity.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionList.add(it)
            }
        }

        return if (requestPermissionList.isEmpty()) {
            true
        } else {
            activity.requestPermissions(array, 201)
            false
        }
    }

    return true
}