package kz.dragau.larek.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import kz.dragau.larek.api.response.*
import kz.dragau.larek.models.objects.SalesOuter
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
    fun registerStore(@Body body: SalesOuter): Observable<CreateResponse>

    @POST("v1/salesoutlet/uploadphoto")
    fun uploadPhoto(@Body body: JsonObject): Observable<CreateResponse>

    @GET("v1/order/getordersbyoutlet")
    fun getOrdersByOutlet(@Query("salesOutletId") salesOuterId: String): Observable<OrdersByOutletResponce>

    @GET("v1/product/getproductcategories")
    fun getProductategories(): Observable<ProductCategoriesResponce>

    @GET("v1/product/getproductbybarcode")
    fun getProductByBarcode(@Query("barcode") barcode: String): Observable<ProductResponce>

    @POST("v1/product/create")
    fun createProduct(@Body body: JsonObject): Observable<CreateResponse>

    @POST("v1/product/updateproduct")
    fun updateProduct(@Body body: JsonObject): Observable<CreateResponse>

}