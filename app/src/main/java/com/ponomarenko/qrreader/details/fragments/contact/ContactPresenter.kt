package com.ponomarenko.qrreader.details.fragments.contact

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
interface ContactPresenter {
    fun setData(barcode: Barcode)
    fun bind(contactView: ContactView)
    fun unbind()
    fun onCallPressed(contactInfo: Barcode.ContactInfo?)
    fun requestPermissions()
}