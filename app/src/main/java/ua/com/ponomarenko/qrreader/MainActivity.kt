package ua.com.ponomarenko.qrreader

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.android.gms.vision.barcode.Barcode
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var barcode: Barcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barcode = intent.extras.getParcelable("barcodeInstance")
        content_tv.text = barcode.displayValue

        browse_btn.setOnClickListener(this)
        copy_btn.setOnClickListener(this)
        share_btn.setOnClickListener(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.browse_btn -> openBrowsePressed()
            R.id.copy_btn -> copyPressed()
            R.id.share_btn -> sharePressed()
        }
    }

    private fun sharePressed() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, barcode.rawValue)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    private fun copyPressed() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(getString(applicationInfo.labelRes), barcode.rawValue)
        clipboard.primaryClip = clip
        Toast.makeText(this, getString(R.string.toast_data_copied), Toast.LENGTH_SHORT).show()
    }

    private fun openBrowsePressed() {
        var url = Uri.parse(barcode.url.url)
        if (TextUtils.isEmpty(url.toString())) {
            url = Uri.parse(barcode.rawValue)
        }
        if (TextUtils.isEmpty(url.toString())) {
            Toast.makeText(this, getString(R.string.toast_url_not_found), Toast.LENGTH_SHORT).show()
            return
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, url)
        startActivity(browserIntent)
    }


}
