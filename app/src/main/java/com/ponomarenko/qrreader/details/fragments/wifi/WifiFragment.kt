package com.ponomarenko.qrreader.details.fragments.wifi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.details.DisplayInfoPresenterImpl
import com.ponomarenko.qrreader.setVisible
import kotlinx.android.synthetic.main.cat_button_ll.*
import kotlinx.android.synthetic.main.detail_container_ll.*


/**
 * Created by Ponomarenko Oleh on 08.06.2018.
 */
class WifiFragment : Fragment(), WifiView {


    companion object {
        const val CHECK_PERMISSION_WIFI_REQUEST: Int = 203
    }

    override fun connectWifi(conf: WifiConfiguration) {
        val wifiManager = activity?.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.addNetwork(conf)

        val list = wifiManager.configuredNetworks
        for (i in list) {
            if (i.SSID != null && i.SSID == "\"" + conf.SSID + "\"") {
                wifiManager.isWifiEnabled = true
                wifiManager.disconnect()
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val listPermissionsNeeded = ArrayList<String>()

            if (activity?.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE)
            }
            if (activity?.checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CHANGE_WIFI_STATE)
            }

            if (!listPermissionsNeeded.isEmpty()) {
                requestPermissions(listPermissionsNeeded.toTypedArray(), CHECK_PERMISSION_WIFI_REQUEST)
                return false
            }
            return true
        } else {
            return true
        }
    }

    override fun shareContent(content: String) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            type = getString(R.string.share_type)
            putExtra(Intent.EXTRA_TEXT, content)
        }.let { startActivity(Intent.createChooser(it, getString(R.string.share_title))) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CHECK_PERMISSION_WIFI_REQUEST) {
            if ((grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED)) {
                wifiPresenter.onConnectBtnPressed()
            }
        }
    }

}