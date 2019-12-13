package kz.dragau.larek.models.objects

import com.google.gson.annotations.SerializedName

data class OrdersByOutletResult(
    @field:SerializedName("orderId")
    var orderId: String? = null,

    @field:SerializedName("contactId")
    var contactId: String? = null,

    @field:SerializedName("image")
    var image: String? = null,

    @field:SerializedName("price")
    var price: Int? = null,

    @field:SerializedName("contact")
    var contact: OrderContact? = null
)