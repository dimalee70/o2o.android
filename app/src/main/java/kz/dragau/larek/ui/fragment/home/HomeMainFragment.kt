package kz.dragau.larek.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.home.HomeMainView
import kz.dragau.larek.presentation.presenter.home.HomeMainPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import photograd.kz.photograd.ui.fragment.BaseMvpFragment

class HomeMainFragment : BaseMvpFragment(), HomeMainView {
    companion object {
        const val TAG = "HomeMainFragment"

        fun newInstance(): HomeMainFragment {
            val fragment: HomeMainFragment = HomeMainFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mHomeMainPresenter: HomeMainPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
