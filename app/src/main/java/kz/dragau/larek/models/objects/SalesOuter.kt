package kz.dragau.larek.models.objects

data class SalesOuter(
                      var name: String?,
                      var address: String?,
                      var latitude: Double?,
                      var longitude: Double?,
                      var isAcceptOrders: Boolean? = true){

    override fun toString(): String {
        return "SalesOuter(name=$name, address=$address, latitude=$latitude, longitude=$longitude, isAcceptOrders=$isAcceptOrders)"
    }
}