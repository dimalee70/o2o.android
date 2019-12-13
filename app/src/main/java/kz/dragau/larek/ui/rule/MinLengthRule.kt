package kz.dragau.larek.ui.rule

import ru.whalemare.rxvalidator.ValidateRule

class MinLengthRule(private var count: Int): ValidateRule{
    override fun errorMessage() = "Text must be smaller than $count"

    override fun validate(data: String?): Boolean {
        return data?.length?: 0 >= count
    }
}