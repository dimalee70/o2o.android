package kz.dragau.larek.ui.fragment.customs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.customs.OnlineCustomsView
import kz.dragau.larek.presentation.presenter.customs.OnlineCustomsPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.dragau.larek.App
import kz.dragau.larek.databinding.FragmentOnlineCustomsBinding
import kz.dragau.larek.models.objects.Customs
import kz.dragau.larek.models.objects.OrdersByOutletResult
import kz.dragau.larek.models.objects.Types
import kz.dragau.larek.ui.adapters.RecyclerBindingAdapter
import kz.dragau.larek.ui.common.SwipeToDeleteCallback
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OnlineCustomsFragment : BaseMvpFragment(), OnlineCustomsView {
    companion object {
        const val TAG = "OnlineCustomsFragment"

        fun newInstance(): OnlineCustomsFragment {
            val fragment: OnlineCustomsFragment = OnlineCustomsFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mOnlineCustomsPresenter: OnlineCustomsPresenter

    @Inject
    lateinit var customs: ObservableArrayList<OrdersByOutletResult>

    @ProvidePresenter
    fun providePressenter(): OnlineCustomsPresenter{
        return OnlineCustomsPresenter(router)
    }

    lateinit var binding: FragmentOnlineCustomsBinding

    lateinit var recyclerCustomsAdapter: RecyclerBindingAdapter<OrdersByOutletResult>

//    var customs = ObservableArrayList<Customs>()

    private var onCustomClickListenerRecycler: RecyclerBindingAdapter.OnItemClickListener<OrdersByOutletResult>? = object:
        RecyclerBindingAdapter.OnItemClickListener<OrdersByOutletResult> {
        override fun onItemClick(position: Int, item: OrdersByOutletResult) {
            Toast.makeText(context!!, item.contactId, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        recyclerCustomsAdapter = RecyclerBindingAdapter(R.layout.item_custom_full, BR.data, context!!)
        if(onCustomClickListenerRecycler != null){
            recyclerCustomsAdapter.setOnItemClickListener(onCustomClickListenerRecycler!!)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_online_customs, container, false)
        binding.presenter = mOnlineCustomsPresenter
        recyclerCustomsAdapter.setItems(customs)
        binding.customsRv.adapter = recyclerCustomsAdapter

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                recyclerCustomsAdapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.customsRv)

        binding.customsRv.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
