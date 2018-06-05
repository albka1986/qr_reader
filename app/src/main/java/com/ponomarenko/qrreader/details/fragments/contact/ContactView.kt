package com.ponomarenko.qrreader.details.fragments.contact

import android.support.v4.app.Fragment
import com.google.android.gms.vision.barcode.Barcode

interface ContactView {

    fun setData(detailedInfoText: String)
    fun initCall(phone: String)
    fun getViewFragment(): Fragment?
    fun setCallBtnVisible(visible: Boolean)
    fun setBrowserBtnVisible(visible: Boolean)
    fun setMapBtnVisible(visible: Boolean)
    fun setAddContactBtnVisible(visible: Boolean)
    fun setEmailBtnVisible(visible: Boolean)
    fun shareContent(content: String?)
}
