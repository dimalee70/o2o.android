package kz.dragau.larek.ui.activity.store

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.arellomobile.mvp.presenter.InjectPresenter
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.store.StoreView
import kz.dragau.larek.presentation.presenter.store.StorePresenter
import kz.dragau.larek.ui.activity.BaseActivity


class StoreActivity : BaseActivity(), StoreView {
    companion object {
        const val TAG = "StoreActivity"
        fun getIntent(context: Context): Intent = Intent(context, StoreActivity::class.java)
    }

    @InjectPresenter
    lateinit var mStorePresenter: StorePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
    }
}
