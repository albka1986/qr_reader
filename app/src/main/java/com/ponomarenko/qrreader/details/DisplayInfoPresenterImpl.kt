package com.ponomarenko.qrreader.details

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.details.fragments.default.GeneralFragment
import org.jetbrains.annotations.NotNull

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
class DisplayInfoPresenterImpl : DisplayInfoPresenter {


    private lateinit var displayInfoView: DisplayInfoView

    override fun bind(displayInfoView: DisplayInfoView) {
        this.displayInfoView = displayInfoView
    }


    override fun detectFragment(@NotNull barcode: Barcode?) {
        when {
        /*barcode!!.contactInfo != null -> BarcodeType.CONTACT
        barcode.email != null -> BarcodeType.EMAIL
        barcode.phone != null -> BarcodeType.PHONE
        barcode.url != null -> BarcodeType.URL
        barcode.calendarEvent != null -> BarcodeType.CALENDAR_EVENT
        barcode.wifi != null -> BarcodeType.WIFI
        barcode.driverLicense != null -> BarcodeType.DRIVER_LICENSE
        barcode.geoPoint != null -> BarcodeType.GEO_POINT*/

            else -> {
                val generalFragment: Fragment = GeneralFragment()
                val args = Bundle()
                args.putString("rawData", barcode!!.displayValue)
                generalFragment.arguments = args
                displayInfoView.launchFragment(generalFragment)
            }
        }
    }
}