package com.ponomarenko.qrreader.details.fragments.contact

import android.app.Activity

interface ContactView {

    fun setData(detailedInfoText: String)
    fun initCall(phone: String)
    fun getViewActivity(): Activity?


}
