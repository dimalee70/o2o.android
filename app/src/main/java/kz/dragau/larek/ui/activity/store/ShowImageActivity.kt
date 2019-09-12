package kz.dragau.larek.ui.activity.store

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.arellomobile.mvp.presenter.InjectPresenter
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.store.ShowImageView
import kz.dragau.larek.presentation.presenter.store.ShowImagePresenter

import                  kz.dragau.larek.ui.activity.BaseActivity;


class ShowImageActivity : BaseActivity(), ShowImageView {
    companion object {
        const val TAG = "ShowImageActivity"
        fun getIntent(context: Context): Intent = Intent(context, ShowImageActivity::class.java)
    }

    @InjectPresenter
    lateinit var mShowImagePresenter: ShowImagePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)
    }
}
