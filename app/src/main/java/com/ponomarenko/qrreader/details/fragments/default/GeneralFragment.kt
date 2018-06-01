package com.ponomarenko.qrreader.details.fragments.default

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ponomarenko.qrreader.R
import kotlinx.android.synthetic.main.fragment_general.*

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
class GeneralFragment : Fragment(), GeneralView {

    private val generalPresenter: GeneralPresenter by lazy { GeneralPresenterImpl() }

    override fun setData(text: String) {
        raw_value_tv.text = text

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generalPresenter.bind(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rawData = arguments?.getString("rawData")
        if (rawData != null) {
            generalPresenter.setData(rawData)
        }
    }
}