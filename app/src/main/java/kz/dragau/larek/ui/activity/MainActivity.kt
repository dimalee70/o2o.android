package kz.dragau.larek.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView

import com.arellomobile.mvp.presenter.InjectPresenter
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.MainView
import kz.dragau.larek.presentation.presenter.MainPresenter

import                  com.arellomobile.mvp.MvpAppCompatActivity;


class MainActivity : MvpAppCompatActivity(), MainView {
    companion object {
        const val TAG = "MainActivity"
        fun getIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    @InjectPresenter
    lateinit var mMainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
    }
}
