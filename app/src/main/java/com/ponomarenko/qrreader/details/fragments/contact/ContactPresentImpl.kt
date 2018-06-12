package com.ponomarenko.qrreader.details.fragments.contact

import android.net.Uri
import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */

class ContactPresentImpl(private var barcode: Barcode) : ContactPresenter {

    companion object {
        const val GOOGLE_MAP_URL = "https://www.google.com/maps/search/?api=1&query="
        const val HTTP = "http://"
        const val HTTPS = "https://"
    }

    private var contactView: ContactView? = null


    override fun onCallBtnPressed(phone: String) {
        contactView?.initCall(phone)
    }

    override fun bind(view: ContactView?) {
        contactView = view
    }

    override fun unbind() {
        contactView = null
    }

    override fun parseBarcode() {
        val text = parseContactInfo(barcode)
        contactView?.setData(text)
        checkAvailableButtons(barcode)
    }

    override fun onBrowserBtnPressed() {
        var url: String = barcode.contactInfo.urls?.first() ?: return

        if (url.startsWith(HTTP) || url.startsWith(HTTPS)) {
        } else {
            url = HTTP.plus(url)
        }
        val uri = Uri.parse(url)

        contactView?.openBrowser(uri)
    }

    override fun onAddContactBtnPressed() {
        barcode.contactInfo.let { contactView?.addContact(it) }
    }

    override fun onMapBtnPressed() {
        val textAddress = StringBuilder()
        val firstAddress = barcode.contactInfo.addresses?.first()?.addressLines
        firstAddress?.forEach { currentAddress -> textAddress.append(currentAddress) }

        val uri: Uri = Uri.parse(GOOGLE_MAP_URL.plus(textAddress))
        contactView?.openGoogleMaps(uri)
    }

    override fun onShareBtnPressed() {
        val text = barcode.displayValue
        contactView?.shareContent(text)
    }

    override fun getBarcode(): Barcode {
        return this.barcode
    }

    private fun checkAvailableButtons(barcode: Barcode) {
        with(barcode.contactInfo) {
            contactView?.setPhoneBtnVisible(!phones?.takeIf { it.isNotEmpty() }?.first()?.number.isNullOrEmpty())
            contactView?.setMapBtnVisible(!addresses?.takeIf { it.isNotEmpty() }?.first()?.addressLines?.takeIf { it.isNotEmpty() }?.first().isNullOrEmpty())
            contactView?.setBrowserBtnVisible(!urls?.takeIf { it.isNotEmpty() }?.first().isNullOrEmpty())
            contactView?.setEmailBtnVisible(!emails?.takeIf { it.isNotEmpty() }?.first()?.address.isNullOrEmpty())
        }
    }

    private fun parseContactInfo(barcode: Barcode): String {
        with(barcode.contactInfo) {
            return arrayOf(name.formattedName.takeIf { it.isNotBlank() },
                    title.takeIf { it.isNotBlank() },
                    organization.takeIf { it.isNotBlank() },
                    phones?.takeIf { it.isNotEmpty() }?.joinToString("", "", "") { it.number },
                    emails?.takeIf { it.isNotEmpty() }?.joinToString("", "", "") { it.address },
                    addresses?.map { it.addressLines?.takeIf { it.isNotEmpty() }?.joinToString(";\n", "", "") }?.joinToString(", ", "", ""),
                    urls?.joinToString("\n", "", ""))
                    .filterNotNull()
                    .joinToString("\n\n", "", "")
        }
    }

}