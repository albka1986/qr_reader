package com.ponomarenko.qrreader.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.View
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.ponomarenko.qrreader.PERMISSION_REQUEST_CODE
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.    checkPermissions
import com.ponomarenko.qrreader.details.DisplayInfoActivity
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : Activity() {

    companion object {
        const val BARCODE_KEY: String = "barcodeIntentKey"
        fun getIntent(context: Context) = Intent(context, CameraActivity::class.java)
    }

    private val tag = this::class.qualifiedName
    private lateinit var cameraSource: CameraSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun launchBarcodeDetector() {
        val barcodeDetector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build()
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                onBarcodeDetected(detections)
            }

            override fun release() {}

        })

        cameraSource = CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .build()

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(p0: SurfaceHolder) {
                try {
                    cameraSource.start(surfaceView.holder)
                } catch (e: SecurityException) {
                    Log.e(tag, e.message ?: "Unexpected error")
                }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                //no opt
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }
        })
    }

    private fun onBarcodeDetected(detections: Detector.Detections<Barcode>?) {
        val barcodeList: SparseArray<Barcode> = detections!!.detectedItems
        if (barcodeList.size() != 0) {
            val intent = DisplayInfoActivity.getIntent(this)
            intent.removeExtra(BARCODE_KEY)
            intent.putExtra(BARCODE_KEY, barcodeList.valueAt(0))
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)

            Log.d(tag, barcodeList.valueAt(0).rawValue)
        }
    }

    private fun runCamera() {
        launchBarcodeDetector()
        surfaceView.visibility = View.VISIBLE
    }

    private fun checkPermission() {
        if (this.checkPermissions(arrayOf(android.Manifest.permission.CAMERA))) {
            runCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED)) {
                    runCamera()
                } else {
                    finish()
                }
                return
            }
        }
    }
}