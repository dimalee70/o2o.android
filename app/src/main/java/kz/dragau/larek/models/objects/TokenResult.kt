package kz.dragau.larek.models.objects

import com.google.gson.annotations.SerializedName

data class TokenResult(

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("expireDate")
    val expireDate: String? = null
) {
    override fun toString(): String {
        return "TokenResult(code=$token, expireDate=$expireDate)"
    }
}