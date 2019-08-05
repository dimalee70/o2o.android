package kz.dragau.larek.api

import io.reactivex.Observable
import kz.dragau.larek.Constants
import kz.dragau.larek.api.requests.LoginRequestModel
import kz.dragau.larek.api.response.SmsCodeResponse
import kz.dragau.larek.models.objects.User
import kz.dragau.larek.models.shared.DataHolder
import retrofit2.http.*


interface ApiManager {
    @GET("values")
    fun getValues(@Header("No-Authentication") value: Boolean = true): Observable<ArrayList<String>>

    @GET("v1/identity/getsmscode")
    fun getSmsCode(@Query("mobilePhone") mPhone: String): Observable<SmsCodeResponse>
}