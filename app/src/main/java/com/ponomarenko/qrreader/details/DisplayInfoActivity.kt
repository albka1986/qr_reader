package com.ponomarenko.qrreader.details

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.camera.CameraActivity
import kotlinx.android.synthetic.main.activity_main.*

class DisplayInfoActivity : AppCompatActivity(), DisplayInfoView {


    private val displayInfoPresenter: DisplayInfoPresenter by lazy { DisplayInfoPresenterImpl() }

    private lateinit var barcode: Barcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barcode = intent.extras.getParcelable(CameraActivity.BARCODE_KEY)
        displayInfoPresenter.bind(this)
        displayInfoPresenter.detectFragment(barcode)
        take_new_qr_btn.setOnClickListener { takeNewQR() }
    }

    private fun takeNewQR() {
        finish()
        val intentStartCamera = Intent(this, CameraActivity::class.java)
        startActivity(intentStartCamera)
    }

    override fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
    }
}
