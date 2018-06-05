package com.ponomarenko.qrreader.details.fragments.contact

import android.support.v4.app.Fragment

interface ContactView {

    fun setData(detailedInfoText: String)
    fun initCall(phone: String)
    fun getViewFragment(): Fragment?


}
