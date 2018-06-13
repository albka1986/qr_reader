package com.ponomarenko.qrreader.details

import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.details.fragments.contact.ContactFragment
import com.ponomarenko.qrreader.details.fragments.email.EmailFragment
import com.ponomarenko.qrreader.details.fragments.general.GeneralFragment
import com.ponomarenko.qrreader.details.fragments.geo.GeoFragment
import com.ponomarenko.qrreader.details.fragments.phone.PhoneFragment
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
            barcode.contactInfo != null -> displayInfoView?.launchFragment(ContactFragment(), barcode)
            barcode.wifi != null -> displayInfoView?.launchFragment(WifiFragment(), barcode)
            barcode.email != null -> displayInfoView?.launchFragment(EmailFragment(), barcode)
            barcode.geoPoint != null -> displayInfoView?.launchFragment(GeoFragment(), barcode)
            barcode.url != null -> displayInfoView?.launchFragment(UrlFragment(), barcode)
            barcode.phone != null -> displayInfoView?.launchFragment(PhoneFragment(), barcode)

//          barcode.calendarEvent != null -> BarcodeType.CALENDAR_EVENT

            else -> {
                displayInfoView?.launchFragment(GeneralFragment(), barcode)
            }
        }
    }


}