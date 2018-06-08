package com.ponomarenko.qrreader.details.fragments.general

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.details.DisplayInfoPresenterImpl
import kotlinx.android.synthetic.main.cat_button_ll.*
import kotlinx.android.synthetic.main.fragment_general.*

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
class GeneralFragment : Fragment(), GeneralView {

    private lateinit var generalPresenter: GeneralPresenter

    override fun setData(text: String) {
        raw_value_tv.text = text
    }

    override fun onStart() {
        super.onStart()

        val barcode: Barcode? = arguments?.getParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)
        barcode?.let {
            generalPresenter = GeneralPresenterImpl(it)
            generalPresenter.bind(this)
            generalPresenter.parseBarcode()
            share_btn.setOnClickListener { generalPresenter.onShareBtnPressed() }
        }
    }

    override fun onStop() {
        super.onStop()
        generalPresenter.unbind()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    override fun shareContent(text: String) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            type = getString(R.string.share_type)
            putExtra(Intent.EXTRA_TEXT, text)
        }.let { startActivity(Intent.createChooser(it, getString(R.string.share_title))) }
    }

}