package com.ponomarenko.qrreader.details.fragments.geo

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 12.06.2018.
 */
interface GeoPresenter {

    fun bind(view: GeoView?)
    fun unbind()
    fun parseBarcode()
    fun onShareBtnPressed()
    fun getBarcode(): Barcode
    fun onMapBtnPressed()

}