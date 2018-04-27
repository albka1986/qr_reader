package ua.com.ponomarenko.qrreader

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras
import kotlinx.android.synthetic.main.activity_main.*


private const val PERMISSION_CODE = 101

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 102


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        checkPermissions()

        dispatchTakePictureIntent()

//        val myBitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.puppy)
//
//        val detector = BarcodeDetector.Builder(applicationContext).setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE).build()
//
//        if (!detector.isOperational) {
//        }

//        val frame = Frame.Builder().setBitmap(myBitmap).build()
//        val barcodes = detector.detect(frame)
//
//        val thisCode = barcodes.valueAt(0)
    }



    private fun checkPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_CODE)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                } else {
                    finish()
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data.extras.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }
}
