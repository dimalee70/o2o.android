package kz.dragau.larek.ui.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.home.HomeMainView
import kz.dragau.larek.presentation.presenter.home.HomeMainPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import kz.dragau.larek.App
import kz.dragau.larek.databinding.FragmentHomeMainBinding
import kz.dragau.larek.models.objects.Customs
import kz.dragau.larek.ui.adapters.RecyclerBindingAdapter
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import java.lang.ClassCastException
import java.util.*
import kotlin.collections.ArrayList

class HomeMainFragment : BaseMvpFragment(), HomeMainView, RecyclerBindingAdapter.OnItemClickListener<Customs> {


//    private var onItemClickListenerRecycler
    companion object {
        const val TAG = "HomeMainFragment"

        fun newInstance(): HomeMainFragment {
            val fragment: HomeMainFragment = HomeMainFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClick(position: Int, item: Customs) {
        Toast.makeText(context!!, item.name, Toast.LENGTH_SHORT).show()
    }

    @InjectPresenter
    lateinit var mHomeMainPresenter: HomeMainPresenter

    lateinit var binding: FragmentHomeMainBinding

    lateinit var recyclerBindingAdapter: RecyclerBindingAdapter<Customs>

    private var onItemClickListenerRecycler: RecyclerBindingAdapter.OnItemClickListener<Customs>? = this
//        object: RecyclerBindingAdapter.OnItemClickListener<Customs>{
//            override fun onItemClick(position: Int, item: Customs) {
//                Toast.makeText(context!!, item.text, Toast.LENGTH_SHORT).show()
//            }
//        }

    var data = ObservableArrayList<Customs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        recyclerBindingAdapter = RecyclerBindingAdapter(R.layout.custom_item, BR.data, context!!)
        if(onItemClickListenerRecycler != null){
            recyclerBindingAdapter.setOnItemClickListener(onItemClickListenerRecycler!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_main, container, false)
        binding.presenter = mHomeMainPresenter
        var customList = ArrayList<Customs>()
        customList.add(Customs("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSUcp-vFwbcy-8m14jgD4p947IV_1TqO--f87Y54u-JvyiiDh1k", "Константин",
            "Аль фараби, к45Б", "8 этаж/105", 5500))
//        customList.add(Customs("Hello2"))
//        customList.add(Customs("Hello3"))
//        customList.add(Customs("Hello4"))
//        customList.add(Customs("Hello5"))
//        customList.add(Customs("Hello6"))

        data.addAll(customList)
        recyclerBindingAdapter.setItems(data)
        binding.customsRv.adapter = recyclerBindingAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onItemClickListenerRecycler = this
        }catch (e: Throwable){
            throw ClassCastException(context.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        onItemClickListenerRecycler = null
    }
}
