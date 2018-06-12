package com.ponomarenko.qrreader.details.fragments.phone

/**
 * Created by Ponomarenko Oleh on 12.06.2018.
 */
interface PhoneView {
    fun setData(content: String)
    fun initCall(phone: String)
    fun shareContent(content: String)
    fun addContact(phone: String)
    fun setPhoneBtnVisible(visible: Boolean)
}