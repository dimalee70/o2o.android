package kz.dragau.larek.presentation.presenter.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.app.AlertDialog
import kz.dragau.larek.App
import kz.dragau.larek.R
import kz.dragau.larek.models.enums.DialogSize

abstract class BaseDialogHelper {

    abstract val dialogView: View
    abstract val builder: AlertDialog.Builder

    //  required bools
    open var cancelable: Boolean = false
    open var isBackGroundTransparent: Boolean = true

    //  dialog
    open var dialog: AlertDialog? = null
    //  dialog create
    open fun create(dialogSize: DialogSize?): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()

        //  very much needed for customised dialogs
        if (isBackGroundTransparent)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (dialogSize != null) {
            dialog?.setOnShowListener {
                val windowlp = dialog?.window?.attributes
                when (dialogSize) {
                    DialogSize.SmallFixedWidth -> windowlp?.width = getDimensionValue(R.dimen.smallDialogWidth)
                    DialogSize.SmallFixedWidthHeight -> {
                        windowlp?.width = getDimensionValue(R.dimen.smallDialogWidth)
                        windowlp?.height = getDimensionValue(R.dimen.smallDialogHeight)
                    }
                    DialogSize.LargeFixedWidth -> windowlp?.width = getDimensionValue(R.dimen.largeDialogWidth)
                    DialogSize.LargeFixedWidthHeight -> {
                        windowlp?.width = getDimensionValue(R.dimen.largeDialogWidth)
                        windowlp?.height = getDimensionValue(R.dimen.largeDialogHeight)
                    }
                }
                dialog?.window?.attributes = windowlp
            }
        }
        return dialog!!
    }

    //  cancel listener
    open fun onCancelListener(func: () -> Unit): AlertDialog.Builder? =
        builder.setOnCancelListener {
            func()
    }


    fun getDimensionValue(id: Int): Int
    {
        return App.appComponent.context().resources.getDimension(id).toInt()
    }

    //  view click listener as extension function
    fun View.setClickListenerToDialogButton(func: (() -> Unit)?) =
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }

    fun AlertDialog.setOnDismissListener(func: (() -> Unit)?) =
        setOnDismissListener {
            func?.invoke()
        }

    fun dismissListener(func: (() -> Unit)? = null) =
        with(this) {
            dismissFunc = func
        }

    var dismissFunc: (() -> Unit)? = null

    fun setDismissListener() =
        with(dialog!!) {
            setOnDismissListener(dismissFunc)
        }
}