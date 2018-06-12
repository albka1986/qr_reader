package com.ponomarenko.qrreader.details.fragments.email

interface EmailView {
    fun shareContent(text: String)
    fun setData(content: String)
    fun setEmailBtnVisible(visible: Boolean)

}
