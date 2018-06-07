package com.ponomarenko.qrreader.details.fragments.url

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.details.DisplayInfoPresenterImpl
import kotlinx.android.synthetic.main.detail_container_ll.*

/**
 * Created by Ponomarenko Oleh on 5/2/2018.
 */

class UrlFragment : Fragment(), UrlView {

    private val urlPresenter: UrlPresenter by lazy { UrlPresenterImpl() }

    override fun setData(detailedInfoText: String) {
        detail_info_container_tv.text = detailedInfoText
    }

    override fun onStart() {
        super.onStart()
        urlPresenter.bind(this)
        val barcode: Barcode = arguments?.getParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)!!
        urlPresenter.setData(barcode)
    }

    override fun onStop() {
        super.onStop()
        urlPresenter.unbind()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_url, container, false)
    }
}