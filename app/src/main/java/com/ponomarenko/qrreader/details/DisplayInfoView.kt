package com.ponomarenko.qrreader.details

import androidx.fragment.app.Fragment
import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
interface DisplayInfoView {
    fun launchFragment(fragment: Fragment, barcode: Barcode)
}