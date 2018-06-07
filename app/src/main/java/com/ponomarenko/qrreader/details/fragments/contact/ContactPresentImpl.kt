package com.ponomarenko.qrreader.details.fragments.contact

import android.net.Uri
import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */

class ContactPresentImpl : ContactPresenter {

    companion object {
        const val GOOGLE_MAP_URL = "http://maps.google.co.in/maps?q="
        const val HTTP = "http://"
        const val HTTPS = "https://"
    }

    private var contactView: ContactView? = null

    override fun onCallBtnPressed(phone: String) {
        contactView?.initCall(phone)
    }

    override fun bind(contactView: ContactView) {
        this.contactView = contactView
    }

    override fun unbind() {
        contactView = null
    }

    override fun parseBarcode(barcode: Barcode) {
        val text = parseContactInfo(barcode)
        contactView?.setData(text)
        checkAvailableButtons(barcode)
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
            return arrayOf(name.formattedName,
                    title,
                    organization,
                    phones?.joinToString("\n", "", "") { it.number },
                    emails?.joinToString("\n", "", "") { it.address },
                    addresses?.map { it.addressLines?.joinToString("\n", "", "") },
                    urls?.joinToString("\n", "", ""))
                    .filterNotNull()
                    .joinToString("\n\n", "", "")
        }
    }

    override fun onBrowserBtnPressed(contactInfo: Barcode.ContactInfo?) {
        var url: String = contactInfo?.urls?.first() ?: return

        if (url.startsWith(HTTP) || url.startsWith(HTTPS)) {
        } else {
            url = HTTP.plus(url)
        }
        val uri = Uri.parse(url)

        contactView?.openBrowser(uri)
    }

    override fun onAddContactBtnPressed(contactInfo: Barcode.ContactInfo?) {
        //TODO parse a contact info here and pass an object Contact
        contactView?.addContact(contactInfo!!)

    }

    override fun onMapBtnPressed(contactInfo: Barcode.ContactInfo?) {
        val textAddress = StringBuilder()
        val firstAddress = contactInfo?.addresses?.first()?.addressLines
        firstAddress?.forEach { currentAddress -> textAddress.append(currentAddress) }

        val uri: Uri = Uri.parse(GOOGLE_MAP_URL.plus(textAddress))
        contactView?.openGoogleMaps(uri)
    }

    override fun onShareBtnPressed(barcode: Barcode?) {
        val text = barcode?.displayValue
        contactView?.shareContent(text)
    }
}