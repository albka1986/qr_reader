package com.ponomarenko.qrreader.details.fragments.general

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
class GeneralPresenterImpl(private val barcode: Barcode) : GeneralPresenter {

    private var generalView: GeneralView? = null

    override fun parseBarcode() {
        generalView?.setData(barcode.displayValue)
    }

    override fun bind(generalView: GeneralView) {
        this.generalView = generalView
    }

    override fun unbind() {
        generalView = null
    }

    override fun onShareBtnPressed() {
        generalView?.shareContent(barcode.displayValue)
    }


}