package kz.dragau.larek.api.response

import kz.dragau.larek.models.objects.TokenResult

class TokenResponse: BaseResponse() {
    override val resultObject: TokenResult? = null

    override fun toString(): String {
        return "BaseResponse(messageList=$messageList, result=$result, resultObject=$resultObject)"
    }

}