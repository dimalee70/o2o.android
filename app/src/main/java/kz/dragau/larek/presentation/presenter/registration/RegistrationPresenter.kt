package kz.dragau.larek.presentation.presenter.registration

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import kz.dragau.larek.App
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.RegistrationRequestModel
import kz.dragau.larek.presentation.view.registration.RegistrationView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class RegistrationPresenter(private var router: Router) : MvpPresenter<RegistrationView>() {
    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val registrationRequestModel = RegistrationRequestModel()

    private var disposable: Disposable? = null

    init {
        App.appComponent.inject(this)
    }
}