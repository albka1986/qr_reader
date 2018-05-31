package com.ponomarenko.qrreader.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.R.id.header_image

/**
 * Created by Ponomarenko Oleh on 5/2/2018.
 */

class ContactFragment : Fragment(), ContactView {

    override fun startCall() {

    }

    private val contactPresenter: ContactPresenter by lazy { ContactPresentImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactPresenter.bind(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        header_image.setOnClickListener { contactPresenter.onCallPressed() }
    }
}