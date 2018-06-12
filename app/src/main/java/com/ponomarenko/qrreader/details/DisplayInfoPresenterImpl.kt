package com.ponomarenko.qrreader.details

import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.details.fragments.contact.ContactFragment
import com.ponomarenko.qrreader.details.fragments.email.EmailFragment
import com.ponomarenko.qrreader.details.fragments.general.GeneralFragment
import com.ponomarenko.qrreader.details.fragments.url.UrlFragment
import com.ponomarenko.qrreader.details.fragments.wifi.WifiFragment

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
class DisplayInfoPresenterImpl : DisplayInfoPresenter {

    companion object {
        const val ARGUMENT_DATA_KEY = "ARGUMENT_DATA_KEY"
    }

    private var displayInfoView: DisplayInfoView? = null

    override fun bind(displayInfoView: DisplayInfoView) {
        this.displayInfoView = displayInfoView
    }

    override fun unbind() {
        this.displayInfoView = null
    }

    override fun detectFragment(barcode: Barcode) {
        when {
            barcode.contactInfo != null -> launchContactFragment(barcode)
            barcode.url != null -> launchUrlFragment(barcode)
            barcode.wifi != null -> launchWifiFragment(barcode)
            barcode.email != null -> launchEmailFragment(barcode)

//          barcode.email != null -> BarcodeType.EMAIL
//          barcode.phone != null -> BarcodeType.PHONE
//          barcode.calendarEvent != null -> BarcodeType.CALENDAR_EVENT
//          barcode.driverLicense != null -> BarcodeType.DRIVER_LICENSE
//          barcode.geoPoint != null -> BarcodeType.GEO_POINT

            else -> {
                launchGeneralFragment(barcode)
            }
        }
    }

    private fun launchEmailFragment(barcode: Barcode) {
        val emailFragment = EmailFragment()
        emailFragment.arguments = Bundle().apply { putParcelable(ARGUMENT_DATA_KEY, barcode) }
        displayInfoView?.launchFragment(emailFragment)
    }

    private fun launchWifiFragment(barcode: Barcode) {
        val wifFragment = WifiFragment()
        wifFragment.arguments = Bundle().apply { putParcelable(ARGUMENT_DATA_KEY, barcode) }
        displayInfoView?.launchFragment(wifFragment)
    }

    private fun launchUrlFragment(barcode: Barcode) {
        val urlFragment = UrlFragment()
        urlFragment.arguments = Bundle().apply { putParcelable(ARGUMENT_DATA_KEY, barcode) }
        displayInfoView?.launchFragment(urlFragment)
    }

    private fun launchContactFragment(barcode: Barcode) {
        val contactFragment: Fragment = ContactFragment()
        contactFragment.arguments = Bundle().apply { putParcelable(ARGUMENT_DATA_KEY, barcode) }
        displayInfoView?.launchFragment(contactFragment)
    }

    private fun launchGeneralFragment(barcode: Barcode) {
        val generalFragment: Fragment = GeneralFragment()
        generalFragment.arguments = Bundle().apply { putParcelable(ARGUMENT_DATA_KEY, barcode) }
        displayInfoView?.launchFragment(generalFragment)
    }
}