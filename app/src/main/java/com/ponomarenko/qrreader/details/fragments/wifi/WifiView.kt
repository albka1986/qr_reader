package com.ponomarenko.qrreader.details.fragments.wifi

import android.net.wifi.WifiConfiguration

/**
 * Created by Ponomarenko Oleh on 08.06.2018.
 */
interface WifiView {
    fun setData(text: String)
    fun setConnectBtnVisible(visible: Boolean)
    fun shareContent(content: String)
    fun connectWifi(conf: WifiConfiguration)
}