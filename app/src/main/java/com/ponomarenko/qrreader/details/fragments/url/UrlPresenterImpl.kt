package com.ponomarenko.qrreader.details.fragments.url

import android.text.TextUtils
import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
class UrlPresenterImpl : UrlPresenter {

    private lateinit var urlView: UrlView

    override fun bind(urlView: UrlView) {
        this.urlView = urlView
    }

    override fun unbind() {
        TODO("not implemented unbind")
    }

    override fun setData(barcode: Barcode) {
        val text = parseUrlInfo(barcode)
        urlView.setData(text)
    }

    private fun parseUrlInfo(barcode: Barcode): String {
        val title = barcode.url.title?.plus("\n")
        val url = barcode.url?.url

        if (TextUtils.isEmpty(url)) {
            return barcode.displayValue
        }
        return title + url
    }
}