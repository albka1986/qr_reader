package com.ponomarenko.qrreader.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.View
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.ponomarenko.qrreader.MainActivity
import com.ponomarenko.qrreader.R
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : Activity() {

    private val tag = this::class.qualifiedName
    private val permissionCode = 101
    private lateinit var cameraSource: CameraSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        checkPermissions()

        val barcodeDetector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build()
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                onBarcodeDetected(detections)
            }

            override fun release() {}

        })

        cameraSource = CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .build()

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                cameraSource.stop()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                try {
                    cameraSource.start(surfaceView.holder)
                } catch (e: SecurityException) {
                    Log.e(tag, e.message)
                }
            }
        })
    }

    private fun onBarcodeDetected(detections: Detector.Detections<Barcode>?) {
        val barcodeList: SparseArray<Barcode> = detections!!.detectedItems
        if (barcodeList.size() != 0) {

            val intent = Intent(this@CameraActivity, MainActivity::class.java)
            intent.putExtra("barcodeInstance", barcodeList.valueAt(0))
            startActivity(intent)

            Log.d(tag, barcodeList.valueAt(0).rawValue)
        }
    }

    private fun runCamera() {
        surfaceView.visibility = View.VISIBLE
    }

    private fun checkPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), permissionCode)
            } else {
                runCamera()
            }
        } else {
            runCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            permissionCode -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    runCamera()
                } else {
                    finish()
                }
                return
            }
        }
    }
}