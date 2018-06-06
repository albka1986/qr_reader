package com.ponomarenko.qrreader.details.fragments.contact

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
interface ContactPresenter {
    fun parseBarcode(barcode: Barcode)
    fun bind(contactView: ContactView)
    fun unbind()
    fun onCallBtnPressed(phone:String)
    fun onBrowserBtnPressed(contactInfo: Barcode.ContactInfo?)
    fun onAddContactBtnPressed(contactInfo: Barcode.ContactInfo?)
    fun onMapBtnPressed(contactInfo: Barcode.ContactInfo?)
    fun onShareBtnPressed(barcode: Barcode?)
}