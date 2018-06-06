package com.ponomarenko.qrreader.details.fragments.default

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
class GeneralPresenterImpl : GeneralPresenter {


    private var generalView: GeneralView? = null

    override fun setData(rawData: String) {
        generalView?.setData(rawData)
    }

    override fun bind(generalView: GeneralView) {
        this.generalView = generalView
    }

    override fun unbind() {
        generalView = null
    }

}