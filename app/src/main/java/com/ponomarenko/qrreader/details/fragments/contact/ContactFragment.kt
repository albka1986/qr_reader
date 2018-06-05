package com.ponomarenko.qrreader.details.fragments.contact

import android.annotation.SuppressLint
import android.app.AlertDialog
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

    private val contactPresenter: ContactPresenter by lazy { ContactPresentImpl() }

    private var barcode: Barcode? = null

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
            contactPresenter.updateUI(barcode!!)
            share_btn.setOnClickListener(this)
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

    override fun setCallBtnVisible(visible: Boolean) {
        call_btn.visibility = if (visible) View.VISIBLE else View.GONE
        call_btn.setOnClickListener(this)
    }

    override fun setBrowserBtnVisible(visible: Boolean) {
        browser_btn.visibility = if (visible) View.VISIBLE else View.GONE
        browser_btn.setOnClickListener(this)
    }

    override fun setMapBtnVisible(visible: Boolean) {
        map_btn.visibility = if (visible) View.VISIBLE else View.GONE
        map_btn.setOnClickListener(this)

    }

    override fun setAddContactBtnVisible(visible: Boolean) {
        add_contact_btn.visibility = if (visible) View.VISIBLE else View.GONE
        add_contact_btn.setOnClickListener(this)

    }

    override fun setEmailBtnVisible(visible: Boolean) {
        email_btn.visibility = if (visible) View.VISIBLE else View.GONE
        email_btn.setOnClickListener(this)
    }

    override fun getViewFragment(): Fragment? {
        return this
    }

    @SuppressLint("MissingPermission")
    override fun initCall(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse(getString(R.string.intent_call_type).plus(phone))
        context?.startActivity(intent)
    }

    override fun onClick(v: View?) {
        val contactInfo = barcode?.contactInfo
        when (v?.id) {
            R.id.call_btn -> contactPresenter.onCallBtnPressed(contactInfo)
            R.id.browser_btn -> contactPresenter.onBrowserBtnPressed(contactInfo)
            R.id.add_contact_btn -> contactPresenter.onAddContactBtnPressed(contactInfo)
            R.id.map_btn -> contactPresenter.onMapBtnPressed(contactInfo)
            R.id.share_btn -> contactPresenter.onShareBtnPressed(barcode)
            R.id.email_btn -> chooseEmail(contactInfo?.emails)
        }
    }

    private fun chooseEmail(emails: Array<out Barcode.Email>?) {
        if (emails == null) {
            return
        } else if (emails.size == 1) {
            sendEmail(emails.first().address)
            return
        }

        val emailItems: ArrayList<CharSequence> = ArrayList()
        emails.forEach { email -> emailItems.add(email.address) }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.choose_email_title))
        builder.setItems(emailItems.toTypedArray(), { dialog, which ->
            dialog.dismiss()
            sendEmail(emails[which].address)
        })
        builder.show()
    }

    private fun sendEmail(email: String?) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(getString(R.string.send_email_scheme), email, null))
        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email_title)))
    }

    override fun setData(detailedInfoText: String) {
        detail_info_container_tv.text = detailedInfoText
    }

    override fun shareContent(content: String?) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = getString(R.string.share_type)
        shareIntent.putExtra(Intent.EXTRA_TEXT, content)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_title)))
    }

}