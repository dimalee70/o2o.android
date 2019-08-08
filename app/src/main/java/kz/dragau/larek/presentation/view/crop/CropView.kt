package kz.dragau.larek.presentation.view.crop

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface CropView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun cropImage()

    @StateStrategyType(SkipStrategy::class)
    fun close()

    @StateStrategyType(SkipStrategy::class)
    fun rotate(degrees: Int)
}
