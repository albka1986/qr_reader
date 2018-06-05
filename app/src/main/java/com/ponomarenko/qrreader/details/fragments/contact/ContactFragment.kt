package com.ponomarenko.qrreader.details.fragments.contact

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.details.DisplayInfoPresenterImpl
import kotlinx.android.synthetic.main.cat_button_ll.*
import kotlinx.android.synthetic.main.detail_container_ll.*

/**
 * Created by Ponomarenko Oleh on 5/2/2018.
 */

class ContactFragment : Fragment(), ContactView, View.OnClickListener {

    private var barcode: Barcode? = null

    override fun getViewFragment(): Fragment? {
        return this
    }

    @SuppressLint("MissingPermission")
    override fun initCall(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:".plus(phone))
        context?.startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.call_btn -> contactPresenter.checkPermission(barcode?.contactInfo)
        }
    }

    override fun setData(detailedInfoText: String) {
        call_btn.visibility = View.VISIBLE //TODO FIX ME
        call_btn.setOnClickListener(this)
        detail_info_container_tv.text = detailedInfoText
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
        barcode = arguments?.getParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)
        if (barcode != null) {
            contactPresenter.setData(barcode!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ContactPresentImpl.CHECK_PERMISSION_CALL_REQUEST) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                contactPresenter.prepareCall(barcode?.contactInfo)
            }
        }
    }
}