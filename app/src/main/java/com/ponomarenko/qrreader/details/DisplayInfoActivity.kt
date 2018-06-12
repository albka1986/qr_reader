package com.ponomarenko.qrreader.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.camera.CameraActivity
import kotlinx.android.synthetic.main.activity_display_info.*

class DisplayInfoActivity : AppCompatActivity(), DisplayInfoView {

    companion object {
        fun getIntent(context: Context) = Intent(context, DisplayInfoActivity::class.java)
    }

    private val displayInfoPresenter: DisplayInfoPresenter by lazy { DisplayInfoPresenterImpl() }

    private lateinit var barcode: Barcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_info)
        take_new_qr_btn.setOnClickListener { takeNewQR() }
    }

    override fun onStart() {
        super.onStart()
        displayInfoPresenter.bind(this)
        barcode = intent.extras.getParcelable(CameraActivity.BARCODE_KEY)
        displayInfoPresenter.detectFragment(barcode)
    }

    override fun onStop() {
        super.onStop()
        displayInfoPresenter.unbind()
    }

    private fun takeNewQR() {
        startActivity(CameraActivity.getIntent(this))
        finish()
    }

    override fun launchFragment(fragment: Fragment, barcode: Barcode) {
        fragment.arguments = Bundle().apply { putParcelable(DisplayInfoPresenterImpl.ARGUMENT_DATA_KEY, barcode) }
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
    }
}
