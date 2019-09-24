package kz.dragau.larek.ui.rule

import ru.whalemare.rxvalidator.ValidateRule

class NotNullRule: ValidateRule {

    override fun errorMessage() = "Text must not be null"

    override fun validate(data: String?): Boolean {
        return data != null
    }
}