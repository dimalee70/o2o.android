package kz.dragau.larek.api.requests

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kz.dragau.larek.BR
import kz.dragau.larek.models.shared.DataHolder

data class LoginRequestModel(
    private val _phone: String,
    private val _password: String,
    private val _platform: Int = 1
): BaseObservable()
{
    var platform: Int = 1
    var sessionid: String? = DataHolder.jwt

    var phone: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }

    var password: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }
}