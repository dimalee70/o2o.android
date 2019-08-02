package kz.dragau.larek.presentation.view

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.dragau.larek.presentation.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface MainAppView : BaseView {
    //fun showLogin()
    fun goToHome()
    fun showTutorial()
}
