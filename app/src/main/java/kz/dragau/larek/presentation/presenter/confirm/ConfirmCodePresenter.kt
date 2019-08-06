package kz.dragau.larek.presentation.presenter.confirm

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import kz.dragau.larek.App
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.ConfirmRequestModel
import kz.dragau.larek.presentation.view.confirm.ConfirmCodeView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ConfirmCodePresenter(private val router: Router, private val code: String?) : MvpPresenter<ConfirmCodeView>() {
    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val confirmRequestModel = ConfirmRequestModel()

    private var disposable: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    fun checkConfirmCode(){
        viewState?.hideKeyboard()
        viewState?.showProgress()
        println(code)
        println(confirmRequestModel.confirmCode)
        if (code.equals(confirmRequestModel.confirmCode)){
            println("Success")
        }
    }

}
