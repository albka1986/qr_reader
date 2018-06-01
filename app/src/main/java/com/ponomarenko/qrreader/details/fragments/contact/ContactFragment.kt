package com.ponomarenko.qrreader.details.fragments.contact

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.details.DisplayInfoPresenterImpl
import kotlinx.android.synthetic.main.fragment_contact.*

/**
 * Created by Ponomarenko Oleh on 5/2/2018.
 */

class ContactFragment : Fragment(), ContactView {

    override fun setData(detailedInfoText: String) {
        contact_detail_tv.text = detailedInfoText
    }

    private val contactPresenter: ContactPresenter by lazy { ContactPresentImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactPresenter.bind(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val barcode: Barcode? = arguments?.getParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)
        if (barcode != null) {
            contactPresenter.setData(barcode)
        }
    }
}