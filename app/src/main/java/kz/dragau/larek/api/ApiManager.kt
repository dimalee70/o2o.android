package kz.dragau.larek.api

import com.google.gson.JsonObject
import io.reactivex.Observable
import kz.dragau.larek.Constants
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.api.response.SalesOutletResponse
import kz.dragau.larek.api.response.SmsCodeResponse
import kz.dragau.larek.api.response.TokenResponse
import kz.dragau.larek.models.objects.User
import kz.dragau.larek.models.shared.DataHolder
import retrofit2.http.*


interface ApiManager {
    @GET("values")
    fun getValues(@Header("No-Authentication") value: Boolean = true): Observable<ArrayList<String>>

    @GET("v1/identity/getsmscode")
    fun getSmsCode(@Query("mobilePhone") mPhone: String): Observable<SmsCodeResponse>


    @POST("v1/auth/gettoken")
    @Headers("Content-Type: application/json")
    fun getToken(@Body body: JsonObject): Observable<TokenResponse>

    @GET("v1/salesoutlet/getbyname")
    @Headers("Content-Type: application/json")
    fun getSalesOuterByName(@Query("name") name: String): Observable<SalesOutletResponse>
}