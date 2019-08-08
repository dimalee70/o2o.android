package kz.dragau.larek.ui.activity.crop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.databinding.DataBindingUtil

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.dragau.larek.R
import kz.dragau.larek.databinding.ActivityCropBinding
import kz.dragau.larek.presentation.view.crop.CropView
import kz.dragau.larek.presentation.presenter.crop.CropPresenter

import kz.dragau.larek.ui.activity.BaseActivity
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class CropActivity : BaseActivity(), CropView {
    companion object {
        const val TAG = "CropActivity"
        fun getIntent(context: Context): Intent = Intent(context, CropActivity::class.java)
    }

    val CROP_IMAGE_REQUEST = 2

    @InjectPresenter
    lateinit var mCropPresenter: CropPresenter

    lateinit var binding: ActivityCropBinding

    @ProvidePresenter
    fun providePresenter(): CropPresenter
    {
        val presenter = CropPresenter(intent.getParcelableExtra<Uri>("uri"))
        return presenter
    }

    private var isImageAttached : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        isDynamicThemingOn = false
        isFullScreen = true

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crop)
        binding.presenter = mCropPresenter
        binding.executePendingBindings()
    }

    override fun rotate(degrees: Int)
    {
        binding.cropView.rotatedDegrees = degrees
    }


    override fun cropImage()
    {
        val cropped = binding.cropView.croppedImage
        val i = Intent(this, CropActivity::class.java)

        val result = Bitmap.createScaledBitmap(cropped, 400, 400, false)

        val uri = saveTempBitmap(result)
        if (uri != null) {
            i.data = uri
            result.recycle()
            setResult(Activity.RESULT_OK, i)
            finish()
        }
    }

    fun saveTempBitmap(bitmap: Bitmap): Uri? {
        return if (isExternalStorageWritable()) {
            saveImage(bitmap)
        } else {
            showError("Ошибка при сохранении изображения", -1)
            null
        }
    }

    private fun saveImage(finalBitmap: Bitmap): Uri? {

        val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        return try {
            val file = File.createTempFile(timeStamp, ".png", storageDir)
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            showError("Ошибка при сохранении изображения", -1)
            null
        }

    }

    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    override fun close() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
