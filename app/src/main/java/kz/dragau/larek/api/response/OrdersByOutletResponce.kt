package kz.dragau.larek.api.response

import com.google.gson.annotations.SerializedName
import kz.dragau.larek.models.objects.OrdersByOutletResult

class OrdersByOutletResponce: BaseResponse(){
    override val resultObject: ArrayList<OrdersByOutletResult>? = null
//            override val resultObject: List<SalesOutletResult>? = null
}