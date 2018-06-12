package com.ponomarenko.qrreader.details.fragments.email

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 12.06.2018.
 */
class EmailPresenterImpl(private val barcode: Barcode) : EmailPresenter {

    private var emailView: EmailView? = null

    override fun bind(view: EmailView?) {
        this.emailView = view
    }

    override fun unbind() {
        this.emailView = null
    }

    override fun parseBarcode() {
        val content = parseContent()
        content?.let {
            emailView?.setData(content)
        }
        checkAvailableButtons(content)

    }

    private fun parseContent(): String? {
        return barcode.email.address
    }

    override fun getBarcode(): Barcode {
        return this.barcode
    }

    override fun onShareBtnPressed() {
        val text = barcode.displayValue
        emailView?.shareContent(text)
    }

    override fun checkAvailableButtons(content: String?) {
        if (content.isNullOrBlank()) {
            emailView?.setEmailBtnVisible(false)
        } else {
            emailView?.setEmailBtnVisible(true)
        }
    }
}