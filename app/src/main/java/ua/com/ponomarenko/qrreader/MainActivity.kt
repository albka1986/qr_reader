package ua.com.ponomarenko.qrreader

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu


class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 102

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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




    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data.extras.get("data") as Bitmap
//            imageView.setImageBitmap(imageBitmap)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
