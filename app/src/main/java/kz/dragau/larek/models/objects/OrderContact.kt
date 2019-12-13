package kz.dragau.larek.models.objects

import com.google.gson.annotations.SerializedName

data class OrderContact(
    @field:SerializedName("contactId")
    var contactId: String? = null,

    @field:SerializedName("firstName")
    var firstName: String? = null,

    @field:SerializedName("lastName")
    var lastName: String? = null,

    @field:SerializedName("fullName")
    var fullName: String? = null,

    @field:SerializedName("mobilePhone")
    var mobilePhone: String? = null
)