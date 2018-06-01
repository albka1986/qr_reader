package com.ponomarenko.qrreader.details

import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.details.fragments.contact.ContactFragment
import com.ponomarenko.qrreader.details.fragments.default.GeneralFragment
import org.jetbrains.annotations.NotNull

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
class DisplayInfoPresenterImpl : DisplayInfoPresenter {

    companion object {
        const val ARGUMENT_DATA_KEY = "ARGUMENT_DATA_KEY"
    }

    private lateinit var displayInfoView: DisplayInfoView

    override fun bind(displayInfoView: DisplayInfoView) {
        this.displayInfoView = displayInfoView
    }

    override fun detectFragment(@NotNull barcode: Barcode?) {
        when {
            barcode!!.contactInfo != null -> launchContactFragment(barcode)
        /*barcode.email != null -> BarcodeType.EMAIL
        barcode.phone != null -> BarcodeType.PHONE
        barcode.url != null -> BarcodeType.URL
        barcode.calendarEvent != null -> BarcodeType.CALENDAR_EVENT
        barcode.wifi != null -> BarcodeType.WIFI
        barcode.driverLicense != null -> BarcodeType.DRIVER_LICENSE
        barcode.geoPoint != null -> BarcodeType.GEO_POINT*/

            else -> {
                launchGeneralFragment(barcode)
            }
        }
    }

    private fun launchContactFragment(barcode: Barcode?) {
        val contactFragment: Fragment = ContactFragment()
        val args = Bundle()
        args.putParcelable(ARGUMENT_DATA_KEY, barcode)
        contactFragment.arguments = args
        displayInfoView.launchFragment(contactFragment)
    }

    private fun launchGeneralFragment(barcode: Barcode?) {
        val generalFragment: Fragment = GeneralFragment()
        val args = Bundle()
        args.putString(ARGUMENT_DATA_KEY, barcode!!.displayValue)
        generalFragment.arguments = args
        displayInfoView.launchFragment(generalFragment)
    }
}