package com.ponomarenko.qrreader.details.fragments.geo

import android.net.Uri
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.details.fragments.contact.ContactPresentImpl

/**
 * Created by Ponomarenko Oleh on 12.06.2018.
 */
class GeoPresenterImpl(private val barcode: Barcode) : GeoPresenter {

    private var geoView: GeoView? = null

    override fun bind(view: GeoView?) {
        this.geoView = view
    }

    override fun unbind() {
        this.geoView = null
    }

    override fun parseBarcode() {
        val content = parseContent()
        content?.let {
            geoView?.setData(it)
        }
        checkAvailableBtn()
    }

    private fun checkAvailableBtn() {
        geoView?.setMapBtnVisible(barcode.geoPoint != null)

    }

    private fun parseContent(): String? {
        return barcode.displayValue
    }

    override fun onShareBtnPressed() {
        val text = barcode.displayValue
        geoView?.shareContent(text)
    }

    override fun getBarcode(): Barcode {
        return this.barcode
    }

    override fun onMapBtnPressed() {
        val textAddress = StringBuilder()
        val firstAddress = barcode.contactInfo?.addresses?.first()?.addressLines
        firstAddress?.forEach { currentAddress -> textAddress.append(currentAddress) }
        val uri: Uri
        uri = if (textAddress.isBlank()) {
            with(barcode.geoPoint) {
                val coordinates = String.format("geo:$lat,$lng")
                Uri.parse(coordinates)
            }
        } else {
            Uri.parse(ContactPresentImpl.GOOGLE_MAP_URL.plus(textAddress))
        }
        geoView?.openGoogleMaps(uri)
    }


}