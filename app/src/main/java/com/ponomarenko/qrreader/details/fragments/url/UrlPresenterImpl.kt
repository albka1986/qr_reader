package com.ponomarenko.qrreader.details.fragments.url

import android.net.Uri
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.details.fragments.contact.ContactPresentImpl
import java.util.regex.Pattern

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
class UrlPresenterImpl(private var barcode: Barcode) : UrlPresenter {

    companion object {
        const val URL_REGEX = "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)" +
                "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*" +
                "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)"
    }

    private var urlView: UrlView? = null
    private var url: String? = null

    override fun checkAvailableButtons(url: String) {
        urlView?.setBrowserBtnVisible(url.isNotEmpty())
    }

    override fun onBrowserBtnPressed() {
        url?.let {
            if (it.startsWith(ContactPresentImpl.HTTP) || it.startsWith(ContactPresentImpl.HTTPS)) {
            } else {
                url = ContactPresentImpl.HTTP.plus(url)
            }
            val uri = Uri.parse(url)

            urlView?.openBrowser(uri)
        }

    }

    override fun onShareBtnPressed() {
        val text = barcode.displayValue
        urlView?.shareContent(text)
    }

    override fun getBarcode(): Barcode {
        return this.barcode
    }

    override fun parseBarcode() {
        val text = parseUrl()

        text?.let {
            if (it.isNotEmpty()) {
                urlView?.setData(it)
                checkAvailableButtons(it)
            } else {
                urlView?.setData(barcode.rawValue)
            }
        }


    }

    private fun parseUrl(): String? {
        barcode.url?.let {
            this.url = arrayOf(it.title.takeIf { it.isNotEmpty() && it.length > 2 },
                    it.url.takeIf { it.isNotEmpty() && it.length > 2 })
                    .filterNotNull()
                    .joinToString("\n\n", "", "")
        }

        if (this.url.isNullOrEmpty()) {
            val matcher = urlPattern().matcher(barcode.rawValue)
            while (matcher.find()) {
                val matchStart = matcher.start(1)
                val matchEnd = matcher.end()
                this.url = barcode.rawValue.substring(matchStart, matchEnd)
            }
        }

        return this.url
    }

    override fun bind(view: UrlView?) {
        this.urlView = view
    }

    override fun unbind() {
        urlView = null
    }

    private fun urlPattern(): Pattern {
        return Pattern.compile(URL_REGEX)
    }

}