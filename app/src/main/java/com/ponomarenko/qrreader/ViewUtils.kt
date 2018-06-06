package com.ponomarenko.qrreader

import android.view.View

/**
 * Created by Ponomarenko Oleh on 06.06.2018.
 */
fun View?.setVisible(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.GONE
}