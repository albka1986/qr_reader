package com.ponomarenko.qrreader.details.fragments.contact

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
interface ContactPresenter {
    fun bind(view: ContactView?)
    fun unbind()
    fun parseBarcode()
    fun onCallBtnPressed(phone: String)
    fun onBrowserBtnPressed()
    fun onAddContactBtnPressed()
    fun onMapBtnPressed()
    fun getBarcode(): Barcode
    fun onShareBtnPressed()
}