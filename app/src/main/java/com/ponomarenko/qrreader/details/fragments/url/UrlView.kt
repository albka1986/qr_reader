package com.ponomarenko.qrreader.details.fragments.url

import android.net.Uri

interface UrlView {

    fun setData(detailedInfoText: String)
    fun shareContent(content: String)
    fun setBrowserBtnVisible(visible: Boolean)
    fun openBrowser(url: Uri)
}
