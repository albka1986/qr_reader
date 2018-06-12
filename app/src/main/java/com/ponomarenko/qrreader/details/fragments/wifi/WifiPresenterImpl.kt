package com.ponomarenko.qrreader.details.fragments.wifi

import android.net.wifi.WifiConfiguration
import android.util.Log
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.Barcode.WiFi.WPA


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

        val content = arrayOf(ssidTitle, ssid, passwordTitle, password)
                .filterNotNull()
                .joinToString("\n", "", "")

        wifiView?.setData(content)
        checkAvailableButtons(barcode)
    }


    override fun onShareBtnPressed() {

        wifiView?.shareContent(barcode.displayValue)
    }

    override fun onConnectBtnPressed() {
        val networkSSID = barcode.wifi.ssid
        val networkPass = barcode.wifi.password
        val conf = WifiConfiguration()
        conf.SSID = "\"" + networkSSID + "\""   // Please note the quotes. String should contain ssid in quotes
        conf.status = WifiConfiguration.Status.ENABLED

        when {
            barcode.wifi.encryptionType == com.google.android.gms.vision.barcode.Barcode.WiFi.WEP -> {
                Log.v("rht", "Configuring WEP")
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN)
                conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA)
                conf.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
                conf.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)

                if (networkPass.matches(Regex("^[0-9a-fA-F]+$"))) {
                    conf.wepKeys[0] = networkPass
                } else {
                    conf.wepKeys[0] = "\"" + networkPass + "\""
                }

                conf.wepTxKeyIndex = 0

            }
            barcode.wifi.encryptionType == WPA -> {
                Log.v("rht", "Configuring WPA")

                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN)
                conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA)
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)


                conf.preSharedKey = "\"" + networkPass + "\""

            }
            else -> {
                Log.v("rht", "Configuring OPEN network")
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN)
                conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA)
                conf.allowedAuthAlgorithms.clear()
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            }
        }

        wifiView?.connectWifi(conf)
    }

    override fun getBarcode(): Barcode {
        return this.barcode
    }

    private fun checkAvailableButtons(barcode: Barcode) {
        val isConnectionBtnUnavailable = barcode.wifi.ssid.isNullOrBlank() && barcode.wifi.password.isNullOrBlank()
        wifiView?.setConnectBtnVisible(!isConnectionBtnUnavailable)
    }

}