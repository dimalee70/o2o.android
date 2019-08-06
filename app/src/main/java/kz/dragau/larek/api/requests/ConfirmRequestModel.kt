package kz.dragau.larek.api.requests

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kz.dragau.larek.BR

class ConfirmRequestModel: BaseObservable()
{
    var confirmCode: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }
}
