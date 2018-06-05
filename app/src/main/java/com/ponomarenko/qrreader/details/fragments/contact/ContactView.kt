package com.ponomarenko.qrreader.details.fragments.contact

import android.net.Uri
import android.support.v4.app.Fragment

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
    fun openBrowser(url: Uri)
    fun openGoogleMaps(uri: Uri)
}
