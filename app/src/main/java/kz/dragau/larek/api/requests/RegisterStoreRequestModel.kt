package kz.dragau.larek.api.requests

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kz.dragau.larek.BR
import java.io.Serializable

class RegisterStoreRequestModel: BaseObservable(), Serializable
{
    var name: String? = null
        @Bindable get
        set(value){
            field = value
            notifyPropertyChanged(BR.name)
        }

    var address: String? = null
        @Bindable get
        set(value){
            field = value
            notifyPropertyChanged(BR.address)
        }

    var isChecked: Boolean = true
//        @Bindable get
//        set(value){
//            field = value
////            notifyPropertyChanged(BR.isAcceptOrders)
//        }

}