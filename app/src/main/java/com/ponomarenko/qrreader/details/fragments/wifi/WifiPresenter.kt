package com.ponomarenko.qrreader.details.fragments.wifi

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 08.06.2018.
 */
interface WifiPresenter {
    fun bind(wifiView: WifiView)
    fun unbind()
    fun parseBarcode()
    fun onShareBtnPressed()
    fun onConnectBtnPressed()
    fun getBarcode(): Barcode
}