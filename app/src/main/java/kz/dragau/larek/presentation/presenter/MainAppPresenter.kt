package kz.dragau.larek.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.dragau.larek.App
import kz.dragau.larek.Screens
import kz.dragau.larek.api.ApiManager
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.models.db.UserDao
import kz.dragau.larek.models.objects.User
import kz.dragau.larek.models.shared.DataHolder
import kz.dragau.larek.presentation.view.MainAppView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainAppPresenter(private val router: Router) : MvpPresenter<MainAppView>() {
    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var userDao: UserDao

    init {
        App.appComponent.inject(this)
    }

    private var disposable: Disposable? = null

    fun checkUserToken()
    {
        if (DataHolder.userId != -1L) {
            disposable = userDao.get(DataHolder.userId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { user: User ->
                        //router.newRootScreen(Screens.LoginScreen())
                        //TODO: перейти на Main Screen
                    },
                    {

                        viewState?.showError(it)
                    },
                    {
                        viewState?.showError("Пользователь не найден!", -1)
                    }
                )
        }
        else
        {
            router.newRootScreen(Screens.AddProductScreen())
        }
    }

    fun showLogin()
    {

        /*var id = 0L
        val u = User(username = "test", id = id)
        u.phone = "7055717177"


        disposable = Observable.fromCallable { userDao.insert(u) }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe {
                id = it

                disposable = userDao.get(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result: User ->
                            router.newRootScreen(Screens.LoginScreen())
                        },
                        {

                            viewState?.showError(it)
                        },
                        {
                            viewState?.showError("Пользователь не найден!", -1)
                        }
                    )
            }*/
    }

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

    }
}
