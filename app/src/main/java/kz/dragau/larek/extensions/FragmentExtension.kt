package kz.dragau.larek.extensions

import androidx.appcompat.app.AlertDialog
import kz.dragau.larek.moxy.MvpAppCompatFragment
import kz.dragau.larek.presentation.presenter.dialogs.ConfirmDialogPresenter
import kz.dragau.larek.presentation.presenter.dialogs.ErrorDialogPresenter
import kz.dragau.larek.presentation.presenter.dialogs.ProductExistDialogPresenter


inline fun MvpAppCompatFragment.showErrorAlertDialog(func: ErrorDialogPresenter.() -> Unit, title: String?, message: String?): AlertDialog =
    ErrorDialogPresenter(this.context!!, title, message).apply {
        func()
    }.create(null)

inline fun MvpAppCompatFragment.showConfirmAlertDialog(func: ConfirmDialogPresenter.() -> Unit, title: Int, message: Int): AlertDialog =
    ConfirmDialogPresenter(this.context!!, title, message).apply{
        func()
    }.create(null)