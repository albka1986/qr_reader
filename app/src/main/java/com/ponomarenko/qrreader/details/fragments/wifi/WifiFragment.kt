package com.ponomarenko.qrreader.details.fragments.wifi

import android.annotation.SuppressLint
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Bundle
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

    @SuppressLint("WifiManagerLeak")
    override fun connectWifi(conf: WifiConfiguration) {
        val wifiManager = requireContext().getSystemService(WIFI_SERVICE) as WifiManager
        val netId = wifiManager.addNetwork(conf)
        wifiManager.disconnect()
        wifiManager.enableNetwork(netId, true)
        wifiManager.reconnect()
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
    }

    override fun shareContent(content: String) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            type = getString(R.string.share_type)
            putExtra(Intent.EXTRA_TEXT, content)
        }.let { startActivity(Intent.createChooser(it, getString(R.string.share_title))) }
    }
}