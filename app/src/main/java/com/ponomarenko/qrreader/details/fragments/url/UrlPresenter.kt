package com.ponomarenko.qrreader.details.fragments.url

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
interface UrlPresenter {
    fun setData(barcode: Barcode)
    fun bind(urlView: UrlView)
    fun unbind()
}