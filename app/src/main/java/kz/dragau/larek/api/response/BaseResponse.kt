package kz.dragau.larek.api.response

import com.google.gson.annotations.SerializedName

abstract class BaseResponse
{
    var messageList: List<Message>? = null
    var result: Boolean? = null
    abstract val resultObject: Any?



}