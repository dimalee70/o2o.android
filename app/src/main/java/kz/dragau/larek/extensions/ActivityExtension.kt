package kz.dragau.larek.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kz.dragau.larek.presentation.presenter.dialogs.ErrorDialogPresenter
import kz.dragau.larek.presentation.presenter.dialogs.ProgressDialogPresenter
import java.io.ByteArrayOutputStream

inline fun AppCompatActivity.showProgressAlertDialog(func: ProgressDialogPresenter.() -> Unit): AlertDialog =
    ProgressDialogPresenter(this).apply {
        func()
    }.create(null)

inline fun AppCompatActivity.showErrorAlertDialog(func: ErrorDialogPresenter.() -> Unit, title: String?, message: String?): AlertDialog =
    ErrorDialogPresenter(this, title, message).apply {
        func()
    }.create(null)

fun Uri.encodeImage(context: Context): String{
    val imageStream = context.contentResolver.openInputStream(this)
    val imageBitmap = BitmapFactory.decodeStream(imageStream)
    val baos = ByteArrayOutputStream()
    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}