package kz.dragau.larek.ui.rule

import ru.whalemare.rxvalidator.ValidateRule

class NotEmptyRule: ValidateRule {
    override fun errorMessage() = "Text must not be null"

    override fun validate(data: String?): Boolean {
        if(!NotNullRule().validate(data)) return false
        return data!!.isNotEmpty()
    }
}