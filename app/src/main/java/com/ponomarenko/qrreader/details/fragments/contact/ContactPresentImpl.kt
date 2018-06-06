package com.ponomarenko.qrreader.details.fragments.contact

import android.net.Uri
import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */

class ContactPresentImpl : ContactPresenter {

    private var contactView: ContactView? = null

    override fun onCallBtnPressed(contactInfo: Barcode.ContactInfo?) {
        if (contactInfo?.phones == null || contactInfo.phones.first() == null) {
            return
        }
        val phoneNumber = contactInfo.phones.first().number
        contactView?.initCall(phoneNumber)
    }

    override fun bind(contactView: ContactView) {
        this.contactView = contactView
    }

    override fun unbind() {
        contactView = null
    }

    override fun updateUI(barcode: Barcode) {// TODO naming
        val text = parseContactInfo(barcode)
        contactView?.setData(text)
    }

    private fun parseContactInfo(barcode: Barcode): String {

        //TODO Array -> filter not null -> join to string

        with(barcode.contactInfo) {
            val array = arrayOf(name.formattedName,
                    title,
                    organization,
                    phones,
                    emails,
                    addresses?.map { it.addressLines }?.joinToString(", ", "", ""),
                    urls)
                    .filterNotNull()
                    .joinToString("\n\n", "", "")
        }


        val text = StringBuilder()

        val name = barcode.contactInfo.name.formattedName
        if (name.isNotEmpty()) {
            text.append(name).append("\n\n")
        }

        val title = barcode.contactInfo.title
        if (title.isNotEmpty()) {
            text.append(title).append("\n\n")
        }

        val organization = barcode.contactInfo.organization
        if (organization.isNotEmpty()) {
            text.append(organization).append("\n\n")
        }

        val phones = StringBuilder()
        barcode.contactInfo.phones.iterator().forEach { phone -> phones.append(phone?.number).append("\n\n") }
        contactView?.setCallBtnVisible(phones.isNotEmpty())
        contactView?.setAddContactBtnVisible(phones.isNotEmpty())
        if (phones.isNotEmpty()) {
            text.append(phones)
        }

        val emails = StringBuilder()
        barcode.contactInfo.emails.iterator().forEach { email -> emails.append(email?.address).append("\n\n") }
        contactView?.setEmailBtnVisible(emails.isNotEmpty())
        if (emails.isNotEmpty()) {
            text.append(emails)
        }

        val addresses = StringBuilder()
        barcode.contactInfo.addresses.iterator().forEach { address -> address?.addressLines?.iterator()?.forEach { addressLine -> addresses.append(addressLine).append("\n\n") } }
        contactView?.setMapBtnVisible(addresses.isNotEmpty())
        if (addresses.isNotEmpty()) {
            text.append(addresses)
        }

        val urls = StringBuilder()
        barcode.contactInfo.urls.iterator().forEach { url -> urls.append(url).append("\n\n") }
        contactView?.setBrowserBtnVisible(urls.isNotEmpty())
        if (urls.isNotEmpty()) {
            text.append(urls)
        }

        return text.toString()
    }

    override fun onBrowserBtnPressed(contactInfo: Barcode.ContactInfo?) {
        var url: String = contactInfo?.urls?.first() ?: return

        if (url.startsWith("http://") || url.startsWith("https://")) {
        } else {
            url = "http://$url"
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

        val uri: Uri = Uri.parse("http://maps.google.co.in/maps?q=".plus(textAddress))
        contactView?.openGoogleMaps(uri)
    }

    override fun onShareBtnPressed(barcode: Barcode?) {
        val text = barcode?.displayValue
        contactView?.shareContent(text)
    }
}