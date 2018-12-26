package com.ponomarenko.qrreader.details.fragments.email

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
 * Created by Ponomarenko Oleh on 12.06.2018.
 */
class EmailFragment : Fragment(), EmailView {

    private lateinit var emailPresenter: EmailPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onStart() {
        super.onStart()
        val barcode: Barcode? = arguments?.getParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)
        barcode?.let {
            emailPresenter = EmailPresenterImpl(it)
            emailPresenter.bind(this)
            emailPresenter.parseBarcode()
            share_btn.setOnClickListener { emailPresenter.onShareBtnPressed() }
        }
    }

    override fun onStop() {
        super.onStop()
        emailPresenter.unbind()
    }

    override fun shareContent(text: String) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            type = getString(R.string.share_type)
            putExtra(Intent.EXTRA_TEXT, text)
        }.let { startActivity(Intent.createChooser(it, getString(R.string.share_title))) }
    }

    override fun setData(content: String) {
        detail_info_container_tv.text = content
    }

    override fun setEmailBtnVisible(visible: Boolean) {
        email_btn.setVisible(visible)
        if (visible) {
            email_btn.setOnClickListener { sendEmail(emailPresenter.getBarcode().email) }
        }
    }

    private fun sendEmail(email: Barcode.Email) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(getString(R.string.send_email_scheme), email.address, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, email.subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, email.body)
        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email_title)))
    }
}