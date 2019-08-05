package kz.dragau.larek.api.response

abstract class BaseResponse
{
    var messageList: List<Message>? = null
    var result: Boolean? = null
    abstract val resultObject: Any?
}