package com.ponomarenko.qrreader.details.fragments.url

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
interface UrlPresenter {
    fun bind(view: UrlView?)
    fun unbind()
    fun parseBarcode()
    fun getBarcode(): Barcode
    fun onShareBtnPressed()
    fun onBrowserBtnPressed()
    fun checkAvailableButtons(url : String)
}