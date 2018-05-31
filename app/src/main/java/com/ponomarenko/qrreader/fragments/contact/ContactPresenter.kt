package com.ponomarenko.qrreader.fragments.contact

/**
 * Created by Ponomarenko Oleh on 5/31/2018.
 */
interface ContactPresenter{
    fun  onCallPressed()
    fun bind(contactView: ContactView)
}