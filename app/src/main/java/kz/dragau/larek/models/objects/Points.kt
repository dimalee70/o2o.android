package kz.dragau.larek.models.objects

import com.google.gson.annotations.SerializedName

data class Points(
    @field:SerializedName("x")
    val x: Double? = null,
    @field:SerializedName("y")
    val y: Double? = null
){
    override fun toString(): String {
        return "Points(x=$x, y=$y)"
    }
}