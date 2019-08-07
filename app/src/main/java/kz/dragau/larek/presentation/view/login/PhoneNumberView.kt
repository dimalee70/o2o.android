package kz.dragau.larek.presentation.view.login

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.dragau.larek.presentation.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface PhoneNumberView : BaseView {
    fun verifyPhoneNumber(phoneNumber: String)
}
