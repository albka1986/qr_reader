package com.ponomarenko.qrreader.details.fragments.contact

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.checkPermissions
import com.ponomarenko.qrreader.details.DisplayInfoPresenterImpl
import com.ponomarenko.qrreader.setVisible
import kotlinx.android.synthetic.main.cat_button_ll.*
import kotlinx.android.synthetic.main.detail_container_ll.*

/**
 * Created by Ponomarenko Oleh on 5/2/2018.
 */

class ContactFragment : Fragment(), ContactView {

    companion object {
        const val CHECK_PERMISSION_CALL_REQUEST: Int = 201
    }

    private lateinit var contactPresenter: ContactPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onStart() {
        super.onStart()
        val barcode: Barcode? = arguments?.getParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)
        barcode?.let {
            contactPresenter = ContactPresentImpl(it)
            contactPresenter.bind(this)
            contactPresenter.parseBarcode()
            share_btn.setOnClickListener { contactPresenter.onShareBtnPressed() }
        }
    }

    override fun onStop() {
        super.onStop()
        contactPresenter.unbind()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CHECK_PERMISSION_CALL_REQUEST) {
            if ((grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED)) {
                choosePhoneNumber()
            }
        }
    }

    override fun setPhoneBtnVisible(visible: Boolean) {
        call_btn.setVisible(visible)
        if (visible) {
            call_btn.setOnClickListener { checkCallPermission() }
        }

        add_contact_btn.setVisible(visible)
        if (visible) {
            add_contact_btn.setOnClickListener { contactPresenter.onAddContactBtnPressed() }
        }
    }

    override fun setBrowserBtnVisible(visible: Boolean) {
        browser_btn.setVisible(visible)
        if (visible) {
            browser_btn.setOnClickListener { contactPresenter.onBrowserBtnPressed() }
        }
    }

    override fun setMapBtnVisible(visible: Boolean) {
        map_btn.setVisible(visible)
        if (visible) {
            map_btn.setOnClickListener { contactPresenter.onMapBtnPressed() }
        }
    }

    override fun setEmailBtnVisible(visible: Boolean) {
        email_btn.setVisible(visible)
        if (visible) {
            email_btn.setOnClickListener { chooseEmail(contactPresenter.getBarcode().contactInfo?.emails) }
        }
    }

    @SuppressLint("MissingPermission")
    override fun initCall(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse(getString(R.string.intent_call_type).plus(phone))
        context?.startActivity(intent)
    }


    override fun setData(content: String) {
        detail_info_container_tv.text = content
    }

    override fun shareContent(content: String) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            type = getString(R.string.share_type)
            putExtra(Intent.EXTRA_TEXT, content)
        }.let { startActivity(Intent.createChooser(it, getString(R.string.share_title))) }
    }

    override fun openBrowser(url: Uri) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = url
        startActivity(i)
    }

    override fun openGoogleMaps(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun addContact(contactInfo: Barcode.ContactInfo) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.type = ContactsContract.Contacts.CONTENT_TYPE

        intent.putExtra(ContactsContract.Intents.Insert.NAME, contactInfo.name?.formattedName)
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, contactInfo.phones?.first()?.number)
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, contactInfo.emails?.first()?.address)

        startActivity(intent)

    }

    private fun checkCallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity?.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                choosePhoneNumber()

            } else {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), CHECK_PERMISSION_CALL_REQUEST)
            }
        } else {
            choosePhoneNumber()
        }
    }

    private fun choosePhoneNumber() {
        with(contactPresenter.getBarcode().contactInfo) {
            if (phones?.size == 1) {
                contactPresenter.onCallBtnPressed(phones.first().number)
            } else {
                val phonesItems = mutableListOf<String>()
                phones.forEach { phone -> phonesItems.add(phone.number) }

                AlertDialog.Builder(requireContext()).apply {
                    setTitle(getString(R.string.choose_number_dialog_title))
                    setItems(phonesItems.toTypedArray(), { dialog, which ->
                        dialog.dismiss()
                        contactPresenter.onCallBtnPressed(phones[which].number)
                    })
                    show()
                }
            }
        }

    }

    private fun chooseEmail(emails: Array<out Barcode.Email>?) {
        if (emails == null) {
            return
        } else if (emails.size == 1) {
            sendEmail(emails.first())
            return
        }

        val emailItems = mutableListOf<CharSequence>()

        emails.forEach { email -> emailItems.add(email.address) }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.choose_email_title))
        builder.setItems(emailItems.toTypedArray(), { dialog, which ->
            dialog.dismiss()
            sendEmail(emails[which])
        })
        builder.show()
    }

    private fun sendEmail(email: Barcode.Email) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(getString(R.string.send_email_scheme), email.address, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, email.subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, email.body)
        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email_title)))
    }

}