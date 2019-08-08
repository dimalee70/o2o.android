package kz.dragau.larek.ui.activity.product

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import kz.dragau.larek.presentation.view.product.ScanView
import kz.dragau.larek.presentation.presenter.product.ScanPresenter
import kz.dragau.larek.ui.activity.BaseActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView
import timber.log.Timber


class ScanActivity : BaseActivity(), ScanView, ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null

    companion object {
        const val TAG = "ScanActivity"
        fun getIntent(context: Context): Intent = Intent(context, ScanActivity::class.java)
    }

    @InjectPresenter
    lateinit var mScanPresenter: ScanPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)
        println("OnCreate")
    }

    override fun handleResult(p0: Result?) {
//        if(p0!!.barcodeFormat == BarcodeFormat.CODE_128)
        Timber.i("Result from qr code " + p0!!.text)

        onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }
}
