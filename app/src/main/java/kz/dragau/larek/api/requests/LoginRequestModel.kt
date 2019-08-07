package kz.dragau.larek.api.requests

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kz.dragau.larek.BR
import kz.dragau.larek.models.shared.DataHolder
import java.io.Serializable

class LoginRequestModel: BaseObservable(), Serializable {
    var mobilePhone: String? = null
        @Bindable get
        set(value) {
            if (value == null)
            {
                field = value
                return
            }
            val re = Regex("[^+0-9]")
            val tel = re.replace(value, "")
            field = tel
            notifyPropertyChanged(BR.phone)
        }

    var smsCode: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.smsCode)
        }
}