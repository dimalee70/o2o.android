package kz.dragau.larek.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import kz.dragau.larek.api.response.SaleCreateResponse
import kz.dragau.larek.api.response.SalesOutletResponse
import kz.dragau.larek.api.response.SmsCodeResponse
import kz.dragau.larek.api.response.TokenResponse
import kz.dragau.larek.models.objects.SalesOuter
import org.json.JSONObject
import retrofit2.http.*


interface ApiManager {
    @GET("values")
    fun getValues(@Header("No-Authentication") value: Boolean = true): Observable<ArrayList<String>>

    @GET("v1/identity/getsmscode")
    fun getSmsCode(@Query("mobilePhone") mPhone: String): Observable<SmsCodeResponse>


    @POST("v1/auth/gettoken")
    fun getToken(@Body body: JsonObject): Observable<TokenResponse>

    @GET("v1/salesoutlet/getbyname")
    fun getSalesOuterByName(@Query("name") name: String): Observable<SalesOutletResponse>

    @POST("v1/salesoutlet/getbyboundary")
    fun getSalesOuterByBoundary(@Body body: JsonArray): Observable<SalesOutletResponse>

    @POST("v1/salesoutlet/create")
    fun registerStore(@Body body: SalesOuter): Observable<SaleCreateResponse>

//    @POST("v1/salesoutlet/uploadphoto")
//    fun uploadPhoto()

}