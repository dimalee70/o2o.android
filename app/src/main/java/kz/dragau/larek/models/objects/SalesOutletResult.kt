package kz.dragau.larek.models.objects

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem
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

//    @field:SerializedName("stateCode")
//    val stateCode: Integer? = null,
//
//    @field:SerializedName("statusCode")
//    val statusCode: Integer? = null,
//
//    @field:SerializedName("importSequenceNumber")
//    val importSequenceNumber: String? = null,
//
//    @field:SerializedName("createdOn")
//    val createdOn: String? = null,
//
//    @field:SerializedName(" createdBy")
//    val createdBy: String? = null,
//
//    @field:SerializedName("modifiedOn")
//    val  modifiedOn: String? = null,
//
//    @field:SerializedName("modifiedBy")
//    val  modifiedBy: String? = null,

    @field:SerializedName("isAcceptOrders")
    var isAcceptOrders: Boolean? = true
): ClusterItem {

    constructor(
            salesOutletId: String?,

            name: String?,

            address: String?,

            latLng: LatLng,

//            stateCode: Integer?,
//
//            statusCode: Integer?,
//
//            importSequenceNumber: String?,
//
//            createdOn: String?,
//
//            createdBy: String?,
//
//            modifiedOn: String?,
//
//            modifiedBy: String?,

            isAcceptOrders: Boolean?
            ) : this(salesOutletId,
                name, address, latLng.latitude, latLng.longitude,isAcceptOrders
//                stateCode, statusCode,
//                importSequenceNumber, createdOn, createdBy,
//                modifiedOn, modifiedBy
        ) {}

    override fun getSnippet(): String {
        return  address!!
    }

    override fun getTitle(): String {
        return name!!
    }

    override fun getPosition(): LatLng {
        return LatLng(latitude!!, longitude!!)
    }

    override fun toString(): String {
        return "SalesOutletResult(salesOutletId=$salesOutletId, name=$name, address=$address, latitude=$latitude, longitude=$longitude, isAcceptOrders=$isAcceptOrders)"
    }


}