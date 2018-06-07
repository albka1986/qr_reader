package com.ponomarenko.qrreader.details.fragments.contact

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
interface ContactPresenter {
    fun parseBarcode()
    fun bind(contactView: ContactView)
    fun unbind()
    fun onCallBtnPressed(phone: String)
    fun onBrowserBtnPressed()
    fun onAddContactBtnPressed()
    fun onMapBtnPressed()
    fun onShareBtnPressed()
    fun getBarcode(): Barcode
}