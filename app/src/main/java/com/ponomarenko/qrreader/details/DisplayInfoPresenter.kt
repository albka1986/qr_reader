package com.ponomarenko.qrreader.details

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
interface DisplayInfoPresenter {
    fun bind(displayInfoView: DisplayInfoView)
    fun detectFragment(barcode: Barcode?)
}