package ua.com.ponomarenko.qrreader

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_camera.*

private const val PERMISSION_CODE = 101


class CameraActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        checkPermissions()

        val barcodeDetector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build()
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun receiveDetections(barcodes: Detector.Detections<Barcode>?) {
                val detectedItems = barcodes?.detectedItems
                if (detectedItems != null) {
                    val valueAt = detectedItems.valueAt(0)
                }
            }

            override fun release() {}

        })

        val cameraSource = CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build()


        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                Log.d("debug", "surfaceChanged")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                cameraSource.stop()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                try {
                    cameraSource.start(surfaceView.holder)
                } catch (e: SecurityException) {
                    Log.e("CAMERA SOURCE", e.message)
                }
            }
        })
    }

    private fun runCamera() {
        surfaceView.visibility = View.VISIBLE
    }

    private fun checkPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CODE)
            } else {
                runCamera()
            }
        } else {
            runCamera()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
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
