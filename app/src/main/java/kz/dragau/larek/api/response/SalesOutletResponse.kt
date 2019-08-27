package kz.dragau.larek.api.response

import com.google.gson.annotations.SerializedName
import kz.dragau.larek.models.objects.SalesOutletResult

class SalesOutletResponse: BaseResponse() {
    @field:SerializedName("resultObject")
    override val resultObject: List<SalesOutletResult>? = null
}