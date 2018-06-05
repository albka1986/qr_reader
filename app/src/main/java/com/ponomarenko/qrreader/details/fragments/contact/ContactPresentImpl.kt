package com.ponomarenko.qrreader.details.fragments.contact

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */

class ContactPresentImpl : ContactPresenter {

    companion object {
        const val CHECK_PERMISSION_CALL_REQUEST: Int= 201
    }

    private lateinit var contactView: ContactView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun requestPermissions(contactInfo: Barcode.ContactInfo?) {
        contactView.getViewFragment()?.requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), CHECK_PERMISSION_CALL_REQUEST)
    }

    override fun checkPermission(contactInfo: Barcode.ContactInfo?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (contactView.getViewFragment()?.activity?.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                prepareCall(contactInfo)
            } else {
                requestPermissions(contactInfo)
            }
        } else {
            prepareCall(contactInfo)
        }
    }

    override fun prepareCall(contactInfo: Barcode.ContactInfo?) {
        if (contactInfo?.phones == null || contactInfo.phones[0] == null) {
            return
        }
        val phoneNumber = contactInfo.phones[0].number
        contactView.initCall(phoneNumber)
    }

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