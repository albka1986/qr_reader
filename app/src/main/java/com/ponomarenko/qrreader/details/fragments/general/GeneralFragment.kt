package com.ponomarenko.qrreader.details.fragments.general

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.details.DisplayInfoPresenterImpl
import kotlinx.android.synthetic.main.fragment_general.*

/**
 * Created by Ponomarenko Oleh on 31.05.2018.
 */
class GeneralFragment : Fragment(), GeneralView {

    private val generalPresenter: GeneralPresenter by lazy { GeneralPresenterImpl() }

    override fun setData(text: String) {
        raw_value_tv.text = text
    }

    override fun onStart() {
        super.onStart()
        generalPresenter.bind(this)
        val rawData = arguments?.getString(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY)
        rawData?.let { generalPresenter.setData(rawData) }
    }

    override fun onStop() {
        super.onStop()
        generalPresenter.unbind()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    override fun shareContent(text: String) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            type = getString(R.string.share_type)
            putExtra(Intent.EXTRA_TEXT, text)
        }.let { startActivity(Intent.createChooser(it, getString(R.string.share_title))) }
    }

}