package com.ponomarenko.qrreader.details.fragments.phone

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
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
 * Created by Ponomarenko Oleh on 12.06.2018.
 */
class PhoneFragment : Fragment(), PhoneView {

    private lateinit var presenter: PhonePresenter

    companion object {
        const val CHECK_PERMISSION_CALL_REQUEST: Int = 201
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phone, container, false)
    }

    override fun onStart() {
        super.onStart()
        val barcode: Barcode? = arguments?.getParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)
        barcode?.let {
            presenter = PhonePresenterImpl(it)
            presenter.bind(this)
            presenter.parseBarcode()
            share_btn.setOnClickListener { presenter.onShareBtnPressed() }
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun setData(content: String) {
        detail_info_container_tv.text = content
    }

    @SuppressLint("MissingPermission")
    override fun initCall(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse(getString(R.string.intent_call_type).plus(phone))
        context?.startActivity(intent)
    }

    override fun shareContent(content: String) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            type = getString(R.string.share_type)
            putExtra(Intent.EXTRA_TEXT, content)
        }.let { startActivity(Intent.createChooser(it, getString(R.string.share_title))) }
    }

    override fun addContact(phone: String) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.type = ContactsContract.Contacts.CONTENT_TYPE
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone)
        startActivity(intent)
    }

    override fun setPhoneBtnVisible(visible: Boolean) {
        call_btn.setVisible(visible)
        add_contact_btn.setVisible(visible)

        if (visible) {
            call_btn.setOnClickListener { checkCallPermission() }
            add_contact_btn.setOnClickListener { presenter.onAddContactBtnPressed() }
        }

    }

    private fun checkCallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity?.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                presenter.onCallBtnPressed()

            } else {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), CHECK_PERMISSION_CALL_REQUEST)
            }
        } else {
            presenter.onCallBtnPressed()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CHECK_PERMISSION_CALL_REQUEST) {
            if ((grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED)) {
                presenter.onCallBtnPressed()
            }
        }
    }
}