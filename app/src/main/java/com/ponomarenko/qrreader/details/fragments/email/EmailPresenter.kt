package com.ponomarenko.qrreader.details.fragments.email

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 12.06.2018.
 */
interface EmailPresenter {
    fun bind(view: EmailView?)
    fun unbind()
    fun parseBarcode()
    fun getBarcode(): Barcode
    fun onShareBtnPressed()
    fun checkAvailableButtons(content: String?)
}