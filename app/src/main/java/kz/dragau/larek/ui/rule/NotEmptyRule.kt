package kz.dragau.larek.ui.rule

import ru.whalemare.rxvalidator.ValidateRule

class NotEmptyRule(private val message: String): ValidateRule {

    override fun errorMessage() = message

    override fun validate(data: String?): Boolean {
        if(!NotNullRule().validate(data)) return false
        return data!!.isNotEmpty()
    }
}