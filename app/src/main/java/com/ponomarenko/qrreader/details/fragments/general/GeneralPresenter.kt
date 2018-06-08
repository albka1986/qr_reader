package com.ponomarenko.qrreader.details.fragments.general

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
interface GeneralPresenter {
    fun bind(generalView: GeneralView)
    fun unbind()
    fun parseBarcode()
    fun onShareBtnPressed()
}