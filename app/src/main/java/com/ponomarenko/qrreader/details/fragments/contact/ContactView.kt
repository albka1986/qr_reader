package com.ponomarenko.qrreader.details.fragments.contact

import android.net.Uri
import com.google.android.gms.vision.barcode.Barcode

interface ContactView {

    fun setData(content: String)

    fun initCall(phone: String)

    fun shareContent(content: String)

    fun addContact(contactInfo: Barcode.ContactInfo)

    fun openBrowser(url: Uri)

    fun openGoogleMaps(uri: Uri)

    fun setPhoneBtnVisible(visible: Boolean)

    fun setBrowserBtnVisible(visible: Boolean)

    fun setMapBtnVisible(visible: Boolean)

    fun setEmailBtnVisible(visible: Boolean)


}
