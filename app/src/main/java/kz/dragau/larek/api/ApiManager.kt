package kz.dragau.larek.api

import io.reactivex.Observable
import kz.dragau.larek.Constants
import kz.dragau.larek.models.objects.User
import kz.dragau.larek.models.shared.DataHolder
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers


interface ApiManager {
    @GET("values")
    fun getValues(@Header("No-Authentication") value: Boolean = true): Observable<ArrayList<String>>
}