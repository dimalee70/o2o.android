package kz.dragau.larek.api.response

import com.google.gson.annotations.SerializedName

class SmsCodeResponse: BaseResponse() {
    @SerializedName("resultObject")
    override val resultObject: String? = null
}