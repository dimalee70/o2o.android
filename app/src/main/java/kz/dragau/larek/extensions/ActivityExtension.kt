package kz.dragau.larek.extensions

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kz.dragau.larek.presentation.presenter.dialogs.ErrorDialogPresenter
import kz.dragau.larek.presentation.presenter.dialogs.ProgressDialogPresenter

inline fun AppCompatActivity.showProgressAlertDialog(func: ProgressDialogPresenter.() -> Unit): AlertDialog =
    ProgressDialogPresenter(this).apply {
        func()
    }.create(null)

inline fun AppCompatActivity.showErrorAlertDialog(func: ErrorDialogPresenter.() -> Unit, title: String?, message: String?): AlertDialog =
    ErrorDialogPresenter(this, title, message).apply {
        func()
    }.create(null)