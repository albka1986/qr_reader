package com.ponomarenko.qrreader.details.fragments.geo

import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
 * Created by Ponomarenko Oleh on 12.06.2018.
 */
class GeoFragment : Fragment(), GeoView {

    lateinit var presenter: GeoPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onStart() {
        super.onStart()
        val barcode: Barcode? = arguments?.getParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)
        barcode?.let {
            presenter = GeoPresenterImpl(it)
            presenter.bind(this)
            presenter.parseBarcode()
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun setMapBtnVisible(visible: Boolean) {
        map_btn.setVisible(visible)
        if (visible) {
            map_btn.setOnClickListener { presenter.onMapBtnPressed() }
        }
    }

    override fun openGoogleMaps(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun shareContent(content: String) {
        startActivity(Intent.createChooser(with(Intent()) {
            action = Intent.ACTION_SEND
            type = getString(R.string.share_type)
            putExtra(Intent.EXTRA_TEXT, content)
        }, getString(R.string.share_title)))
    }

    override fun setData(content: String?) {
        content?.let {
            detail_info_container_tv.text = content
        }
    }
}