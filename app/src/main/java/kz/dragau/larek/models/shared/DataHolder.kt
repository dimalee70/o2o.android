package kz.dragau.larek.models.shared


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.preference.PreferenceManager
import kz.dragau.larek.App
import kz.dragau.larek.Constants
import kz.dragau.larek.models.objects.User


object DataHolder : BaseObservable()
{
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(App.appComponent.context())

    var user: User? = null
    @Bindable get
    set(value) {
        field = value
        //notifyPropertyChanged(BR.user)
    }

    var userId: String?
    set(value) {
        sharedPref.edit().putString(Constants.userIdPrefsKey, value).apply()
    }
    get() {
        return sharedPref.getString(Constants.userIdPrefsKey, null)
    }

    var jwt: String?
    set(value) {
        sharedPref.edit().putString(Constants.jwtPrefsKey, value).apply()
    }
    get() {
        return sharedPref.getString(Constants.jwtPrefsKey, null)
    }
}