package com.ponomarenko.qrreader.fragments

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
class ContactPresentImpl : ContactPresenter {

    lateinit var contactView: ContactView

    override fun bind(contactView: ContactView) {
        this.contactView = contactView
    }

    override fun onCallPressed() {
        contactView.startCall()
    }
}