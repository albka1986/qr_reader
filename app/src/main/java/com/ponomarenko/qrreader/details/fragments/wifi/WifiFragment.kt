package com.ponomarenko.qrreader.details.fragments.wifi

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.PERMISSION_REQUEST_CODE
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.checkPermissions
import com.ponomarenko.qrreader.details.DisplayInfoPresenterImpl
import com.ponomarenko.qrreader.setVisible
import kotlinx.android.synthetic.main.cat_button_ll.*
import kotlinx.android.synthetic.main.detail_container_ll.*

/**
 * Created by Ponomarenko Oleh on 08.06.2018.
 */
class WifiFragment : Fragment(), WifiView {

    override fun connectWifi(conf: WifiConfiguration) {
        val wifiManager = activity?.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.addNetwork(conf)
        wifiManager.isWifiEnabled = true
        wifiManager.disconnect()

        val list = wifiManager.configuredNetworks
        for (i in list) {
            if (i.SSID != null && i.SSID == "\"" + conf.SSID + "\"") {
                wifiManager.enableNetwork(i.networkId, true)
                wifiManager.reconnect()
                break
            }
        }
    }

    private lateinit var wifiPresenter: WifiPresenter

    override fun onStart() {
        super.onStart()
        val barcode: Barcode? = arguments?.getParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)
        barcode?.let {
            wifiPresenter = WifiPresenterImpl(it)
            wifiPresenter.bind(this)
            wifiPresenter.parseBarcode()
            share_btn.setOnClickListener { wifiPresenter.onShareBtnPressed() }
        }
    }

    override fun onStop() {
        super.onStop()
        wifiPresenter.unbind()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wifi, container, false)
    }

    override fun setData(text: String) {
        detail_info_container_tv.text = text
    }

    override fun setConnectBtnVisible(visible: Boolean) {
        wifi_btn.setVisible(visible)
        wifi_btn.setOnClickListener { checkAndRequestPermissions() }
    }

    private fun checkAndRequestPermissions() {
        if (isPermissionProvided()) {
            wifiPresenter.onConnectBtnPressed()
        }
    }

    private fun isPermissionProvided(): Boolean {
        return checkPermissions(arrayOf(android.Manifest.permission.ACCESS_WIFI_STATE, android.Manifest.permission.CHANGE_WIFI_STATE, android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_FINE_LOCATION))
    }

    override fun shareContent(content: String) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            type = getString(R.string.share_type)
            putExtra(Intent.EXTRA_TEXT, content)
        }.let { startActivity(Intent.createChooser(it, getString(R.string.share_title))) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED)) {
                wifiPresenter.onConnectBtnPressed()
            }
        }
    }
}