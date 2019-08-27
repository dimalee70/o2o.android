package kz.dragau.larek.models.objects

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class SalesOutletResult(

    @field:SerializedName("salesOutletId")
    val salesOutletId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("longitude")
    val longitude: Double?  = null,

    @field:SerializedName("stateCode")
    val stateCode: Integer? = null,

    @field:SerializedName("statusCode")
    val statusCode: Integer? = null,

    @field:SerializedName("importSequenceNumber")
    val importSequenceNumber: String? = null,

    @field:SerializedName("createdOn")
    val createdOn: String? = null,

    @field:SerializedName(" createdBy")
    val createdBy: String? = null,

    @field:SerializedName("modifiedOn")
    val  modifiedOn: String? = null,

    @field:SerializedName("modifiedBy")
    val  modifiedBy: String? = null
){
    override fun toString(): String {
        return "SalesOutletResult(salesOutletId=$salesOutletId, name=$name, address=$address, latitude=$latitude, longitude=$longitude, stateCode=$stateCode, statusCode=$statusCode, importSequenceNumber=$importSequenceNumber, createdOn=$createdOn, createdBy=$createdBy, modifiedOn=$modifiedOn, modifiedBy=$modifiedBy)"
    }
}