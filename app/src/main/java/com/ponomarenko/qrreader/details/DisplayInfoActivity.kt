package com.ponomarenko.qrreader.details

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.vision.barcode.Barcode
import com.ponomarenko.qrreader.R
import com.ponomarenko.qrreader.camera.CameraActivity
import kotlinx.android.synthetic.main.activity_main.*

class DisplayInfoActivity : AppCompatActivity(), DisplayInfoView {


    private val displayInfoPresenter: DisplayInfoPresenter by lazy { DisplayInfoPresenterImpl() }

    private val mailRequest = 1110

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    private fun sharePressed() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, barcode.rawValue)
        sendIntent.type = getString(R.string.intent_type)
        startActivity(sendIntent)
    }

    private fun copyPressed() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(getString(applicationInfo.labelRes), barcode.rawValue)
        clipboard.primaryClip = clip
        Toast.makeText(this, getString(R.string.toast_data_copied), Toast.LENGTH_SHORT).show()
    }

    private fun openBrowsePressed() {
        var url = barcode.url?.url

        if (TextUtils.isEmpty(url)) {

            val matcher = Patterns.WEB_URL.matcher(barcode.rawValue)
            while (matcher.find()) {
                val matchStart = matcher.start(1)
                val matchEnd = matcher.end()
                url = barcode.rawValue.substring(matchStart, matchEnd)
            }

            if (TextUtils.isEmpty(url)) {
                Toast.makeText(this, getString(R.string.toast_url_not_found), Toast.LENGTH_SHORT).show()
                return
            }
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_share_app -> shareApp()
            R.id.menu_send_feedback -> sendFeedback()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendFeedback() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(getString(R.string.feedback_email)))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        startActivityForResult(Intent.createChooser(intent, getString(R.string.feedback_decription)), mailRequest)
    }

    private fun shareApp() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = getString(R.string.intent_type)
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.play_store_url) + packageName)
        startActivity(Intent.createChooser(intent, getString(R.string.share_app_title)))
    }
}
