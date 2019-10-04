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
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.home.HomeMainView
import kz.dragau.larek.presentation.presenter.home.HomeMainPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import kz.dragau.larek.App
import kz.dragau.larek.databinding.FragmentHomeMainBinding
import kz.dragau.larek.models.objects.Customs
import kz.dragau.larek.models.objects.Types
import kz.dragau.larek.ui.adapters.RecyclerBindingAdapter
import kz.dragau.larek.ui.adapters.RecyclerBindingAdapter.OnItemClickListener
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import java.lang.ClassCastException
import kotlin.collections.ArrayList

class HomeMainFragment : BaseMvpFragment(), HomeMainView, OnItemClickListener<Customs>{


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

    lateinit var recyclerCustomsAdapter: RecyclerBindingAdapter<Customs>

    lateinit var recyclerTypesAdapter: RecyclerBindingAdapter<Types>

    private var onCustomClickListenerRecycler: OnItemClickListener<Customs>? = this

    private var onTypeClickListenerRecycle: OnItemClickListener<Types>? = object: OnItemClickListener<Types>{
        override fun onItemClick(position: Int, item: Types) {
            Toast.makeText(context!!, item.text, Toast.LENGTH_SHORT).show()
        }

    }
//        object: RecyclerBindingAdapter.OnItemClickListener<Customs>{
//            override fun onItemClick(position: Int, item: Customs) {
//                Toast.makeText(context!!, item.text, Toast.LENGTH_SHORT).show()
//            }
//        }

    var customs = ObservableArrayList<Customs>()
    var types = ObservableArrayList<Types>()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        recyclerCustomsAdapter = RecyclerBindingAdapter(R.layout.item_custom, BR.data, context!!)
        recyclerTypesAdapter = RecyclerBindingAdapter(R.layout.item_type, BR.data, context!!)
        if(onCustomClickListenerRecycler != null){
            recyclerCustomsAdapter.setOnItemClickListener(onCustomClickListenerRecycler!!)
        }
        if(onTypeClickListenerRecycle != null)
            recyclerTypesAdapter.setOnItemClickListener(onTypeClickListenerRecycle!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_main, container, false)
        binding.presenter = mHomeMainPresenter

        val typesList = ArrayList<Types>()
        typesList.add(Types("Акции", "#FF7058", "https://img.icons8.com/carbon-copy/2x/instagram-new.png"))
        typesList.add(Types("Поставщики", "#FFB980", "https://img.icons8.com/carbon-copy/2x/instagram-new.png"))
        typesList.add(Types("Сервис обслуживания", "#7985EB", "https://img.icons8.com/carbon-copy/2x/instagram-new.png"))
        typesList.add(Types("Заказы", "#2CC245", "https://img.icons8.com/carbon-copy/2x/instagram-new.png"))
        typesList.add(Types("Добавить торговую точку", "#FF7058", "https://img.icons8.com/carbon-copy/2x/instagram-new.png"))
        typesList.add(Types("Доставки", "#4B5BE6", "https://img.icons8.com/carbon-copy/2x/instagram-new.png"))
//        обслуживания
//        торговую точку

        val customList = ArrayList<Customs>()
        customList.add(Customs("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSUcp-vFwbcy-8m14jgD4p947IV_1TqO--f87Y54u-JvyiiDh1k",
            "Константин",
            "Аль фараби, к45Б 8 этаж/105",
            5500))
        customList.add(Customs("https://media.vanityfair.com/photos/58c2f5aa0a144505fae9e9ee/master/pass/avatar-sequels-delayed.jpg",
            "Дмитрий",
            "Аль фараби, к47Б",
            10_000))
        customList.add(Customs("https://cdn6.f-cdn.com/contestentries/918774/22954115/586eea98be949_thumb900.jpg",
            "Бородач",
            "Масанчи, к45Б 8 этаж/105",
            1_000_000_500))
        customList.add(Customs("https://cdn0.iconfinder.com/data/icons/iconshock_guys/512/andrew.png",
            "Волосач",
            "Аль фараби, к4Б 8 этаж/105, подъезд номер 5, ключи под поласом возле входной двери",
            5500))
//        customList.add(Customs("Hello2"))
//        customList.add(Customs("Hello3"))
//        customList.add(Customs("Hello4"))
//        customList.add(Customs("Hello5"))
//        customList.add(Customs("Hello6"))
        types.addAll(typesList)
        customs.addAll(customList)
        recyclerTypesAdapter.setItems(types)
        recyclerCustomsAdapter.setItems(customs)
        binding.typesRv.adapter = recyclerTypesAdapter
        binding.customsRv.adapter = recyclerCustomsAdapter
        binding.typesRv.setHasFixedSize(true)
        binding.customsRv.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onCustomClickListenerRecycler = this
            onTypeClickListenerRecycle = object: OnItemClickListener<Types>{
                override fun onItemClick(position: Int, item: Types) {
                    Toast.makeText(context!!, item.text, Toast.LENGTH_SHORT).show()
                }

            }
        }catch (e: Throwable){
            throw ClassCastException(context.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        onCustomClickListenerRecycler = null
        onTypeClickListenerRecycle = null
    }
}
