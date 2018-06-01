package com.ponomarenko.qrreader.details.fragments.default

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
interface GeneralPresenter {
    fun bind(generalView: GeneralView)
    fun setData(rawData: String)
}