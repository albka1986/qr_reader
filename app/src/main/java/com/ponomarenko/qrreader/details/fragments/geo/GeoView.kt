package com.ponomarenko.qrreader.details.fragments.geo

import android.net.Uri

interface GeoView {
    fun setData(content: String?)
    fun setMapBtnVisible(visible: Boolean)
    fun openGoogleMaps(uri: Uri)
    fun shareContent(content: String)
}
