package com.ponomarenko.qrreader.details.fragments.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Ponomarenko Oleh on 13.06.2018.
 */
class WifiReceiver : BroadcastReceiver() {

    companion object {
        private const val ACTION_WIFI_ON = "android.intent.action.WIFI_ON"
        private const val ACTION_WIFI_OFF = "android.intent.action.WIFI_OFF"
        private const val ACTION_CONNECT_TO_WIFI = "android.intent.action.CONNECT_TO_WIFI"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("not implemented - onReceive")
    }
}