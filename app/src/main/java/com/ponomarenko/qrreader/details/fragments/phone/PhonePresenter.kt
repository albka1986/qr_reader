package com.ponomarenko.qrreader.details.fragments.phone

import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.details.fragments.contact.ContactView

/**
 * Created by Ponomarenko Oleh on 12.06.2018.
 */
interface PhonePresenter {
    fun bind(view: PhoneView?)
    fun unbind()
    fun parseBarcode()
    fun onCallBtnPressed()
    fun onAddContactBtnPressed()
    fun getBarcode(): Barcode
    fun onShareBtnPressed()
}