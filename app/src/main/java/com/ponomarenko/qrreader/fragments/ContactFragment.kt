package com.ponomarenko.qrreader.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ponomarenko.qrreader.R

/**
 * Created by Ponomarenko Oleh on 5/2/2018.
 */

class ContactFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_contact, container, false)
    }

}