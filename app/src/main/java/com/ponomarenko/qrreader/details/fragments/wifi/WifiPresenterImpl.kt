package com.ponomarenko.qrreader.details.fragments.wifi

import android.content.Context
import android.net.wifi.WifiConfiguration
import com.google.android.gms.vision.barcode.Barcode
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager



/**
 * Created by Ponomarenko Oleh on 08.06.2018.
 */
class WifiPresenterImpl(private val barcode: Barcode) : WifiPresenter {

    private var wifiView: WifiView? = null
    private var ssid: String? = null
    private var password: String? = null


    override fun bind(wifiView: WifiView) {
        this.wifiView = wifiView
    }

    override fun unbind() {
        wifiView = null
    }


    override fun parseBarcode() {

        val ssidTitle = "SSID:"
        ssid = barcode.wifi.ssid
        val passwordTitle = "\nPassword:"
        password = barcode.wifi.password

        val content = arrayOf(ssidTitle, ssid, passwordTitle.takeIf { password.isNotBlank() && password.length > 2 }, password.takeIf { it.isNotBlank() && it.length > 2 })
                .filterNotNull()
                .joinToString("\n", "", "")

        wifiView?.setData(content)
        checkAvailableButtons(barcode)
    }


    override fun onShareBtnPressed() {

        wifiView?.shareContent(barcode.displayValue)
    }

    override fun onConnectBtnPressed() {
        val networkSSID = "test"
        val networkPass = "pass"

        val conf = WifiConfiguration()
        conf.SSID = "\"" + networkSSID + "\""
        conf.preSharedKey = "\""+ networkPass +"\""
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)

        wifiView?.connectWifi(conf)

        TODO("not implemented - onConnectBtnPressed")
    }

    override fun getBarcode(): Barcode {
        return this.barcode
    }

    private fun checkAvailableButtons(barcode: Barcode) {
        val isConnectionBtnUnavailable = barcode.wifi.ssid.isNullOrBlank() && barcode.wifi.password.isNullOrBlank()
        wifiView?.setConnectBtnVisible(!isConnectionBtnUnavailable)
    }

}