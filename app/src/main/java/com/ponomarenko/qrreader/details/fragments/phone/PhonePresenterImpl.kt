package com.ponomarenko.qrreader.details.fragments.phone

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by Ponomarenko Oleh on 12.06.2018.
 */
class PhonePresenterImpl(private val barcode: Barcode) : PhonePresenter {

    private var view: PhoneView? = null

    override fun bind(view: PhoneView?) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun parseBarcode() {
        val content = parseContent()
        content?.let {
            view?.setData(it)
        }
        checkAvailableBtn()
    }

    private fun parseContent(): String? {
        return barcode.displayValue
    }

    private fun checkAvailableBtn() {
        val isEmpty = barcode.phone.number.isNullOrBlank()
        view?.setPhoneBtnVisible(!isEmpty)
    }

    override fun onCallBtnPressed() {
        barcode.phone.number?.let {
            view?.initCall(it)
        }
    }


    override fun onAddContactBtnPressed() {
        barcode.phone.number?.let { view?.addContact(it) }
    }

    override fun getBarcode(): Barcode {
        return this.barcode
    }

    override fun onShareBtnPressed() {
        val text = barcode.displayValue
        view?.shareContent(text)
    }


}