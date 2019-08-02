package kz.dragau.larek.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.models.shared.DataHolder
import kz.dragau.larek.presentation.view.MainAppView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainAppPresenter(private val router: Router) : MvpPresenter<MainAppView>() {
    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
    }

    private var disposable: Disposable? = null
    lateinit var source: ExtractorMediaSource

    fun showLogin()
    {
        disposable = client.getValues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {    result ->
                    run {
                        viewState?.hideProgress()
                        //router.newRootScreen(Screens.LoginScreen())
                    }
                },
                { error ->
                    run {
                        viewState?.hideProgress()
                    }

                    if (error is HttpException)
                    {
                        if (error.code() == 403)
                        {
                            DataHolder.sharedPref.edit().clear().apply()
                            //viewState?.showLogin()
                            return@subscribe
                        }
                    }

                    run {
                        viewState?.showError(error)
                    }
                }
            )
    }

    val user = LoginRequestModel("",_password = "")

    fun auth() {
        viewState?.showProgress()

        /*disposable = client.userAuth(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {    result ->
                    run {
                        viewState?.hideProgress()
                        DataHolder.user = result.data.user
                        DataHolder.userId = result.data.user.id

                        viewState?.goToHome()
                    }
                },
                { error ->
                    run {
                        viewState?.hideProgress()
                    }

                    if (error is HttpException)
                    {
                        if (error.code() == 403)
                        {
                            DataHolder.sharedPref.edit().clear().apply()
                            viewState?.showLogin()
                            return@subscribe
                        }
                    }

                    run {
                        viewState?.showError(error)
                    }
                }
            )*/
    }
    fun showTutorial(){
        viewState.showTutorial()
    }
}
