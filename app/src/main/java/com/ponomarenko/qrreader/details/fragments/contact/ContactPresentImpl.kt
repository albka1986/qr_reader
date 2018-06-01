package com.ponomarenko.qrreader.details.fragments.contact

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
class ContactPresentImpl : ContactPresenter {


    private lateinit var contactView: ContactView


    override fun bind(contactView: ContactView) {
        this.contactView = contactView
    }

    override fun unbind() {
        TODO("not implemented unbind")
    }

    override fun setData(barcode: Barcode) {
        val text = parseContactInfo(barcode)
        contactView.setData(text)
    }

    private fun parseContactInfo(barcode: Barcode): String {

        val name = barcode.contactInfo.name.formattedName

        val title = barcode.contactInfo.title

        val organization = barcode.contactInfo.organization

        val phones = StringBuilder()
        barcode.contactInfo.phones.iterator().forEach { phone -> phones.append(phone?.number).append("\n") }

        val emails = StringBuilder()
        barcode.contactInfo.emails.iterator().forEach { email -> emails.append(email?.address).append("\n") }

        val addresses = StringBuilder()
        barcode.contactInfo.addresses.iterator().forEach { address -> address?.addressLines?.iterator()?.forEach { addressLine -> addresses.append(addressLine).append("\n") } }

        val urls = StringBuilder()
        barcode.contactInfo.urls.iterator().forEach { url -> urls.append(url).append("\n") }

        val text = StringBuilder()
        text.append(name).append("\n")
        text.append(title).append("\n")
        text.append(organization).append("\n")
        text.append(phones)
        text.append(emails)
        text.append(addresses)
        text.append(urls)
        return text.toString()
    }
}