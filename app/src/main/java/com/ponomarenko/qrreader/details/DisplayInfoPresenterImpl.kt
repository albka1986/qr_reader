package com.ponomarenko.qrreader.details

import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.details.fragments.contact.ContactFragment
import com.ponomarenko.qrreader.details.fragments.default.GeneralFragment
import com.ponomarenko.qrreader.details.fragments.url.UrlFragment

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

    override fun detectFragment(barcode: Barcode?) {
        barcode?.let {
            TODO("move when clause here")
        }

        when {
            barcode!!.contactInfo != null -> launchContactFragment(barcode)
            barcode.url != null -> launchUrlFragment(barcode)

        /*barcode.email != null -> BarcodeType.EMAIL
        barcode.phone != null -> BarcodeType.PHONE
        barcode.calendarEvent != null -> BarcodeType.CALENDAR_EVENT
        barcode.ic_wifi != null -> BarcodeType.WIFI
        barcode.driverLicense != null -> BarcodeType.DRIVER_LICENSE
        barcode.geoPoint != null -> BarcodeType.GEO_POINT*/

            else -> {
                launchGeneralFragment(barcode)
            }
        }
    }

    private fun launchUrlFragment(barcode: Barcode) {
        TODO("refactoring")
        val urlFragment = UrlFragment()
        val args = Bundle()
        args.putParcelable(ARGUMENT_DATA_KEY, barcode)
        urlFragment.arguments = args
        displayInfoView?.launchFragment(urlFragment)
    }

    private fun launchContactFragment(barcode: Barcode?) {
        TODO("refactoring")
        val contactFragment: Fragment = ContactFragment()
        val args = Bundle()
        args.putParcelable(ARGUMENT_DATA_KEY, barcode)
        contactFragment.arguments = args
        displayInfoView?.launchFragment(contactFragment)
    }

    private fun launchGeneralFragment(barcode: Barcode?) {
        TODO("refactoring")
        val generalFragment: Fragment = GeneralFragment()
        generalFragment.arguments = Bundle().apply { putString(ARGUMENT_DATA_KEY, barcode!!.displayValue) }
        displayInfoView?.launchFragment(generalFragment)
    }
}