package com.ponomarenko.qrreader.details.fragments.url

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.details.DisplayInfoPresenterImpl
import com.ponomarenko.qrreader.setVisible
import kotlinx.android.synthetic.main.cat_button_ll.*
import kotlinx.android.synthetic.main.detail_container_ll.*

/**
 * Created by Ponomarenko Oleh on 5/2/2018.
 */

class UrlFragment : Fragment(), UrlView {

    private lateinit var urlPresenter: UrlPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_url, container, false)
    }

    override fun onStart() {
        super.onStart()
        val barcode: Barcode? = arguments?.getParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)
        barcode?.let {
            urlPresenter = UrlPresenterImpl(it)
            urlPresenter.bind(this)
            urlPresenter.parseBarcode()
            share_btn.setOnClickListener { urlPresenter.onShareBtnPressed() }
        }
    }

    override fun onStop() {
        super.onStop()
        urlPresenter.unbind()
    }

    override fun setBrowserBtnVisible(visible: Boolean) {
        browser_btn.setVisible(visible)
        if (visible) {
            browser_btn.setOnClickListener { urlPresenter.onBrowserBtnPressed() }
        }
    }

    override fun openBrowser(url: Uri) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = url
        startActivity(i)
    }

    override fun shareContent(content: String) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            type = getString(R.string.share_type)
            putExtra(Intent.EXTRA_TEXT, content)
        }.let { startActivity(Intent.createChooser(it, getString(R.string.share_title))) }
    }

    override fun setData(detailedInfoText: String) {
        detail_info_container_tv.text = detailedInfoText
    }
}