package com.ponomarenko.qrreader.details.fragments.contact

import android.annotation.SuppressLint
import android.app.Activity
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
import kotlinx.android.synthetic.main.detail_container_ll.*

/**
 * Created by Ponomarenko Oleh on 5/2/2018.
 */

class ContactFragment : Fragment(), ContactView, View.OnClickListener {

    private var barcode: Barcode? = null


    override fun getViewActivity(): Activity? {
        return this.activity
    }

    @SuppressLint("MissingPermission")
    override fun initCall(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:".plus(phone))
        context?.startActivity(intent)
    }



    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.call_btn -> contactPresenter.onCallPressed(barcode?.contactInfo)
        }
    }

    override fun setData(detailedInfoText: String) {
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
        if (requestCode == 666) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                contactPresenter.onCallPressed(barcode?.contactInfo)
            }
        }
    }
}