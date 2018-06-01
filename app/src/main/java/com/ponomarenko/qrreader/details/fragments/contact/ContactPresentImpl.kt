package com.ponomarenko.qrreader.details.fragments.contact

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
class ContactPresentImpl : ContactPresenter {

    private lateinit var contactView: ContactView


    override fun bind(contactView: ContactView) {
        this.contactView = contactView
    }

    override fun unbind() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCallPressed() {
        contactView.startCall()
    }
}